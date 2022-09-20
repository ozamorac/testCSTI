package com.example.test.controller;

import com.example.test.dto.UserDto;
import com.example.test.entity.User;
import com.example.test.request.JwtRequest;
import com.example.test.response.JwtResponse;
import com.example.test.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/security")
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        log.info("-------------------------------------" );
        log.info("JwtAuthenticationController: createAuthenticationToken{}");

        String userRequest = authenticationRequest.getUsername();
        String passRequest = authenticationRequest.getPassword();

        if (authenticationRequest.getUsername().length()==0 || authenticationRequest.getPassword().length()==0) {
            log.info("JwtAuthenticationController: createAuthenticationToken{}: Excepcion Credenciales incorrectas");
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        if (!isValidCredentials(userRequest, passRequest)) {
            log.info("JwtAuthenticationController: createAuthenticationToken{}: Excepcion" +
                    " Credenciales incorrectas");

            Map<String, Object> responseError = new HashMap<>();
            responseError.put("Error", "Credenciales incorrectas");
            return new ResponseEntity<Map<String, Object>>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("JwtAuthenticationController: createAuthenticationToken{}: Generar token");

        String token = getJWTToken(authenticationRequest.getUsername());

        UserDto user = new UserDto();
        user.setUser(authenticationRequest.getUsername());
        user.setToken(token);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    private String getJWTToken(String username) {
        String secretKey = "eyJhbGciOiJIUzUxMiJ9";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts
                .builder()
                .setId("nsrtmJWT")
                .setClaims(claims)
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10800000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }

    private Boolean isValidCredentials(String userName, String password){
        Boolean response = false;

        Boolean isUsuario = userService.findUserByCredetianls(userName, password);

        if (isUsuario) {
            response= true;
        }

        return response;
    }

    private ResponseEntity<Map<String, Object>> buildResponseError(List<String> errors) {
        Map<String, Object> response = new HashMap<>();
        response.put("Mensaje", errors);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

}