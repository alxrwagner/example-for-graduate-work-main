package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.service.CommentsService;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads/{id}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping
    public ResponseWrapperComment getComments(@PathVariable Long id){
        return commentsService.getAll(id);
    }

    @PostMapping
    public CommentDTO addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return commentsService.addComment(id, commentDTO);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long id, @PathVariable Long commentId){
        commentsService.deleteComment(id, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{commentId}")
    public CommentDTO updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO){
        return commentsService.update(id,commentId, commentDTO);
    }
}
