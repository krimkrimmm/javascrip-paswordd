package vn.scrip.movie_lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.scrip.movie_lab.entity.ActiveToken;
import vn.scrip.movie_lab.enums.TokenType;
import java.util.Optional;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken, String> {
    Optional<ActiveToken> findByTokenAndType(String token, TokenType type);
}







