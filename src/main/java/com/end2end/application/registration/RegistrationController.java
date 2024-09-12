package com.end2end.application.registration;

import com.end2end.application.events.RegistrationCompleteEvent;
import com.end2end.application.registration.token.VerificationToken;
import com.end2end.application.registration.token.VerificationTokenRepository;
import com.end2end.application.registration.token.VerificationTokenService;
import com.end2end.application.user.User;
import com.end2end.application.user.UserServiceProvider;
import com.end2end.application.utility.UrlManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserServiceProvider userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registrationRequest, HttpServletRequest request)
    {
        User user = userService.registerUser(registrationRequest);

        // publish the verification email event
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlManager.getApplicationUrl(request)));

        return "redirect:/registration/registration-form?success";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> tokenFound = tokenRepository.findByToken(token);
        if (tokenFound.isPresent() && tokenFound.get().getUser().isActive()) {
            return "redirect:/login?verified";
        }

        String verificationResult = verificationTokenService.validateToken(token);
        return switch (verificationResult.toLowerCase()) {
            case "expired" -> "redirect:/error?expired";
            case "valid" -> "redirect:/login?valid";
            default -> "redirect:/error?invalid";
        };
    }
}
