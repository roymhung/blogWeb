package vn.BlogWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BlogWeb.model.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
