package com.nklymok.artstore.repository;

import com.nklymok.artstore.enums.ArtworkCategory;
import com.nklymok.artstore.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> getAllByCategory(ArtworkCategory category);

}
