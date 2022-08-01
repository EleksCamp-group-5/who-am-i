package com.eleks.academy.whoami.service.impl;

import com.eleks.academy.whoami.core.exception.UserAlreadyExistsException;
import com.eleks.academy.whoami.core.exception.UserNotFoundException;
import com.eleks.academy.whoami.dmo.User;
import com.eleks.academy.whoami.model.request.UserRequestDto;
import com.eleks.academy.whoami.model.response.UserResponseDto;
import com.eleks.academy.whoami.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String USER_NOT_FOUND_MSG = "User with id = %s not found";

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

    public UserResponseDto get(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MSG, id)));
        return toUserResponseDto(user);
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserResponseDto toUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setNickname(user.getUsername());
        return userResponseDto;
    }
}
