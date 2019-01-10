package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;

public class Ennemi_actif_aggressif extends Ennemis {

    public Ennemi_actif_aggressif(boolean vivant,Case c,int pm) {
        super(Bomberball.multiTexture[24]);
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.vivant=vivant;
        this.c=c;
        this.pm=pm;
    }
}
