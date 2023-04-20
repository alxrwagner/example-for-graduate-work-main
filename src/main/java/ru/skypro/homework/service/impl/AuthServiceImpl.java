package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userDTO.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.JwtUserDetailsService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.mapper.UserMapper;


@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUserDetailsService manager;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(JwtUserDetailsService manager, PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.encoder = passwordEncoder;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            throw new NotFoundException();
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReq registerReq) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        User user = UserMapper.mapFromRegister(registerReq);
        user.setRole(Role.USER);
        manager.createUser(UserMapper.mapToJwtUser(user));
        return true;
    }
}
