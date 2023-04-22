package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.commentDTO.CommentDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.repository.CommentRepos;
import ru.skypro.homework.repository.UserRepos;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvcComment;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AdsRepos adsRepos;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private CommentRepos commentRepos;
    private final User user = new User();
    private final Ads ads = new Ads();
    private final Comment comment = new Comment();

    @BeforeEach
    void setUp() {
        user.setUsername("1@mail.ru");
        user.setFirstName("firstTest");
        user.setLastName("lastTest");
        user.setPhone("+79990002233");
        user.setPassword(encoder.encode("1234qwer"));
        user.setEnabled(true);
        user.setRole(Role.USER);
        userRepos.save(user);

        ads.setPrice(200);
        ads.setDescription("about");
        ads.setTitle("title");
        ads.setAuthor(user);
        adsRepos.save(ads);

        comment.setText("text");
        comment.setAds(ads);
        comment.setAuthor(user);
        comment.setCreatedAt(Instant.now());
        commentRepos.save(comment);
    }

    @AfterEach
    void tearDown() {
        commentRepos.deleteAll();
        adsRepos.deleteAll();
        userRepos.deleteAll();
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void getComments() throws Exception {
        mockMvcComment.perform(get("/ads/" + ads.getPk() + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.count").isNumber());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void addComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("text2");
        mockMvcComment.perform(post("/ads/" + ads.getPk() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("text2"));

    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void deleteComment() throws Exception {
        mockMvcComment.perform(delete("/ads/" + ads.getPk() + "/comments/" + comment.getPk()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer")
    void deleteComment_withOtherUser() throws Exception {
        mockMvcComment.perform(delete("/ads/" + ads.getPk() + "/comments/" + comment.getPk()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer", roles = "ADMIN")
    void deleteComment_withRoleAdmin() throws Exception {
        mockMvcComment.perform(delete("/ads/" + ads.getPk() + "/comments/" + comment.getPk()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void updateComment() throws Exception {
        mockMvcComment.perform(patch("/ads/" + ads.getPk() + "/comments/" + comment.getPk())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"text\": \"newText\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("newText"));
    }
}