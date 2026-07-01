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
import vn.BlogWeb.model.dto.PostRequestDTO;
import vn.BlogWeb.model.dto.PostResponseDTO;
import vn.BlogWeb.service.PostService;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponseDTO>> createPost(
            @Valid @RequestBody PostRequestDTO inputPost) {

        PostResponseDTO post = postService.createPost(inputPost);

        return ApiResponse.created(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getPosts() {
        List<PostResponseDTO> posts = this.postService.fetchPosts();
        return ApiResponse.success(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> getPost(@PathVariable Long id) {

        PostResponseDTO post = postService.fetchPostById(id);

        return ApiResponse.success(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> putPost(@PathVariable Long id,
            @Valid @RequestBody PostRequestDTO inputPost) {

        PostResponseDTO post = postService.updatePostById(id, inputPost);

        return ApiResponse.success(post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable Long id) {

        postService.deletePostById(id);

        return ApiResponse.success("ok");
    }
}
