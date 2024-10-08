package com.end2end.application.user;

import com.end2end.application.registration.RegistrationRequest;
import com.end2end.application.registration.token.VerificationTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

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

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void updateUser(Long userId, String username, String firstName, String lastName, String email) {
        userRepository.update(userId, username, firstName, lastName, email);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(u -> verificationTokenService.deleteUserToken(u.getId()));
        userRepository.deleteById(userId);
    }
}
