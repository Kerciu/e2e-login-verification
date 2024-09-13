package com.end2end.application.registration.password;

import com.end2end.application.user.User;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class PasswordResetTokenService implements PasswordResetTokenServiceProvider {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public void createUserPasswordResetToken(User user, String passwordResetToken) {

    }

    @Override
    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken.isEmpty()) {
            return "invalid";
        }

        Calendar calendar = Calendar.getInstance();
        long expirationTimeLeft = passwordResetToken.get().getExpirationTime().getTime();
        long time = calendar.getTime().getTime();
        if (expirationTimeLeft - time <= 0) {
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordResetToken(String token) {
        return Optional.empty();
    }

    @Override
    public void resetPassword(User user, String newPassword) {

    }
}
