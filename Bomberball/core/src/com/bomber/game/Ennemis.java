package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.LinkedList;

public abstract class Ennemis extends Actor {
    protected Case c;
    protected boolean vivant;

    public LinkedList<Case> getChemin() {
        return chemin;
    }

    public void setChemin(LinkedList<Case> chemin) {
        this.chemin = chemin;
    }

    protected LinkedList<Case> chemin;


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

}
