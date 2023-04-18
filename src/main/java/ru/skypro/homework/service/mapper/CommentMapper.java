package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.model.Comment;

public class CommentMapper {
    public static Comment mapFromDto(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setPk(commentDTO.getPk());
        comment.setAuthorImage(commentDTO.getAuthorImage());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setText(commentDTO.getText());
        return comment;
    }

    public static CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(comment.getPk());
        commentDTO.setText(comment.getText());
        commentDTO.setAuthorImage(comment.getAuthorImage());
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        return commentDTO;
    }
}
