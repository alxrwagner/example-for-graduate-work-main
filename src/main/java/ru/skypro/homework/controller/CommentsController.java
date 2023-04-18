package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping(value = "/ads")
public class CommentsController {

    @GetMapping(value = "/{id}/comments")
    public ResponseWrapperComment getComments(@PathVariable Long id){
        return new ResponseWrapperComment();
    }

    @PostMapping(value = "/{id}/comments")
    public CommentDTO addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return new CommentDTO();
    }

    @DeleteMapping(value = "/{adId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long adId, @PathVariable Long commentId){
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "{adId}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable Long adId, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO){
        return new CommentDTO();
    }
}
