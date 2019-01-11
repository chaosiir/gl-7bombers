package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.LinkedList;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut
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

    public void miseAjour(){

    }


    public void deplacer(){
        int i = pm;
        miseAjour();
        while(!prochain_deplacement.isEmpty() && pm>0){

        }

    }

    public Ennemis(Texture t){
        super(t);
        prochain_deplacement=new LinkedList<Case>();
        this.setName("Ennemis");
    }




}
