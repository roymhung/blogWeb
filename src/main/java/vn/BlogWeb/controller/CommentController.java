package vn.BlogWeb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.ApiResponse;
import vn.BlogWeb.model.Comment;
import vn.BlogWeb.model.dto.CommentResponseDTO;
import vn.BlogWeb.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<ApiResponse<Comment>> postComment(@Valid @RequestBody Comment comment) {
        Comment savedComment = this.commentService.createComment(comment);

        return ApiResponse.created(savedComment);
    }

    @GetMapping("/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> getComments() {
        List<CommentResponseDTO> comments = this.commentService.fetchComments();

        return ApiResponse.success(comments);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> getComment(@PathVariable Long id) {
        CommentResponseDTO comment = this.commentService.fetchCommentById(id);

        return ApiResponse.success(comment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> putComment(@PathVariable Long id,
            @Valid @RequestBody Comment inputComment) {
        CommentResponseDTO comment = this.commentService.updateCommentById(id, inputComment);

        return ApiResponse.success(comment);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Long id) {
        this.commentService.deleteCommentById(id);

        return ApiResponse.success("ok");
    }

}
