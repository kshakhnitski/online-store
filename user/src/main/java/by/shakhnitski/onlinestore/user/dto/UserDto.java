package by.shakhnitski.onlinestore.user.dto;

import by.shakhnitski.onlinestore.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private BigDecimal balance;
    private Instant registrationDate;
    private UserStatus status;
}
