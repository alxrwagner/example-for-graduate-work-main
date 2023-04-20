package ru.skypro.homework.dto.commentDTO;

import lombok.Data;
import ru.skypro.homework.dto.commentDTO.CommentDTO;

import java.util.List;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private List<CommentDTO> results;
}
