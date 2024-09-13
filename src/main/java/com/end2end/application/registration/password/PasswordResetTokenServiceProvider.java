package com.end2end.application.registration.password;

import com.end2end.application.user.User;

public interface PasswordResetTokenServiceProvider {
    void createUserPasswordResetToken(User user, String passwordResetToken);
}
