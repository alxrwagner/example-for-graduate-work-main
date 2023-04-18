package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UserDTO;

import java.awt.*;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/users")
public class UsersController {

    @PostMapping(value = "/set_password")
    public ResponseEntity setPassword(@RequestBody NewPassword newPassword){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me")
    public UserDTO getUser(){
        return new UserDTO();
    }

    @PatchMapping(value = "/me")
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        return userDTO;
    }

    @PatchMapping(value = "/me/image")
    public ResponseEntity updateUserImage(@RequestBody Image image){
        return ResponseEntity.ok().build();
    }
}
