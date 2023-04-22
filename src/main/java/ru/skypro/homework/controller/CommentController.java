package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
public class CommentController {

    private final CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @Operation(summary = "Получить комментарии объявления", tags = "Комментарии")
    @GetMapping("/{id}/comments")
    public ResponseWrapperComment getComments(@PathVariable Integer id) {
        return commentsService.getAll(id);
    }

    @Operation(
            summary = "Добавление нового комментария к объявлению", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable Integer id, @RequestBody CommentDTO commentDTO, Authentication authentication) {
        return commentsService.addComment(id, commentDTO, authentication);
    }

    @Operation(
            summary = "Удалить комментарий", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PreAuthorize("@commentsService.getById(#commentId).author.username" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Integer commentId, @PathVariable("adId") Integer adId) {
        commentsService.deleteComment(commentId, adId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Обновить комментарий", tags = "Комментарии",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentDTO.class))}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
            }
    )
    @PreAuthorize("@commentsService.getById(#commentId).author.username" +
            "== authentication.principal.username or hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{adId}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable("commentId") Integer commentId, @PathVariable("adId") Integer adId, @RequestBody CommentDTO commentDTO) {
        return commentsService.update(commentId, adId, commentDTO);
    }
}
