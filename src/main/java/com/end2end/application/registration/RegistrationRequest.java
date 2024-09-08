package com.end2end.application.registration;

import com.end2end.application.user.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<Role> roles;
}
