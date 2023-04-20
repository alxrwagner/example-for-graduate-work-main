package ru.skypro.homework.dto.commentDTO;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private List<CommentDTO> results;
}
