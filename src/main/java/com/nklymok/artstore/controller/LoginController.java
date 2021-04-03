package com.nklymok.artstore.controller;

import com.nklymok.artstore.exception.UserAlreadyExistsException;
import com.nklymok.artstore.model.User;
import com.nklymok.artstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLogin(@ModelAttribute("user") User user) {
        return "login";
    }

    @PostMapping
    public String loginUser(@ModelAttribute("user") User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            System.out.println("Errors during validation: something is null");
            return "login";
        }
        if (!userService.correctCredentials(user)) {
            System.out.println("Incorrect credentials or user does not exist");
            return "login";
        }
        System.out.println("Success!");
        return "redirect:/";
    }

}
