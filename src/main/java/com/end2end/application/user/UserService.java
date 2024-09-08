package com.end2end.application.user;

import com.end2end.application.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public class UserService implements UserServiceProvider{
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        return null;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }
}
