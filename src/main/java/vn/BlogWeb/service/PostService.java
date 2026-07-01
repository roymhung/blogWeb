package vn.BlogWeb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BlogWeb.helper.exception.ResourceNotFoundException;
import vn.BlogWeb.model.Post;
import vn.BlogWeb.model.Tag;
import vn.BlogWeb.model.User;
import vn.BlogWeb.model.dto.PostRequestDTO;
import vn.BlogWeb.model.dto.PostResponseDTO;
import vn.BlogWeb.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post convertDTOtoPost(PostRequestDTO dto) {
        Post post = new Post();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        List<Tag> tags = dto.getTags().stream()
                .map(inputTag -> new Tag(inputTag.getId(), inputTag.getName(), null))
                .collect(Collectors.toList());

        post.setTags(tags);

        User user = new User();
        user.setId(dto.getUser().getId());

        post.setUser(user);
        return post;
    }

    public PostResponseDTO convertPostToDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();

        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());

        List<PostResponseDTO.OutputTag> tags = post.getTags().stream().map(tag -> {
            PostResponseDTO.OutputTag t = new PostResponseDTO.OutputTag();
            t.setId(tag.getId());
            t.setName(tag.getName());
            return t;
        }).collect(Collectors.toList());

        dto.setTags(tags);
        return dto;
    }

    public PostResponseDTO createPost(PostRequestDTO postDTO) {
        Post post = postRepository.save(convertDTOtoPost(postDTO));

        return convertPostToDTO(post);
    }

    public List<PostResponseDTO> fetchPosts() {
        return postRepository.findAll().stream().map(this::convertPostToDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO updatePostById(Long id, PostRequestDTO postDTO) {

        Post postInDB = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post không tồn tại."));

        postInDB.setTitle(postDTO.getTitle());
        postInDB.setContent(postDTO.getContent());

        if (postDTO.getTags() != null && !postDTO.getTags().isEmpty()) {

            List<Tag> tags = postDTO.getTags().stream()
                    .map(inputTag -> new Tag(inputTag.getId(), inputTag.getName(), null))
                    .collect(Collectors.toList());

            postInDB.setTags(tags);
        }

        postRepository.save(postInDB);
        return convertPostToDTO(postInDB);
    }

    public PostResponseDTO fetchPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post không tồn tại."));

        return convertPostToDTO(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}
