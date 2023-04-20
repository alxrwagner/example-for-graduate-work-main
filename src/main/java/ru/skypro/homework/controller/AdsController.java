package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.service.AdsService;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads")
public class AdsController {

    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAds(){
        return ResponseEntity.ok(adsService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> addAd(@RequestBody CreateAds createAds){
        return ResponseEntity.ok(adsService.create(createAds));
    }

//    @GetMapping(value = "/{id}")
//    public FullAds getAds(@PathVariable Long id, ){
//        return new FullAds();
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Long id){
        return  ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Long id, @RequestBody CreateAds createAds){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getAdsMe(){
        return ResponseEntity.ok().build();
    }

//    @PatchMapping(value = "/{id}/image")
//    public String[] updateImage(@PathVariable Long id, Image image){
//        return null;
//    }
}
