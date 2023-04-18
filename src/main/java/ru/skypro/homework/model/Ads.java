package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
