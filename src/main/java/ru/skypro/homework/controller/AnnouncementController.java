package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

import java.awt.*;
import java.awt.print.Pageable;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads")
public class AnnouncementController {

    @GetMapping
    public ResponseWrapperAds getAllAds(Pageable pageable){
        return new ResponseWrapperAds();
    }

    @PostMapping
    public CreateAds addAd(@RequestBody CreateAds createAds){
        return new CreateAds();
    }

    @GetMapping(value = "/{id}")
    public FullAds getAds(@PathVariable Long id){
        return new FullAds();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity removeAd(@PathVariable Long id){
        return  ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public AdsDTO updateAds(@PathVariable Long id, @RequestBody CreateAds createAds){
        return new AdsDTO();
    }

    @GetMapping(value = "/me")
    public ResponseWrapperAds getAdsMe(){
        return new ResponseWrapperAds();
    }

    @PatchMapping(value = "/{id}/image")
    public String[] updateImage(@PathVariable Long id, Image image){
        return null;
    }
}
