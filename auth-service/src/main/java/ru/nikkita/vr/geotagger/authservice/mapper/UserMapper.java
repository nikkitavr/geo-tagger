package ru.nikkita.vr.geotagger.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.nikkita.vr.geotagger.authservice.dto.UserServiceAddUserRequestDto;
import ru.nikkita.vr.geotagger.authservice.model.User;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserServiceAddUserRequestDto toUserServiceRequestDto (User user);

}
