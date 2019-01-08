package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Ennemis extends Actor {
    protected Case c;
    protected Case A;
    protected Case B;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut

    public Ennemis(){}

    public Ennemis(boolean vivant, Case c, int pm) {
        this.vivant = vivant;
        this.c = c;
        this.pm = pm;

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
