package vn.BlogWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BlogWeb.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, long id);

    Optional<Role> findByIdOrName(Long id, String email);
}
