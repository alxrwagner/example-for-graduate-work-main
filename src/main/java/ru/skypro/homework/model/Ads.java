package ru.skypro.homework.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Data
@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String description;
    private byte[] image;
    private Integer price;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
