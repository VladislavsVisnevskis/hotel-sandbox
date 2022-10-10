package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.User;
import com.wandoo.hotel.exception.NotFoundException;
import com.wandoo.hotel.mapper.UserMapper;
import com.wandoo.hotel.model.UserDto;
import com.wandoo.hotel.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserDtoById(Long id) {
        logger.info("Retrieving user by id: {}", id);
        return userRepository.findById(id)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("User is not found by id: " + id));
    }

    public User getUserById(Long id) {
        logger.info("Retrieving user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User is not found by id: " + id));
    }

    public UserDto saveUser(UserDto user) {
        logger.info("Save user: {}", user);
        return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapFromUserDto(user)));
    }

    public void deleteUser(Long id) {
        logger.info("Removing user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User is not found by id: " + id));
        userRepository.delete(user);
        logger.info("User with id: {} is deleted", id);
    }
}
