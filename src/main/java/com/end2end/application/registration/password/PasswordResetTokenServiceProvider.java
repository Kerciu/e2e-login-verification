package com.end2end.application.registration.password;

import com.end2end.application.user.User;

import java.util.Optional;

public interface PasswordResetTokenServiceProvider {
    void createUserPasswordResetToken(User user, String passwordResetToken);

    String validatePasswordResetToken(String token);

    Optional<User> findUserByPasswordResetToken(String token);

    void resetPassword(User user, String newPassword);
}
