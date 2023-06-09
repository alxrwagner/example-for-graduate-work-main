package ru.skypro.homework.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.CustomUserDetails;
import ru.skypro.homework.dto.userDTO.NewPassword;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.validator.Validator;

import java.io.IOException;

@Service
public class UserService {
    private final UserRepos userRepos;
    private final PasswordEncoder encoder;

    public UserService(UserRepos userRepos, PasswordEncoder encoder) {
        this.userRepos = userRepos;
        this.encoder = encoder;
    }

    public UserDTO findByUsername(Authentication authentication) {
        return UserMapper.mapToDTO(UserMapper.customUserDetailsToUser((CustomUserDetails) authentication.getPrincipal()));
    }

    public void changePassword(NewPassword newPassword, Authentication authentication) {
        Validator.checkValidateObj(newPassword);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        if (!encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Authentication exception");
        }
        user.setPassword(encoder.encode(newPassword.getNewPassword()));
        userRepos.save(UserMapper.customUserDetailsToUser(user));
    }

    public UserDTO update(UserDTO userDTO) {
        User user = userRepos.findById(userDTO.getId()).orElseThrow(NotFoundException::new);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        userRepos.save(user);
        return userDTO;
    }

    public boolean updateAvatar(Authentication authentication, MultipartFile avatar) throws IOException {
        User user = UserMapper.customUserDetailsToUser((CustomUserDetails) authentication.getPrincipal());
        user.setImage(Validator.checkValidateObj(avatar).getBytes());
        userRepos.save(user);
        return true;
    }

    public byte[] showAvatarOnId(Integer id) {
        return userRepos.findById(id).orElseThrow(NotFoundException::new).getImage();
    }
}
