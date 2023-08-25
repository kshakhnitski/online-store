package by.shakhnitski.onlinestore.user.repository;

import by.shakhnitski.onlinestore.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
