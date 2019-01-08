package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut

    public Ennemis(Texture t){
        super(t);
        this.setName("Ennemis");
    }


    public void setVivant(boolean v) {
        vivant = v;
    }

    public Case getC(){return c;}

    public void deplacer(){
        int i = pm;
        while (i>0){

            if (c.getPersonnage()!=null){
                c.getPersonnage().setVivant(false);
            }
        }
    }
}
