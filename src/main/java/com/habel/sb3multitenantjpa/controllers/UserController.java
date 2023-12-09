package com.habel.sb3multitenantjpa.controllers;

import com.habel.sb3multitenantjpa.entity.tenant.User;
import com.habel.sb3multitenantjpa.entity.tenant.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor

public class UserController {

    private final UserRepository userRepository;

    @Parameter(name = "X-Org", description = "Organization header", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"org1", "org2"}))
    @GetMapping
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Parameter(name = "X-Org", description = "Organization header", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"org1", "org2"}))
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findUserByIdNative(id);
    }
}
