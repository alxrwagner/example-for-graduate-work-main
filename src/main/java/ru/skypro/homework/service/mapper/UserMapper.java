package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.userDTO.CustomUserDetails;
import ru.skypro.homework.dto.userDTO.RegisterReq;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.model.User;

public class UserMapper {

    public static UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setImage("/users/me/image/" + user.getId());
        return userDTO;
    }

    public static User mapFromRegister(RegisterReq reg) {
        User user = new User();
        user.setUsername(reg.getUsername());
        user.setPassword(reg.getPassword());
        user.setFirstName(reg.getFirstName());
        user.setLastName(reg.getLastName());
        user.setPhone(reg.getPhone());
        return user;
    }

    public static CustomUserDetails mapToJwtUser(User user) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setRole(user.getRole());
        customUserDetails.setUsername(user.getUsername());
        customUserDetails.setPassword(user.getPassword());
        customUserDetails.setFirstName(user.getFirstName());
        customUserDetails.setLastName(user.getLastName());
        customUserDetails.setPhone(user.getPhone());
        customUserDetails.setImage("/users/me/image/" + user.getId());
        customUserDetails.setEnabled(true);
        return customUserDetails;
    }

    public static User jwtUserToUser(CustomUserDetails customUserDetails) {
        User user = new User();
        user.setUsername(customUserDetails.getUsername());
        user.setFirstName(customUserDetails.getFirstName());
        user.setLastName(customUserDetails.getLastName());
        user.setPhone(customUserDetails.getPhone());
        user.setPassword(customUserDetails.getPassword());
        user.setEnabled(customUserDetails.isEnabled());
        user.setRole(customUserDetails.getRole());
        return user;
    }
}
