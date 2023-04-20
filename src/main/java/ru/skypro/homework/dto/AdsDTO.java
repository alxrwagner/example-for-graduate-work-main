package ru.skypro.homework.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class AdsDTO {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
}
