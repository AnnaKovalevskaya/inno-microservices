package com.innowise.demo.serserviceapp.service;

import com.innowise.demo.serserviceapp.dto.UserDto;
import com.innowise.demo.serserviceapp.exception.UserNotFoundException;
import com.innowise.demo.serserviceapp.mapper.UserMapper;
import com.innowise.demo.serserviceapp.model.User;
import com.innowise.demo.serserviceapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldReturnCreatedUser() {
        UserDto userDto = new UserDto(null, "Anna", "Kovalevskaja", LocalDate.of(2001, 9, 19), "anna@gmail.com");
        User user = new User(1L, "Anna", "Kovalevskaja", LocalDate.of(2001, 9, 19), "anna@gmail.com", null);
        UserDto expectedDto = new UserDto(1L, "Anna", "Kovalevskaja", LocalDate.of(2001, 9, 19), "anna@gmail.com");

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(expectedDto, result);
        verify(userRepository).save(user);
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = new User(1L, "Elon ", "Musk ", LocalDate.of(1971 , 6, 28), "elon@gmail.com", null);
        UserDto userDto = new UserDto(1L, "Elon ", "Musk ", LocalDate.of(1971 , 6, 28), "elon@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void getUserById_shouldThrowExceptionWhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getAllUsers_shouldReturnPagedUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User(1L, "Charles ", "Chaplin ", LocalDate.of(1889 , 4, 16 ), "charles@gmail.com", null);
        Page<User> userPage = new PageImpl<>(List.of(user), pageable, 1);
        UserDto userDto = new UserDto(1L, "Charles ", "Chaplin ", LocalDate.of(1889 , 4, 16 ), "charles@gmail.com");
        Page<UserDto> expectedPage = new PageImpl<>(List.of(userDto), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toDto(user)).thenReturn(userDto);

        Page<UserDto> result = userService.getAllUsers(pageable);

        assertEquals(expectedPage.getContent(), result.getContent());
    }

    @Test
    void getUserByEmail_shouldReturnUser() {
        User user = new User(1L, "Walter ", "Disney ", LocalDate.of(1901, 12, 5), "walter@gmail.com", null);
        UserDto userDto = new UserDto(1L, "Walter ", "Disney ", LocalDate.of(1901, 12, 5), "walter@gmail.com");

        when(userRepository.findByEmail("walter@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        Optional<UserDto> result = userService.getUserByEmail("walter@example.com");

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        UserDto userDto = new UserDto(null, "Marilyn ", "Monroe ", LocalDate.of(1926 , 6, 1), "marilyn@gmail.com");
        User existingUser = new User(1L, "Albert ", "Einstein", LocalDate.of(1879 , 3, 14), "albert@gmail.com", null);
        User updatedUser = new User(1L, "Marilyn ", "Monroe ", LocalDate.of(1926 , 6, 1), "marilyn@gmail.com", null);
        UserDto expectedDto = new UserDto(1L, "Albert ", "Einstein", LocalDate.of(1879 , 3, 14), "albert@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(expectedDto);

        UserDto result = userService.updateUser(1L, userDto);

        assertEquals(expectedDto, result);
        verify(userRepository).save(existingUser);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}