package com.example.test.service.impl;

import com.example.test.entity.User;
import com.example.test.repository.UserRepository;
import com.example.test.request.JwtRequest;
import com.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean findUserByCredetianls(String userName, String password) {
        Boolean response = true;
        User usuario = userRepository.findByUsername(userName);

        if (usuario != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passEncode = passwordEncoder.encode(password);
            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                response = false;
            }
        } else {
            response = false;
        }
        return response;
    }
}
