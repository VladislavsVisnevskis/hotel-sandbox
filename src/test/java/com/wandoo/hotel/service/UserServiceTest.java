package com.wandoo.hotel.service;

import com.wandoo.hotel.domain.User;
import com.wandoo.hotel.exception.NotFoundException;
import com.wandoo.hotel.mapper.UserMapper;
import com.wandoo.hotel.model.UserDto;
import com.wandoo.hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

    @Test
    void getAllUsersTest() {
        List<User> userList = getUserList(List.of(1L, 2L, 3L));
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> expected = getUserDtoList(List.of(1L, 2L, 3L));
        try (MockedStatic<UserMapper> utilities = Mockito.mockStatic(UserMapper.class)) {
            utilities.when(() -> UserMapper.mapToUserDto(userList.get(0))).thenReturn(expected.get(0));
            utilities.when(() -> UserMapper.mapToUserDto(userList.get(1))).thenReturn(expected.get(1));
            utilities.when(() -> UserMapper.mapToUserDto(userList.get(2))).thenReturn(expected.get(2));

            List<UserDto> result = service.getAllUsers();

            assertEquals(expected.size(), result.size());
            verify(userRepository).findAll();
        }
    }

    @Test
    void getUserDtoByIdTest() {
        User user = getUser(69L);
        when(userRepository.findById(69L)).thenReturn(Optional.of(user));
        UserDto expected = getUserDto(69L);
        try (MockedStatic<UserMapper> utilities = Mockito.mockStatic(UserMapper.class)) {
            utilities.when(() -> UserMapper.mapToUserDto(user)).thenReturn(expected);

            UserDto result = service.getUserDtoById(69L);

            assertEquals(expected, result);
            verify(userRepository).findById(69L);
        }
    }

    @Test
    void getUserByIdTest() {
        User user = getUser(69L);
        when(userRepository.findById(69L)).thenReturn(Optional.of(user));
        User expected = getUser(69L);

        User result = service.getUserById(69L);

        assertEquals(expected, result);
        verify(userRepository).findById(69L);
    }

    @Test
    void getUserByIdTest_whenNotPresent() {
        User user = getUser(69L);
        when(userRepository.findById(69L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getUserById(69L));
    }

    @Test
    void saveUserTest() {
        User user = getUser(69L);
        UserDto userToSave = getUserDto(69L);
        when(userRepository.save(user)).thenReturn(user);
        UserDto expected = getUserDto(69L);

        try (MockedStatic<UserMapper> utilities = Mockito.mockStatic(UserMapper.class)) {
            utilities.when(() -> UserMapper.mapToUserDto(user)).thenReturn(expected);
            utilities.when(() -> UserMapper.mapFromUserDto(userToSave)).thenReturn(user);

            UserDto result = service.saveUser(userToSave);

            assertEquals(expected, result);
            verify(userRepository).save(user);
        }
    }

    @Test
    void deleteUserTest() {
        User user = getUser(69L);
        UserDto userToSave = getUserDto(69L);
        when(userRepository.findById(69L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        service.deleteUser(69L);

        verify(userRepository).findById(69L);
        verify(userRepository).delete(user);
    }

    private List<User> getUserList(List<Long> ids) {
        return ids.stream().map(this::getUser)
                .collect(Collectors.toList());
    }

    private User getUser(Long id) {
        return User.builder()
                .id(id)
                .firstName("John")
                .build();
    }

    private List<UserDto> getUserDtoList(List<Long> ids) {
        return ids.stream().map(this::getUserDto)
                .collect(Collectors.toList());
    }

    private UserDto getUserDto(Long id) {
        return UserDto.builder()
                .id(id)
                .firstName("John")
                .build();
    }
}