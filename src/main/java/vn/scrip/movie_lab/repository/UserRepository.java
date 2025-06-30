package vn.scrip.movie_lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.scrip.movie_lab.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
