package com.nklymok.artstore.controller;

import com.nklymok.artstore.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StorageService storageService;

    @Autowired
    public AdminController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String showAdminPanel() {
        return "admin";
    }

    @GetMapping("/edit_featured")
    public String showEditFeatured() {
        return "edit_featured";
    }

    @PostMapping("/add_featured")
    public String addFeatured(@ModelAttribute("file") MultipartFile file) {
        if (file.getSize() > 0) {
            storageService.uploadFile(file, "featured");
        }
        return "redirect:/";
    }

}
