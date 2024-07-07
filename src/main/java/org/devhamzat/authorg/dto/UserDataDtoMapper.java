package org.devhamzat.authorg.dto;

import org.devhamzat.authorg.entity.User;

public class UserDataDtoMapper {
    public UserDto mapUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        return userDto;

    }
}
