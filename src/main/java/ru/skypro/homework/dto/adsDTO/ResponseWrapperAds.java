package ru.skypro.homework.dto.adsDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private Integer count = 0;
    private List<AdsDTO> results;
}
