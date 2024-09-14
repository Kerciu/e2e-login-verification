package com.end2end.application.registration.token;

import com.end2end.application.user.User;

import java.util.Optional;

public interface VerificationTokenServiceProvider {

    String validateToken(String token);

    void saveUserVerificationToken(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(Long id);
}
