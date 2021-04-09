package com.nklymok.artstore.enums;

public enum ArtworkCategory {
    FEATURED("featured"),
    SERVICES("services"),
    OTHER("other");

    private final String category;

    ArtworkCategory(String category) {
        this.category = category;
    }

    public String getValue() {
        return category;
    }
}
