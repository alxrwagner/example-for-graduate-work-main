package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL})
    private List<Ads> ads;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL})
    private List<Comment> comments;
}
