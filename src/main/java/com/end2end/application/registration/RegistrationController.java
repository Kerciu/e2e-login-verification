package com.end2end.application.registration;

import com.end2end.application.events.RegistrationCompleteEvent;
import com.end2end.application.user.User;
import com.end2end.application.user.UserServiceProvider;
import com.end2end.application.utility.UrlManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserServiceProvider userService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registrationRequest, HttpServletRequest request)
    {
        User user = userService.registerUser(registrationRequest);

        // publish the verification email event
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlManager.getApplicationUrl(request)));

        return "redirect:/registration/registration-form?success";
    }
}
