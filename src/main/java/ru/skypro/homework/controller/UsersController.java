package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.userDTO.NewPassword;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword, Authentication authentication){
        userService.changePassword(newPassword, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getUser(Authentication authentication){
        return ResponseEntity.ok(userService.findByUsername(authentication));
    }

    @PatchMapping(value = "/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.update(userDTO));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(Authentication authentication, @RequestParam("image") MultipartFile avatar) throws IOException {
        return ResponseEntity.ok(userService.updateAvatar(authentication, avatar));
    }

    @GetMapping(value = "/me/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showAvatarOnId(@PathVariable("id") Integer id) {
        return userService.showAvatarOnId(id);
    }
}
