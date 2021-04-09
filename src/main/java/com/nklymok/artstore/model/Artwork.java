package com.nklymok.artstore.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "dimensions")
    private String dimensions;

    @NotBlank
    @Column(name = "document_name")
    private String documentName;

    @Transient
    private String valueBase64;

    public Artwork() {
    }

    public Artwork(@NotBlank String name, @NotBlank String category, String dimensions, @NotBlank String documentName) {
        this.name = name;
        this.category = category;
        this.dimensions = dimensions;
        this.documentName = documentName;
    }

    public Artwork(@NotBlank String name, @NotBlank String category, String dimensions) {
        this.name = name;
        this.category = category;
        this.dimensions = dimensions;
    }
}
