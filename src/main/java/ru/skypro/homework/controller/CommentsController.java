package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.commentDTO.CommentDTO;
import ru.skypro.homework.dto.commentDTO.ResponseWrapperComment;
import ru.skypro.homework.service.CommentsService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/{id}/comments")
    public ResponseWrapperComment getComments(@PathVariable Integer id){
        return commentsService.getAll(id);
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable Integer id, @RequestBody CommentDTO commentDTO, Authentication authentication){
        return commentsService.addComment(id, commentDTO, authentication);
    }

    @PreAuthorize("commentsService.getById(#commentId).author.username" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId, @PathVariable("adId") Integer adId){
        commentsService.deleteComment(commentId, adId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("commentsService.getById(#commentId).author.username" +
            "== authentication.principal.username")
    @PatchMapping(value = "/{adId}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable("commentId") Integer commentId, @PathVariable("adId") Integer adId, @RequestBody CommentDTO commentDTO){
        return commentsService.update(commentId, adId, commentDTO);
    }
}
