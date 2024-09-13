package com.end2end.application.registration.password;

import com.end2end.application.user.User;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService implements PasswordResetTokenServiceProvider {
    @Override
    public void createUserPasswordResetToken(User user, String passwordResetToken) {

    }
}
