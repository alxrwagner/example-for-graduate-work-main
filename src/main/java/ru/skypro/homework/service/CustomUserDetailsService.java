package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.userDTO.CustomUserDetails;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.validator.Validator;

@Service
public class CustomUserDetailsService implements UserDetailsManager {

    private final PasswordEncoder encoder;
    private final UserRepos userRepos;

    public CustomUserDetailsService(PasswordEncoder encoder, UserRepos userRepos) {
        this.encoder = encoder;
        this.userRepos = userRepos;
    }

    @Override
    public void createUser(UserDetails user) {
        User saveUser = UserMapper.customUserDetailsToUser((CustomUserDetails) user);
        saveUser.setPassword(encoder.encode(user.getPassword()));
        userRepos.save(saveUser);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepos.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Validator.checkValidateString(username);
        return UserMapper.mapToCustomUserDetails(userRepos.findByUsername(username).orElseThrow(NotFoundException::new));
    }
}
