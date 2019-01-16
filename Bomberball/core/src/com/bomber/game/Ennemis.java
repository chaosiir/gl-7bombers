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

    /**
     * Accesseur du chemin de l'ennemi
     * @return une LinkedList<Case>
     */
    public LinkedList<Case> getProchain_deplacement() {
        return prochain_deplacement;
    }

    /**
     * Modificateur du chemin de l'ennemi
     * @param chemin
     */
    public void setProchain_deplacement(LinkedList<Case> chemin) {
        this.prochain_deplacement = chemin;
    }

    /**
     * Constucteur de la classe Ennemis
     * @param t texture de l'ennemi
     * @param vivant état de l'ennemi
     * @param c case où l'ennemi apparait
     * @param pm nombre de déplacement de l'ennemi
     */
    public Ennemis(Texture t,boolean vivant, Case c, int pm){
        super(t);
        this.c=c;
        this.vivant=vivant;
        this.pm=pm;
        prochain_deplacement=new LinkedList<Case>();
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

    /**
     * Déplace l'ennemi de son nombre de mouvement sur son chemin
     */
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

    /**
     * Vérifie si une case est vide
     * @param caseC
     * @return true si la case est libre
     */
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