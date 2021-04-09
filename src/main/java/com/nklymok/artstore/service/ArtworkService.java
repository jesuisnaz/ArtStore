package com.nklymok.artstore.service;

import com.nklymok.artstore.enums.ArtworkCategory;
import com.nklymok.artstore.model.Artwork;
import com.nklymok.artstore.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    private final StorageService storageService;

    @Autowired
    public ArtworkService(ArtworkRepository artworkRepository, StorageService storageService) {
        this.artworkRepository = artworkRepository;
        this.storageService = storageService;
    }

    public List<Artwork> getAllArtwork(ArtworkCategory category) {
        List<Artwork> artworks = artworkRepository.getAllByCategory(category);
        for (Artwork artwork : artworks) {
            if (artwork.getValueBase64() != null) continue;
            try {
                artwork.setValueBase64(storageService.getAsBase64(category.getValue()+"/"+artwork.getDocumentName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return artworks;
    }

    public void addArtwork(Artwork artwork, MultipartFile file) {
        if (file.getSize() > 0) {
            storageService.uploadFile(file, artwork.getCategory().getValue());
        } else {
            throw new IllegalArgumentException("File must be at least 1 byte in size");
        }
        artworkRepository.save(artwork);
    }
}
