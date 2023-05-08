package com.agency04.devcademy.staycation.model;

public enum AccommodationType {
    HOTEL("Hotel"),
    APARTMENT("Apartment"),
    RESORT("Resort"),
    VILLA("Villa"),
    CABIN("Cabin"),
    MOBILE_HOME("Mobile Home");

    private String name;

    AccommodationType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
