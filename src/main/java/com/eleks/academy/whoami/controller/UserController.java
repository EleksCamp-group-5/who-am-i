package com.eleks.academy.whoami.controller;

import com.eleks.academy.whoami.model.request.UserRequestDto;
import com.eleks.academy.whoami.model.response.UserResponseDto;
import com.eleks.academy.whoami.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.create(userRequestDto));
    }
}
