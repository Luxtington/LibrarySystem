package ru.luxtington.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.luxtington.library.model.Person;
import ru.luxtington.library.service.AuthService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/lib/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(@ModelAttribute("person") Person person) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            authService.register(person);
            return "redirect:login";
        } catch (RuntimeException e) {
            bindingResult.rejectValue("username", "error.person", e.getMessage());
            return "register";
        }
    }
} 