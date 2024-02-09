package pl.patrykdepka.iteventsapp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final MessageSource messageSource;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login-form";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied(Model model) {
        model.addAttribute("httpStatus", HttpStatus.FORBIDDEN.value());
        model.addAttribute(
                "errorMessage",
                messageSource.getMessage("exception.AccessDeniedException.message", null, Locale.getDefault())
        );

        return "error";
    }
}
