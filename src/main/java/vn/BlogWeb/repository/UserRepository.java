package vn.BlogWeb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BlogWeb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);

    Optional<User> findByNameAndEmail(String name, String email);

    boolean existsByEmail(String email);

    List<User> findByRole_Name(String roleName);

}
