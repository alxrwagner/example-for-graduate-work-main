package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.exception.NullableParamException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.repository.CommentRepos;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.service.mapper.CommentMapper;

import java.util.stream.Collectors;

@Service
public class CommentsService {
    private final CommentRepos commentRepos;
    private final UserRepos userRepos;
    private final AdsRepos adsRepos;

    public CommentsService(CommentRepos commentRepos, UserRepos userRepos, AdsRepos adsRepos) {
        this.commentRepos = commentRepos;
        this.userRepos = userRepos;
        this.adsRepos = adsRepos;
    }

    public ResponseWrapperComment getAll(Long adsId) {
        ResponseWrapperComment wrapper = new ResponseWrapperComment();
        wrapper.setResults(commentRepos.findAllByAdsPk(adsId).stream().map(CommentMapper::mapToDTO).collect(Collectors.toList()));
        return wrapper;
    }

    public CommentDTO addComment(Long id, CommentDTO commentDTO) {
        if (id == null) {
            throw new NullableParamException();
        }
        Ads ads = adsRepos.findById(id).orElse(null);
        checkIsFound(ads);
        User user = userRepos.findById(commentDTO.getAuthor()).orElse(null);
        checkIsFound(user);
        commentDTO.setPk(null);
        Comment comment = CommentMapper.mapFromDto(commentDTO);
        comment.setAuthor(user);
        comment.setAds(ads);
        commentRepos.save(comment);
        return commentDTO;
    }

    public void deleteComment(Long adsId, Long commentId){
        if (adsId == null || commentId == null){
            throw new NullableParamException();
        }
        Comment comment = commentRepos.findByAdsPkAndPk(commentId, adsId);
        checkIsFound(comment);
        commentRepos.delete(comment);
    }

    public CommentDTO update(Long adsId, Long commentId, CommentDTO commentDTO){
        if (adsId == null || commentId == null){
            throw new NullableParamException();
        }
        Comment comment = commentRepos.findByAdsPkAndPk(commentId, adsId);
        checkIsFound(comment);
        commentRepos.save(CommentMapper.mapFromDto(commentDTO));
        return commentDTO;
    }
    private void checkIsFound(Object obj){
        if (obj == null) {
            throw new NotFoundException();
        }
    }
}
