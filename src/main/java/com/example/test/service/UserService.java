package com.example.test.service;

import com.example.test.entity.User;
import com.example.test.request.JwtRequest;

public interface UserService {

    Boolean findUserByCredetianls(String userName, String pasword);
}
