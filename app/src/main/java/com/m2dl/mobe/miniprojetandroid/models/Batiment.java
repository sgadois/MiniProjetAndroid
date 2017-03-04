package com.m2dl.mobe.miniprojetandroid.models;

/**
 * Created by seb on 04/03/17.
 */

public class Batiment {

    private String nom;

    private Double latitude;

    private Double longitude;

    private String description;

    public Batiment() {}

    public Batiment(String nom, Double latitude, Double longitude, String description) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }
}
