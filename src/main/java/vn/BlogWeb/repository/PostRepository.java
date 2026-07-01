package vn.BlogWeb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BlogWeb.model.Post;
import vn.BlogWeb.model.Tag;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTagsContains(Tag tag);
}
