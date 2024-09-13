package com.end2end.application.registration;

import com.end2end.application.events.RegistrationCompleteEvent;
import com.end2end.application.events.listener.RegistrationCompleteEventListener;
import com.end2end.application.registration.password.PasswordResetTokenServiceProvider;
import com.end2end.application.registration.token.VerificationToken;
import com.end2end.application.registration.token.VerificationTokenService;
import com.end2end.application.user.User;
import com.end2end.application.user.UserServiceProvider;
import com.end2end.application.utility.EmailSender;
import com.end2end.application.utility.EmailTemplate;
import com.end2end.application.utility.UrlManager;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserServiceProvider userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService verificationTokenService;
    private final PasswordResetTokenServiceProvider passwordResetTokenService;
    private final RegistrationCompleteEventListener registrationCompleteEventListener;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registrationRequest, HttpServletRequest request)
    {
        User user = userService.registerUser(registrationRequest);

        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlManager.getApplicationUrl(request)));

        return "redirect:/registration/registration-form?success";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> tokenFound = verificationTokenService.findByToken(token);
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

    @GetMapping("/forgot-password-request")
    public String forgotPassword() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordRequest(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        User user = userService.findUserByEmail(email);

        if (user == null) {
            return "redirect:/forgot-password-request?not_found";
        }

        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createUserPasswordResetToken(user, passwordResetToken);

        String url = UrlManager.getApplicationUrl(request) + "/registration/password-reset-form?token=" + passwordResetToken;
        String subject = "User Account Password Reset";
        String senderEmail = System.getenv("SPRING_MAIL_USERNAME");
        senderEmail = senderEmail != null ? senderEmail : "kerciuuu@gmail.com";

        String senderName = "End To End Application";

        HashMap<String, String> emailContents = EmailTemplate.createEmail(subject, senderEmail, senderName, url, user, false);
        try {
            EmailSender.send(registrationCompleteEventListener.getMailSender(), emailContents);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/registration/forgot-password-request?success";
    }

    @GetMapping("/reset-password-form")
    public String passwordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset-form";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(token);

        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/error?invalid_token";
        }

        Optional<User> user = passwordResetTokenService.findUserByPasswordResetToken(token);

        if (user.isPresent()) {
            passwordResetTokenService.resetPassword(user.get(), newPassword);
            return "redirect:/login?reset_success";
        }

        else return "redirect:/error?not_found";
    }
}
