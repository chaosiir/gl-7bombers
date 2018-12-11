package com.bomber.game;

public class Ennemis {
    enum type_ennemis {actif,passif,aggressif};
    private type_ennemis etat;
    public Ennemis(type_ennemis e){
        super();
        etat=e;
    }
    public Ennemis(){}

}
