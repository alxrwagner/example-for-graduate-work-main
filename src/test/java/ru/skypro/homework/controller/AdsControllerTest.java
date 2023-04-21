package ru.skypro.homework.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class AdsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllAds() {
    }

    @Test
    void addAds() {
    }

    @Test
    void getAds() {
    }

    @Test
    void removeAd() {
    }

    @Test
    void searchByTitle() {
    }

    @Test
    void updateAds() {
    }

    @Test
    void getAdsMe() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void showImage() {
    }
}