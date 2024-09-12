package com.end2end.application.events.listener;

import com.end2end.application.events.RegistrationCompleteEvent;
import com.end2end.application.registration.token.VerificationTokenService;
import com.end2end.application.user.User;
import com.end2end.application.utility.EmailSender;
import com.end2end.application.utility.EmailTemplate;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.saveUserVerificationToken(user, token);

        String url = event.getConfirmationUrl() + "/registration/verifyEmail?token=" + token;
        String subject = "User Account Verification";
        String senderEmail = System.getenv("SPRING_MAIL_USERNAME");
        senderEmail = senderEmail != null ? senderEmail : "kerciuuu@gmail.com";

        String senderName = "End To End Application";

        HashMap<String, String> emailContents = EmailTemplate.verificationEmail(subject, senderEmail, senderName, url, user);
        try {
            EmailSender.send(mailSender, emailContents);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
