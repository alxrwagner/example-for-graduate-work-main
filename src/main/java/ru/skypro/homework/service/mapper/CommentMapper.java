package ru.skypro.homework.service.mapper;

import ru.skypro.homework.dto.commentDTO.CommentDTO;
import ru.skypro.homework.model.Comment;

import java.time.Instant;

public class CommentMapper {
    public static Comment mapFromDto(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setPk(commentDTO.getPk());
        comment.setText(commentDTO.getText());
        return comment;
    }

    public static CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPk(comment.getPk());
        commentDTO.setText(comment.getText());
        commentDTO.setAuthorImage("/users/me/image/" + comment.getAuthor().getId());
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setCreatedAt(comment.getCreatedAt().toEpochMilli());
        commentDTO.setAuthorFirstName(comment.getAuthor().getFirstName());
        return commentDTO;
    }
}
