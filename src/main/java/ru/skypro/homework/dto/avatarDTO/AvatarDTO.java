package ru.skypro.homework.dto.avatarDTO;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
public class AvatarDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;
}
