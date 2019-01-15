package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.sound.midi.Sequence;
import java.util.LinkedList;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut
    protected LinkedList<Case> prochain_deplacement;

    public LinkedList<Case> getProchain_deplacement() {
        return prochain_deplacement;
    }

    public void setProchain_deplacement(LinkedList<Case> chemin) {
        this.prochain_deplacement = chemin;
    }

    public Ennemis(Texture t,boolean vivant, Case c, int pm){
        super(t);
        this.c=c;
        this.vivant=vivant;
        this.pm=pm;
        setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
    }



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
        SequenceAction seq=new SequenceAction();
        Case actuel=c;
        while(!prochain_deplacement.isEmpty() && i>0){
            Case prochaine=prochain_deplacement.removeFirst();
            if(actuel.posX()!=prochaine.posX()){
                if(actuel.posX()<prochaine.posX()){
                }
                else {

                }
            }
            if(actuel.posY()!=prochaine.posY()){
                if(actuel.posY()<prochaine.posY()){

                }
                else {

                }
            }
            actuel=prochaine;
            i--;

        }

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