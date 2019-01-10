package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.LinkedList;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut
    protected LinkedList<Case> prochain_deplacement;
    protected boolean droite;

    public Ennemis(Texture t){
        super(t);
        droite=true;
        prochain_deplacement=new LinkedList<Case>();
        this.setName("Ennemis");
    }


    public void setVivant(boolean v) {
        vivant = v;
    }

    public Case getC(){return c;}

    public void deplacer(){
        int i = pm;

    }
}
