package ru.skypro.homework.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.service.mapper.UserMapper;

@Service
public class UserService {
    private final UserRepos userRepos;
    private final JwtUserDetailsService jwtService;
    private final PasswordEncoder encoder;
    public UserService(UserRepos userRepos, JwtUserDetailsService jwtService, PasswordEncoder encoder) {
        this.userRepos = userRepos;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public UserDTO findByUsername(String username) {
        return UserMapper.mapToDTO(userRepos.findByUsername(username).orElseThrow(NotFoundException::new));
    }

    public void changePassword(NewPassword newPassword, Authentication authentication){
        User user = UserMapper.mapFromDTO(findByUsername(authentication.getName()));
        if(!encoder.matches(newPassword.getCurrentPassword(), user.getPassword())){
            throw new BadCredentialsException("Authentication exception");
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        userRepos.save(user);
    }

    public UserDTO update(UserDTO userDTO){
        User user = userRepos.findById(userDTO.getId()).orElseThrow(NotFoundException::new);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        userRepos.save(user);
        return userDTO;
    }
}
