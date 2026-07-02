package vn.BlogWeb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceNotFoundException;
import vn.BlogWeb.model.Comment;
import vn.BlogWeb.model.dto.CommentResponseDTO;
import vn.BlogWeb.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDTO convertCommentToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getId());
        dto.setPostId(comment.getPost().getId());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setApproved(comment.isApproved());

        return dto;
    }

    public Comment createComment(Comment inputComment) {
        return this.commentRepository.save(inputComment);
    }

    public List<CommentResponseDTO> fetchComments() {
        return this.commentRepository.findAll().stream().map(this::convertCommentToDTO).toList();
    }

    public CommentResponseDTO fetchCommentById(Long id) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        return convertCommentToDTO(comment);
    }

    public CommentResponseDTO updateCommentById(Long id, Comment inputComment) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        // Chỉ cập nhật trạng thái approved
        comment.setApproved(inputComment.isApproved());

        Comment updatedComment = this.commentRepository.save(comment);

        return convertCommentToDTO(updatedComment);
    }

    public void deleteCommentById(Long id) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        this.commentRepository.delete(comment);
    }
}
