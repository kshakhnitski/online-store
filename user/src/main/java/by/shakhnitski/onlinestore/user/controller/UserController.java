package by.shakhnitski.onlinestore.user.controller;

import by.shakhnitski.onlinestore.user.dto.UserCreateRequest;
import by.shakhnitski.onlinestore.user.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/v1/users")
public interface UserController {

    @GetMapping
    List<UserDto> getUsers();

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable Long userId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto createUser(@Valid @RequestBody UserCreateRequest createRequest);

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeUser(@PathVariable Long userId);
}
