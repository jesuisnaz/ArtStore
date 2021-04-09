package com.nklymok.artstore.model;

import com.nklymok.artstore.enums.ArtworkCategory;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "artwork")
@Data
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "category")
    private ArtworkCategory category;

    @Column(name = "dimensions")
    private String dimensions;

    @NotBlank
    @Column(name = "document_name")
    private String documentName;

    @Transient
    private String valueBase64;

    public Artwork() {
    }

    public Artwork(@NotBlank String name, @NotBlank ArtworkCategory category, String dimensions, @NotBlank String documentName) {
        this.name = name;
        this.category = category;
        this.dimensions = dimensions;
        this.documentName = documentName;
    }

    public Artwork(@NotBlank String name, @NotBlank ArtworkCategory category, String dimensions) {
        this.name = name;
        this.category = category;
        this.dimensions = dimensions;
    }
}
