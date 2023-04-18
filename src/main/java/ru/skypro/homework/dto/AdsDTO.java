package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDTO {
    private Long author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
