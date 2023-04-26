package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.CreateAds;
import ru.skypro.homework.dto.adsDTO.FullAds;
import ru.skypro.homework.dto.adsDTO.ResponseWrapperAds;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.validator.Validator;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class AdsService {
    private final AdsRepos adsRepos;
    private final UserRepos userRepos;

    public AdsService(AdsRepos adsRepos, UserRepos userRepos) {
        this.adsRepos = adsRepos;
        this.userRepos = userRepos;
    }

    public ResponseWrapperAds getAll() {
        ResponseWrapperAds wrap = new ResponseWrapperAds();
        wrap.setResults(adsRepos.findAll().stream().map(AdsMapper::mapToDTO).collect(Collectors.toList()));
        wrap.setCount(wrap.getResults().size());
        return wrap;
    }

    public AdsDTO create(MultipartFile imageFile, CreateAds createAds, Authentication authentication) {
        Validator.checkValidateObj(imageFile);
        Ads ads = AdsMapper.mapFromCreateAds(Validator.checkValidateObj(createAds));
        try {
            byte[] bytes = imageFile.getBytes();
            ads.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User user = userRepos.findByUsername(authentication.getName()).orElseThrow(NotFoundException::new);
        ads.setAuthor(user);
        return AdsMapper.mapToDTO(adsRepos.saveAndFlush(ads));
    }


    public FullAds getById(Integer id) {
        return AdsMapper.mapToFullDTO(adsRepos.findById(id).orElseThrow(NotFoundException::new));
    }

    @Transactional
    public void remove(Integer id) {
        adsRepos.deleteById(id);
    }

    @Transactional
    public AdsDTO update(Integer id, CreateAds createAds) {
        Validator.checkValidateObj(createAds);
        Ads ads = adsRepos.findById(id).orElseThrow(NotFoundException::new);
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        return AdsMapper.mapToDTO(adsRepos.save(ads));
    }

    public ResponseWrapperAds getMeAll(Authentication authentication) {
        ResponseWrapperAds wrap = new ResponseWrapperAds();
        User user = userRepos.findByUsername(authentication.getName()).orElseThrow(NotFoundException::new);
        wrap.setResults(adsRepos.findAllByAuthorId(user.getId()).stream().map(AdsMapper::mapToDTO).collect(Collectors.toList()));
        wrap.setCount(wrap.getResults().size());
        return wrap;
    }

    @Transactional
    public byte[] updateImage(Integer id, MultipartFile avatar) {
        Validator.checkValidateObj(avatar);
        Ads ads = adsRepos.findById(id).orElseThrow(NotFoundException::new);
        try {
            byte[] bytes = avatar.getBytes();
            ads.setImage(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adsRepos.saveAndFlush(ads).getImage();
    }

    public byte[] showImage(Integer id) {
        return adsRepos.findById(id).orElseThrow(NotFoundException::new).getImage();
    }

    public ResponseWrapperAds search(String title) {
        ResponseWrapperAds wrapper = new ResponseWrapperAds();
        wrapper.setResults(adsRepos.findAllByTitleContainsIgnoreCase(title).stream().map(AdsMapper::mapToDTO).collect(Collectors.toList()));
        wrapper.setCount(wrapper.getResults().size());
        return wrapper;
    }
}
