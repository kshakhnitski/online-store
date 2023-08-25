package by.shakhnitski.onlinestore.user.service;

import by.shakhnitski.onlinestore.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long userId);

    User createUser(User user);

    void removeUser(Long userId);
}
