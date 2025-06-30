package vn.scrip.movie_lab.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.scrip.movie_lab.dto.request.ForgotPasswordRequest;
import vn.scrip.movie_lab.dto.request.ResetPasswordRequest;
import vn.scrip.movie_lab.entity.ActiveToken;
import vn.scrip.movie_lab.entity.User;
import vn.scrip.movie_lab.enums.TokenType;
import vn.scrip.movie_lab.exception.BadRequestException;
import vn.scrip.movie_lab.exception.NotFoundException;
import vn.scrip.movie_lab.repository.ActiveTokenRepository;
import vn.scrip.movie_lab.repository.UserRepository;
import vn.scrip.movie_lab.service.EmailService;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final UserRepository userRepository;
    private final ActiveTokenRepository tokenRepository;
    private final EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Email không tồn tại"));

        String token = UUID.randomUUID().toString();
        tokenRepository.save(new ActiveToken(token, user, TokenType.FORGOT_PASSWORD));

        String link = "http://localhost:8080/dat-lai-mat-khau?token=" + token;
        emailService.send(user.getEmail(), "Đặt lại mật khẩu", "Click để đặt lại mật khẩu: " + link);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        ActiveToken token = tokenRepository.findByTokenAndType(request.getToken(), TokenType.FORGOT_PASSWORD)
                .orElseThrow(() -> new NotFoundException("Token không hợp lệ"));

        if (token.getConfirmedAt() != null || token.isExpired()) {
            throw new BadRequestException("Token hết hạn hoặc đã dùng");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Xác nhận mật khẩu không khớp");
        }

        User user = token.getUser();
        user.setPassword(request.getPassword()); // nên mã hoá trước khi lưu
        userRepository.save(user);

        token.setConfirmedAt(LocalDateTime.now());
        tokenRepository.save(token);

        return ResponseEntity.ok().build();
    }
}


