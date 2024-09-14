package com.end2end.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceProvider userService;

    @GetMapping
    public String getUsers(Model model)
    {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        model.addAttribute("user", user.get());
        return "update-user";
    }
}
