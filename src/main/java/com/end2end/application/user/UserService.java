package com.end2end.application.user;

import com.end2end.application.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest registrationRequest) {
        User user = new User(
                registrationRequest.getUsername(),
                registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                registrationRequest.getEmail(),
                Arrays.asList(new Role("ROLE_USER"))
        );
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
