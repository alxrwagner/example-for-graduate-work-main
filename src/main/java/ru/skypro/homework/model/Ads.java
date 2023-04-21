package ru.skypro.homework.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String description;
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] image;
    private Integer price;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
    @OneToMany(mappedBy = "ads", cascade = {CascadeType.REMOVE})
    private List<Comment> comments;
}
