package com.nklymok.artstore.controller;

import com.nklymok.artstore.enums.ArtworkCategory;
import com.nklymok.artstore.service.IArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    private final IArtworkService artworkService;

    @Autowired
    public AppController(IArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public String showLanding(Model model) {
        model.addAttribute("featuredImages",
                artworkService.getAllArtwork(ArtworkCategory.FEATURED));
        return "index";
    }

}

