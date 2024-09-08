package com.end2end.application.user;

import java.util.Collection;

public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    private boolean isActive = false;

    private Collection<Role> roles;

}
