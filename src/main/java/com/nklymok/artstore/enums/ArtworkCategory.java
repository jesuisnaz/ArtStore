package com.nklymok.artstore.enums;

public enum ArtworkCategory {
    FEATURED("featured");

    private final String category;

    ArtworkCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
