package com.nklymok.artstore.repository;

import com.nklymok.artstore.model.Artwork;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends CrudRepository<Artwork, Long> {

    List<Artwork> getAllByCategory(String category);

}
