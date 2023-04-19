package ru.nikkita.vr.geotagger.authservice.mapper;

import org.mapstruct.Mapper;
import ru.nikkita.vr.geotagger.authservice.dto.UserRegistrationRequestDto;
import ru.nikkita.vr.geotagger.authservice.model.User;

@Mapper
public interface UserMapper {
    User toUser(UserRegistrationRequestDto dto);
}
