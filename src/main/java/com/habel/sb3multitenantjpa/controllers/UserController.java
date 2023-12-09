package com.habel.sb3multitenantjpa.controllers;

import com.habel.sb3multitenantjpa.entity.tenant.User;
import com.habel.sb3multitenantjpa.entity.tenant.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
