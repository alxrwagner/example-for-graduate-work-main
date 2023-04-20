package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;


public interface CommentsService {

    ResponseWrapperComment getAll(Long adsId);

    CommentDTO addComment(Long id, CommentDTO commentDTO);

    void deleteComment(Long adsId, Long commentId);

    CommentDTO update(Long adsId, Long commentId, CommentDTO commentDTO);
}
