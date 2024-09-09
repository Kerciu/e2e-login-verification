package com.end2end.application.user;

import com.end2end.application.registration.RegistrationRequest;

import java.util.List;

public interface UserServiceProvider {
    List<User> getAllUsers();
    User registerUser(RegistrationRequest registrationRequest);
    User findUserByEmail(String email);
}
