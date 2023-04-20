package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.userDTO.JwtUser;
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

    public static User mapFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        return user;
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

    public static JwtUser mapToJwtUser(User user) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setRole(user.getRole());
        jwtUser.setUsername(user.getUsername());
        jwtUser.setPassword(user.getPassword());
        jwtUser.setFirstName(user.getFirstName());
        jwtUser.setLastName(user.getLastName());
        jwtUser.setPhone(user.getPhone());
        jwtUser.setImage("/users/me/image/" + user.getId());
        jwtUser.setEnabled(true);
        return jwtUser;
    }

    public static User jwtUserToUser(JwtUser jwtUser) {
        User user = new User();
        user.setUsername(jwtUser.getUsername());
        user.setFirstName(jwtUser.getFirstName());
        user.setLastName(jwtUser.getLastName());
        user.setPhone(jwtUser.getPhone());
        user.setPassword(jwtUser.getPassword());
        user.setEnabled(jwtUser.isEnabled());
        user.setRole(jwtUser.getRole());
        return user;
    }
}
