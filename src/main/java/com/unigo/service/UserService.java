package com.unigo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unigo.dto.response.UsersResponse;
import com.unigo.mapper.UserMapper;
import com.unigo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UsersResponse getUsers() {
        return new UsersResponse(userMapper.toResponseList(userRepository.findAll()));
    }
}
