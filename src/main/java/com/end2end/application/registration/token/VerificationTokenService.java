package com.end2end.application.registration.token;

import com.end2end.application.user.User;
import com.end2end.application.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements VerificationTokenServiceProvider {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (token.isEmpty()) {
            return "Invalid verification token";
        }

        User user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.get().getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            return "expired";
        }

        user.setActive(true);
        userRepository.save(user);

        return "valid";
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
