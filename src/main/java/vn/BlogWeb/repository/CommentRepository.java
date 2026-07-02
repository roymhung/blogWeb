package vn.BlogWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BlogWeb.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
