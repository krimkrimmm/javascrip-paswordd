package vn.scrip.movie_lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.scrip.movie_lab.entity.ActiveToken;
import vn.scrip.movie_lab.enums.TokenType;
import vn.scrip.movie_lab.repository.ActiveTokenRepository;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final ActiveTokenRepository tokenRepository;

    @GetMapping("/quen-mat-khau")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/dat-lai-mat-khau")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        ActiveToken activeToken = tokenRepository.findByTokenAndType(token, TokenType.FORGOT_PASSWORD)
                .orElse(null);
        if (activeToken == null || activeToken.isExpired()) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }
}
