package com.example.signup.controller;

import com.example.signup.model.User;
import com.example.signup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if (!user.getPassword().equals(user.getConfirmPassword())){
            bindingResult.addError(new FieldError( "user", "password",  "The passwords do not match"));
        }

        if (userRepository.existsByEmail(user.getEmail())){
            bindingResult.addError(new FieldError("user", "email", "Email address already in use."));
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/signup?register_success";
    }
}
