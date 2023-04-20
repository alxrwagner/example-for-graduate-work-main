package ru.skypro.homework.dto.commentDTO;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentDTO {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    private String text;
}
