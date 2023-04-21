package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.userDTO.LoginReq;
import ru.skypro.homework.dto.userDTO.RegisterReq;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.service.CustomUserDetailsService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvcAuth;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepos userRepos;
    private final RegisterReq registerReq = new RegisterReq();
    private LoginReq loginReq;
    private User user;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername(RandomStringUtils.randomAlphanumeric(8) + "@mail.ru");
        user.setFirstName("firstTest");
        user.setLastName("lastTest");
        user.setPhone("+79990002233");
        user.setPassword(encoder.encode("1234qwer"));
        user.setEnabled(true);
        user.setRole(Role.USER);
        userRepos.save(user);

        registerReq.setUsername(RandomStringUtils.randomAlphanumeric(8) + "@mail.ru");
        registerReq.setPassword("1234qwer");
        registerReq.setFirstName("testFirst");
        registerReq.setLastName("testLast");
        registerReq.setPhone("+79990001122");
        registerReq.setRole(Role.USER);

        loginReq = new LoginReq();
        loginReq.setPassword("1234qwer");
        loginReq.setUsername(user.getUsername());
    }

    @AfterEach
    void tearDown() {
        userRepos.deleteAll();
    }

    @Test
    void login() throws Exception {
        mockMvcAuth.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk());
    }

    @Test
    void register() throws Exception {
        mockMvcAuth.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andDo(print())
                .andExpect(status().isOk());

        userDetails = userDetailsService.loadUserByUsername(registerReq.getUsername());

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        mockMvcAuth.perform(get("/users/me").with(authentication(auth))).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(registerReq.getUsername()));
    }
}