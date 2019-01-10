package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.LinkedList;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;

    public LinkedList<Case> getProchain_deplacement() {
        return prochain_deplacement;
    }

    public void setProchain_deplacement(LinkedList<Case> chemin) {
        this.prochain_deplacement = chemin;
    }

    protected LinkedList<Case> prochain_deplacement;


    public Case getC() {
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }

    public boolean isVivant() {
        return vivant;
    }

    public void setVivant(boolean vivant) {
        this.vivant = vivant;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    protected int pm;//points de mouvement, 5 par defaut

    public Ennemis(){}

    public Ennemis(boolean vivant, Case c, int pm) {
        this.vivant = vivant;
        this.c = c;
        this.pm = pm;
    }

    /* fonction permettant de tester si une case est occupée ou non par un mur ou un autre ennemi*/
    public boolean caseLibre(Case caseC){
        Map m=caseC.getMap();
        Mur mur=caseC.getMur();
        Ennemis ennemi=caseC.getEnnemi();
        if ((ennemi==null)&&(mur==null)){
            return true;
        }
        else return false;
    }
}