package by.shakhnitski.onlinestore.user.controller.impl;

import by.shakhnitski.onlinestore.user.controller.UserController;
import by.shakhnitski.onlinestore.user.dto.UserCreateRequest;
import by.shakhnitski.onlinestore.user.dto.UserDto;
import by.shakhnitski.onlinestore.user.mapper.UserMapper;
import by.shakhnitski.onlinestore.user.model.User;
import by.shakhnitski.onlinestore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userService.getUsers();
        return users.stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userService.getUser(userId);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto createUser(UserCreateRequest createRequest) {
        User user = userMapper.mapToEntity(createRequest);
        user = userService.createUser(user);
        return userMapper.mapToDto(user);
    }

    @Override
    public void removeUser(Long userId) {
        userService.removeUser(userId);
    }
}
