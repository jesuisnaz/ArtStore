package com.nklymok.artstore.controller;

import com.nklymok.artstore.exception.UserAlreadyExistsException;
import com.nklymok.artstore.model.User;
import com.nklymok.artstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private IUserService userService;

    @GetMapping
    public String showRegister(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult) throws UserAlreadyExistsException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "redirect:/register?error";
        }
        if (userService.existsByEmail(user.getEmail())) {
            return "redirect:/register?emailExists";
        }
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        userService.saveUser(user);
        System.out.println("Successful registration!");

        return "redirect:/register?success";
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
