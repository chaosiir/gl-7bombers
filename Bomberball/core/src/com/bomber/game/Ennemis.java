package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import javax.sound.midi.Sequence;
import java.util.LinkedList;

public abstract class Ennemis extends Image {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut
    protected LinkedList<Case> prochain_deplacement;
    protected Action animation;

    public LinkedList<Case> getProchain_deplacement() {
        return prochain_deplacement;
    }

    public abstract void setAnimationgauche();
    public abstract void setAnimationdroite();
    public abstract void setAnimationdefaite();
    public void setProchain_deplacement(LinkedList<Case> chemin) {
        this.prochain_deplacement = chemin;
    }

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


    public void deplacer(){
        int i = pm;
        miseAjour();
        SequenceAction seq=new SequenceAction();
        Case actuel=c;
        while(!prochain_deplacement.isEmpty() && i>0){
            final Case prochaine=prochain_deplacement.removeFirst();
            if(actuel.posX()!=prochaine.posX()){
                if(actuel.posX()<prochaine.posX()){
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            c.setEnnemi(null);
                            c.removeActor(target);
                            prochaine.setEnnemi((Ennemis) target);
                            target.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
                            return true;
                        }
                    });
                    MoveToAction mv= new MoveToAction();
                    mv.setPosition(0,0);
                    mv.setDuration(0.3f);
                    seq.addAction(mv);
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