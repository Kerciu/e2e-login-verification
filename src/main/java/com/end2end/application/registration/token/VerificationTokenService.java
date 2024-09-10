package com.end2end.application.registration.token;

import com.end2end.application.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements VerificationTokenServiceProvider {
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public String validateToken(String token) {
        return null;
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {

    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
