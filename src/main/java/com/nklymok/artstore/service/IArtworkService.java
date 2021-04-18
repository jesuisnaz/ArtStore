package com.nklymok.artstore.service;

import com.nklymok.artstore.enums.ArtworkCategory;
import com.nklymok.artstore.model.Artwork;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArtworkService {

    List<Artwork> getAllArtwork(ArtworkCategory category);

    void addArtwork(Artwork artwork, MultipartFile file);

}
