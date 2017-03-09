package com.m2dl.mobe.miniprojetandroid.occupationru;

import java.util.Date;

/**
 * Created by Blue on 06/03/2017.
 */

public class OccupationPonctuel {
    private String heure;

    private int nombrePersonne;

    public OccupationPonctuel(String heure,int nombrePersonne){
        this.heure=heure;
        this.nombrePersonne=nombrePersonne;
    }

    public String getHeure(){return this.heure;}

    public int getNombrePersonne(){return this.nombrePersonne;}

}
