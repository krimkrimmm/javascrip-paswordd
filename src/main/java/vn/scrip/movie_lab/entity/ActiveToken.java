package vn.scrip.movie_lab.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import vn.scrip.movie_lab.enums.TokenType;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ActiveToken {
    @Id
    private String token;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiredAt = createdAt.plusMinutes(15);
    private LocalDateTime confirmedAt;

    public ActiveToken(String token, User user, TokenType type) {
        this.token = token;
        this.user = user;
        this.type = type;
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }
}
