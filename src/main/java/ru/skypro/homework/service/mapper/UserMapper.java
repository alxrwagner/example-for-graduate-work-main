package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

public class UserMapper {

    public static UserDTO mapToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setImage(user.getImage());
        return userDTO;
    }

    public static User mapFromDTO(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setImage(userDTO.getImage());
        return user;
    }
}
