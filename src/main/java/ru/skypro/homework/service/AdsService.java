package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.service.mapper.AdsMapper;

import java.util.stream.Collectors;

@Service
public class AdsService {
    private final AdsRepos adsRepos;

    public AdsService(AdsRepos adsRepos) {
        this.adsRepos = adsRepos;
    }

    public ResponseWrapperAds getAll() {
        ResponseWrapperAds wrap = new ResponseWrapperAds();
        wrap.setResults(adsRepos.findAll().stream().map(AdsMapper::mapToDTO).collect(Collectors.toList()));
        return wrap;
    }

    public Object create(CreateAds createAds) {
        return null;
    }
}
