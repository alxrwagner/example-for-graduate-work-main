package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.CreateAds;
import ru.skypro.homework.service.AdsService;

import javax.validation.Valid;


@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads")
public class AdsController {

    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.ok(adsService.getAll());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> addAds(@RequestPart("image") MultipartFile imageFile,
                                         @Valid
                                         @RequestPart("properties") CreateAds createAds,
                                         Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adsService.create(imageFile, createAds, authentication));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getById(id));
    }

    @PreAuthorize("@adsService.getById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
        adsService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchByTitle(@RequestParam(required = false) String title) {
        return ResponseEntity.ok(adsService.search(title));
    }

    @PreAuthorize("@adsService.getById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Integer id, @RequestBody CreateAds createAds) {
        return ResponseEntity.ok(adsService.update(id, createAds));
    }

    @GetMapping(value = "/me")
    public ResponseEntity<?> getAdsMe(Authentication authentication) {
        return ResponseEntity.ok(adsService.getMeAll(authentication));
    }

    @PreAuthorize("@adsService.getById(#id).getEmail()" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}/image")
    public ResponseEntity<?> updateImage(@PathVariable Integer id, @RequestParam("image") MultipartFile avatar) {
        return ResponseEntity.ok(adsService.updateImage(id, avatar));
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] showImage(@PathVariable("id") Integer id) {
        return adsService.showImage(id);
    }
}
