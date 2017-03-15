package com.m2dl.mobe.miniprojetandroid.models;

/**
 * Created by seb on 15/03/17.
 */

public class Photo {

    /**
     * L'id de l'utilisateur qui a pris la photo.
     */
    private String idUtilisateur;

    /**
     * Le nom de la photo.
     */
    private String nom;

    /**
     * La latitude du lieu de prise de photo.
     */
    private double latitude;

    /**
     * La longitude du lieu de prise de photo.
     */
    private double longitude;

    /**
     * La criticité de la photo.
     */
    private String criticite;

    public Photo(String idUtilisateur, String nom, double latitude, double longitude, String criticite) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.criticite = criticite;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCriticite(String criticite) {
        this.criticite = criticite;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCriticite() {
        return criticite;
    }
}
