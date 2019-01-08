package com.bomber.game;

public class Ennemi_passif extends Ennemis{

    public Ennemi_passif(boolean vivant,Case c,int pm){
        super(Bomberball.multiTexture[17]);
        this.vivant=vivant;
        this.c=c;
        this.pm=pm;

    }

}
