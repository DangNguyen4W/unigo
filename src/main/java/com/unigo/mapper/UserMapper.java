package com.unigo.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.unigo.dto.request.RegisterRequest;
import com.unigo.dto.response.UserResponse;
import com.unigo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest user);

    RegisterRequest toRequest(User user);

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}
