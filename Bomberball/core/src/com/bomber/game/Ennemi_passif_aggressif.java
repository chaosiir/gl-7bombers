package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;

public class Ennemi_passif_aggressif extends Ennemis {

    public Ennemi_passif_aggressif(boolean vivant,Case c,int pm) {
        super(Bomberball.multiTexture[25]);
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.vivant=vivant;
        this.c=c;
        this.pm=pm;

    }
}
