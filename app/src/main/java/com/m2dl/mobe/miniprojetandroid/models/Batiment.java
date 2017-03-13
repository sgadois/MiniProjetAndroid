package com.m2dl.mobe.miniprojetandroid.models;

import java.util.ArrayList;

/**
 * Created by seb on 04/03/17.
 */

public class Batiment {

    private String nom;

    private Double latitude;

    private Double longitude;

    private String description;

    private ArrayList<OccupationPonctuel> occupationPonctuels;

    public Batiment() {}

    public Batiment(String nom, Double latitude, Double longitude, String description) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.occupationPonctuels= new ArrayList<>();
    }

    public void setNom(String nom) { this.nom = nom; }

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

    public ArrayList<OccupationPonctuel> getOccupationsPonctuels(){ return this.occupationPonctuels; }


}
