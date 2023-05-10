package org.ricknmorty.model;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("unknown");

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
