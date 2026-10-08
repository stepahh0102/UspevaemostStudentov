package com.example.untitled.controller;

import com.example.untitled.model.User;
import com.example.untitled.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            model.addAttribute("error", "Введите логин и пароль");
            return "login-form";
        }

        if (!userService.authenticate(username, password)) {
            model.addAttribute("error", "Неверный логин или пароль");
            return "login-form";
        }

        session.setAttribute("user", username);
        return "redirect:/students";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {

        User created = userService.register(username, password);

        if (created == null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            return "register-form";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}