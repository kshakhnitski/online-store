package by.shakhnitski.onlinestore.user.mapper;

import by.shakhnitski.onlinestore.user.dto.UserCreateRequest;
import by.shakhnitski.onlinestore.user.dto.UserDto;
import by.shakhnitski.onlinestore.user.model.User;
import by.shakhnitski.onlinestore.user.model.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(imports = {BigDecimal.class, UserStatus.class})
public interface UserMapper {
    UserDto mapToDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "balance", expression = "java(BigDecimal.ZERO)")
    @Mapping(target = "status", expression = "java(UserStatus.ACTIVE)")
    User mapToEntity(UserCreateRequest createRequest);
}
