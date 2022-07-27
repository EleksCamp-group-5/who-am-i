package com.eleks.academy.whoami.service.impl;

import com.eleks.academy.whoami.core.exception.UserAlreadyExistsException;
import com.eleks.academy.whoami.dmo.User;
import com.eleks.academy.whoami.model.request.UserRequestDto;
import com.eleks.academy.whoami.model.response.UserResponseDto;
import com.eleks.academy.whoami.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResponseDto create(UserRequestDto userRequestDto) {

        User userFromDB = userRepository.findByUsername(userRequestDto.getNickname());

        if (userFromDB != null) {
            throw new UserAlreadyExistsException("User with username=[" + userRequestDto.getNickname() + "] already exists");
        }

        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setUsername(userRequestDto.getNickname());
        user.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));

        User createdUser = userRepository.save(user);
        return toUserResponseDto(createdUser);
    }

    private UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setNickname(user.getUsername());
        return userResponseDto;
    }
}
