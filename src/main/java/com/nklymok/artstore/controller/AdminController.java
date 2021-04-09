package com.nklymok.artstore.controller;

import com.nklymok.artstore.enums.ArtworkCategory;
import com.nklymok.artstore.model.Artwork;
import com.nklymok.artstore.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ArtworkService artworkService;

    @Autowired
    public AdminController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public String showAdminPanel(Model model) {
        model.addAttribute("featuredImages", artworkService.getAllArtwork(ArtworkCategory.FEATURED));
        return "admin";
    }

    @PostMapping("/add_featured")
    public String addFeatured(@ModelAttribute("file") MultipartFile file,
                              @ModelAttribute("name") String name,
                              @ModelAttribute("category") ArtworkCategory category,
                              @ModelAttribute("dimensions") String dimensions) {
            artworkService.addArtwork(new Artwork(name, category, dimensions, file.getOriginalFilename()), file);
        return "redirect:/admin";
    }

}
