package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.commentDTO.CommentDTO;
import ru.skypro.homework.dto.commentDTO.ResponseWrapperComment;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepos;
import ru.skypro.homework.repository.CommentRepos;
import ru.skypro.homework.repository.UserRepos;
import ru.skypro.homework.validator.Validator;

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

    public ResponseWrapperComment getAll(Integer adsId) {
        ResponseWrapperComment wrapper = new ResponseWrapperComment();
        wrapper.setResults(commentRepos.findAllByAdsPk(adsId).stream().map(CommentMapper::mapToDTO).collect(Collectors.toList()));
        wrapper.setCount(wrapper.getResults().size());
        return wrapper;
    }

    public CommentDTO addComment(Integer id, CommentDTO commentDTO, Authentication authentication) {
        Comment comment = CommentMapper.mapFromDto(Validator.checkValidateObj(commentDTO));
        User user = userRepos.findByUsername(authentication.getName()).orElseThrow(NotFoundException::new);
        comment.setAuthor(user);
        comment.setAds(adsRepos.findById(id).orElseThrow(NotFoundException::new));
        commentRepos.save(comment);
        return CommentMapper.mapToDTO(comment);
    }

    @Transactional
    public void deleteComment(Integer commentId, Integer adId) {
        commentRepos.deleteByPkAndAdsPk(Validator.checkValidateObj(commentId), adId);
    }

    @Transactional
    public CommentDTO update(Integer commentId, Integer adsId, CommentDTO commentDTO) {
        Comment comment = commentRepos.findByPkAndAdsPk(commentId, adsId).orElseThrow(NotFoundException::new);
        comment.setText(commentDTO.getText());
        return CommentMapper.mapToDTO(commentRepos.save(comment));
    }

    public Comment getById(Integer id) {
        return commentRepos.findById(id).orElseThrow(NotFoundException::new);
    }
}
