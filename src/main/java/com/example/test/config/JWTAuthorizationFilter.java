package com.example.test.config;

import com.example.test.exception.InvalidTokenRequestException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "eyJhbGciOiJIUzUxMiJ9";

	@Value("${jwt.secret}")
	private String secret;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
				if (checkJWTToken(request, response)) {
					Claims claims = validateToken(request);
					if (claims.get("authorities") != null) {
						setUpSpringAuthentication(claims);
					} else {
						SecurityContextHolder.clearContext();
					}
				} else {
					SecurityContextHolder.clearContext();
				}
			chain.doFilter(request, response);

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	private Claims validateToken(HttpServletRequest request) {

		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		Claims claim;

		try {

			claim = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();

		} catch (SignatureException ex) {
			logger.info("Invalid JWT signature");
			throw new InvalidTokenRequestException("JWT", jwtToken, "Firma incorrecta");

		} catch (MalformedJwtException ex) {
			logger.info("Token JWT no válido");
			throw new InvalidTokenRequestException("JWT", jwtToken, "Token con formato incorrecto");

		} catch (ExpiredJwtException ex) {
			logger.info("Token JWT caducado");
			throw new InvalidTokenRequestException("JWT", jwtToken, "Token expirado. Actualización requerida");

		} catch (UnsupportedJwtException ex) {
			logger.info("Token JWT no admitido");
			throw new InvalidTokenRequestException("JWT", jwtToken, "Token no admitido");

		} catch (IllegalArgumentException ex) {
			logger.info("La cadena de claims de JWT está vacía.");
			throw new InvalidTokenRequestException("JWT", jwtToken, "Token de argumento ilegal");
		}

		return claim;
	}

	/**
	 * Authentication method in Spring flow
	 * 
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
