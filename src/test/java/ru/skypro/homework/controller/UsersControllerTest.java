package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.userDTO.UserDTO;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.service.CustomUserDetailsService;
import ru.skypro.homework.service.mapper.UserMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvcUsers;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepos userRepos;
    private final User user = new User();
    @BeforeEach
    void setUp() {
        user.setUsername("1@mail.ru");
        user.setFirstName("firstTest");
        user.setLastName("lastTest");
        user.setPhone("+79990002233");
        user.setPassword(encoder.encode("1234qwer"));
        user.setEnabled(true);
        user.setRole(Role.USER);
        user.setImage("userAvatar".getBytes());
        userRepos.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepos.deleteAll();
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void setPassword() throws Exception {

        mockMvcUsers.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"newPassword\": \"qwer1234\",\n" +
                                "  \"currentPassword\": \"1234qwer\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void getUser() throws Exception {
        mockMvcUsers.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("1@mail.ru"));
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void updateUser() throws Exception {
        UserDTO userDTO = UserMapper.mapToDTO(userRepos.findByUsername(user.getUsername()).orElseThrow(NotFoundException::new));
        userDTO.setEmail("2@mail.ru");
        mockMvcUsers.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDTO.getId()))
                .andExpect(jsonPath("$.email").value("2@mail.ru"));
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void updateUserImage() throws Exception {
        MockPart image = new MockPart("image", "avatar", "userAvatar".getBytes());
        mockMvcUsers.perform(patch("/users/me/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.addPart(image);
                    return request;
                }))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void showAvatarOnId() throws Exception {
        User testUser = userRepos.findByUsername(user.getUsername()).orElseThrow(NotFoundException::new);
        mockMvcUsers.perform(get("/users/me/image/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().bytes("userAvatar".getBytes()));

    }
}