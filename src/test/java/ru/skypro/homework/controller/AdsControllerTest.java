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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.adsDTO.CreateAds;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.repository.UserRepos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class AdsControllerTest {

    @Autowired
    MockMvc mockMvcAds;
    @Autowired
    private UserRepos userRepos;
    @Autowired
    private AdsRepos adsRepos;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    private final MockPart image = new MockPart("image", "image", "image".getBytes());

    private final Ads ads = new Ads();
    private final CreateAds createAds = new CreateAds();
    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("1@mail.ru");
        user.setFirstName("firstTest");
        user.setLastName("lastTest");
        user.setPhone("+79990002233");
        user.setPassword(encoder.encode("1234qwer"));
        user.setEnabled(true);
        user.setRole(Role.USER);
        userRepos.save(user);

        createAds.setPrice(100);
        createAds.setTitle("title");
        createAds.setDescription("description");

        ads.setPrice(200);
        ads.setDescription("about");
        ads.setTitle("title");
        ads.setAuthor(user);
        ads.setImage("image".getBytes());

        adsRepos.save(ads);
    }

    @AfterEach
    void tearDown() {
        adsRepos.deleteAll();
        userRepos.deleteAll();
    }

    @Test
    void getAllAds() throws Exception {
        mockMvcAds.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.count").isNumber());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void addAds() throws Exception {
        MockPart created = new MockPart("properties", objectMapper.writeValueAsBytes(createAds));

        mockMvcAds.perform(multipart("/ads")
                        .part(image)
                        .part(created))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pk").isNotEmpty())
                .andExpect(jsonPath("$.pk").isNumber())
                .andExpect(jsonPath("$.title").value(createAds.getTitle()))
                .andExpect(jsonPath("$.price").value(createAds.getPrice()));
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void getAds() throws Exception {
        mockMvcAds.perform(get("/ads/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("about"))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void removeAd() throws Exception {
        mockMvcAds.perform(delete("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer")
    void removeAd_withOtherUser() throws Exception {
        mockMvcAds.perform(delete("/ads/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer", roles = "ADMIN")
    void removeAd_withAdminRole() throws Exception {
        mockMvcAds.perform(delete("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    void searchByTitle() throws Exception {
        mockMvcAds.perform(get("/ads/search?title=title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.results[0].title").value("title"))
                .andExpect(jsonPath("$.count").isNumber());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void updateAds() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"price\": \"15\",\n" +
                        "  \"title\": \"title2\",\n" +
                        " \"description\": \"aboutNew\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title2"));

    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer")
    void updateAds_withAnotherUser() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"price\": \"15\",\n" +
                                "  \"title\": \"title2\",\n" +
                                " \"description\": \"aboutNew\"}"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer", roles = "ADMIN")
    void updateAds_withRoleAdmin() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"price\": \"15\",\n" +
                                "  \"title\": \"title2\",\n" +
                                " \"description\": \"aboutNew\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title2"));

    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void getAdsMe() throws Exception {
        mockMvcAds.perform(get("/ads/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.results").isArray())
                .andExpect(jsonPath("$.count").isNumber());
    }

    @Test
    @WithMockUser(username = "1@mail.ru", password = "1234qwer")
    void updateImage() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk() + "/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.addPart(image);
                            return request;
                        }))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer")
    void updateImage_withOtherUser() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk() + "/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.addPart(image);
                            return request;
                        }))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "2@mail.ru", password = "1234qwer", roles = "ADMIN")
    void updateImage_withRoleAdmin() throws Exception {
        mockMvcAds.perform(patch("/ads/" + ads.getPk() + "/image")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.addPart(image);
                            return request;
                        }))
                .andExpect(status().isOk());

    }

    @Test
    void showImage() throws Exception {
        mockMvcAds.perform(get("/ads/image/" + ads.getPk()))
                .andExpect(status().isOk())
                .andExpect(content().bytes("image".getBytes()));
    }
}