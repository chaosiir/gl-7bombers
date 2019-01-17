package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;

public class Ennemi_actif extends Ennemis {

    public Ennemi_actif(boolean vivant,Case c,int pm) {
        super(Bomberball.multiTexture[16]);
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.vivant=vivant;
        this.c=c;
        this.pm=pm;

    }

   /* public Case[] prochain_deplacement (){
        Map m=c.getMap();
        int t[][]=m.chemin_libre();
        int a=c.posX();
        int b=c.posY();


    }
*/



}
