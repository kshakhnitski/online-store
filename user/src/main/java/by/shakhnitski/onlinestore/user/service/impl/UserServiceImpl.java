package by.shakhnitski.onlinestore.user.service.impl;

import by.shakhnitski.onlinestore.user.model.User;
import by.shakhnitski.onlinestore.user.repository.UserRepository;
import by.shakhnitski.onlinestore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException();
        }
        userRepository.deleteById(userId);
    }
}
