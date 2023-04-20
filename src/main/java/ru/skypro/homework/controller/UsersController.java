package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.JwtUserDetailsService;
import ru.skypro.homework.service.UserService;

import java.awt.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/users")
public class UsersController {

    private final UserService userService;
    private final JwtUserDetailsService jwtUserDetailsService;

    public UsersController(UserService userService, JwtUserDetailsService jwtUserDetailsService) {
        this.userService = userService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostMapping(value = "/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword, Authentication authentication){
        userService.changePassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getUser(Authentication authentication){
        return ResponseEntity.ok(userService.findByUsername(authentication.getName()));
    }

    @PatchMapping(value = "/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PatchMapping(value = "/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody Image image){
        return ResponseEntity.ok().build();
    }
}
