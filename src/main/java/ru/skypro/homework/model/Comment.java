package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    private String authorImage;
    private Integer createdAt;
    private String text;
    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
}
