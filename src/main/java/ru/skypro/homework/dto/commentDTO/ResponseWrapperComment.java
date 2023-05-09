package ru.skypro.homework.dto.commentDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private Integer count = 0;
    private List<CommentDTO> results;
}
