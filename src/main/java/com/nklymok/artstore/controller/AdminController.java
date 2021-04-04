package com.nklymok.artstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String showAdminPanel() {
        return "admin";
    }

    @GetMapping("/edit_featured")
    public String showEditFeatured() {
        return "edit_featured";
    }

}
