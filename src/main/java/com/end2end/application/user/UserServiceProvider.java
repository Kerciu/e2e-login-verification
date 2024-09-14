package com.end2end.application.user;

import com.end2end.application.registration.RegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserServiceProvider {
    List<User> getAllUsers();
    User registerUser(RegistrationRequest registrationRequest);
    User findUserByEmail(String email);

    Optional<User> findById(Long userId);

    void updateUser(Long userId, String username, String firstName, String lastName, String email);

    void deleteUser(Long userId);
}
