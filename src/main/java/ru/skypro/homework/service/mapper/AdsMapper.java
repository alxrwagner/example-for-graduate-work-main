package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.adsDTO.AdsDTO;
import ru.skypro.homework.dto.adsDTO.CreateAds;
import ru.skypro.homework.dto.adsDTO.FullAds;
import ru.skypro.homework.model.Ads;

public class AdsMapper {

    public static AdsDTO mapToDTO(Ads ads){
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setPk(ads.getPk());
        adsDTO.setAuthor(ads.getAuthor().getId());
        adsDTO.setTitle(ads.getTitle());
        adsDTO.setPrice(ads.getPrice());
        adsDTO.setImage("/ads/image/" + ads.getPk());
        return adsDTO;
    }

    public static FullAds mapToFullDTO(Ads ads){
        FullAds fullAds = new FullAds();
        fullAds.setPk(ads.getPk());
        fullAds.setTitle(ads.getTitle());
        fullAds.setDescription(ads.getDescription());
        fullAds.setPrice(ads.getPrice());
        fullAds.setImage("/ads/image/" + ads.getPk());
        fullAds.setEmail(ads.getAuthor().getUsername());
        fullAds.setPhone(ads.getAuthor().getPhone());
        return fullAds;
    }

    public static Ads mapFromFullDTO(FullAds fullAds){
        Ads ads = new Ads();
        ads.setPk(fullAds.getPk());
        ads.setTitle(fullAds.getTitle());
        ads.setDescription(fullAds.getDescription());
        ads.setPrice(fullAds.getPrice());
        return ads;
    }

    public static Ads mapFromCreateAds(CreateAds createAds){
        Ads ads = new Ads();
        ads.setTitle(createAds.getTitle());
        ads.setDescription(createAds.getDescription());
        ads.setPrice(createAds.getPrice());
        return ads;
    }
}
