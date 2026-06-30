package vn.BlogWeb.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.BlogWeb.model.Comment;
import vn.BlogWeb.model.Post;
import vn.BlogWeb.model.Role;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {

    private int id;

    private String name;

    private String email;

    private String password;

    private String address;

    private Role role;

    private List<Post> posts;

    private List<Comment> comments;

}
