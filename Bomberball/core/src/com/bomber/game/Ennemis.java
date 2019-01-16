package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

public abstract int getPortee();


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

    public abstract LinkedList<Case> getChemin();

    public abstract void miseAjour();
    public abstract boolean isAgro();

    public void deplacer(){
        int i = pm;
       // miseAjour();
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
                            c=prochaine;
                            target.setX(-Bomberball.taillecase);
                            ((Ennemis) target).setAnimationdroite();
                            return true;
                        }
                    });
                    MoveToAction mv= new MoveToAction();
                    mv.setPosition(0,0);
                    mv.setDuration(0.3f);
                    seq.addAction(mv);
                }
                else {
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            ((Ennemis) target).setAnimationgauche();
                            return true;
                        }
                    });
                    MoveByAction action=new MoveByAction();
                    action.setAmount(-Bomberball.taillecase,0);
                    action.setDuration(0.3f);
                    seq.addAction(action);
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                                c.setEnnemi(null);
                                prochaine.setEnnemi((Ennemis) target);
                                c.removeActor(target);
                                c=prochaine;
                                c.addActor(target);
                                target.setX(0);
                            return true;
                        }
                    });
                }
            }
            if(actuel.posY()!=prochaine.posY()){
                if(actuel.posY()<prochaine.posY()){
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            c.setEnnemi(null);
                            c.removeActor(target);
                            prochaine.setEnnemi((Ennemis) target);
                            c=prochaine;
                            c.addActor(target);
                            target.setY(-Bomberball.taillecase);
                            ((Ennemis) target).setAnimationdroite();
                            return true;
                        }
                    });
                    MoveToAction mv=new MoveToAction();
                    mv.setPosition(0,0);
                    mv.setDuration(0.3f);
                    seq.addAction(mv);
                }
                else {
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                            ((Ennemis) target).setAnimationgauche();
                            return true;
                        }
                    });
                    MoveByAction action=new MoveByAction();
                    action.setAmount(0,-Bomberball.taillecase);
                    action.setDuration(0.3f);
                    seq.addAction(action);
                    seq.addAction(new Action() {
                        @Override
                        public boolean act(float delta) {
                                c.setEnnemi(null);
                                c.setPersonnage(null);
                                c.removeActor(target);
                                prochaine.setEnnemi((Ennemis) target);
                                c=prochaine;
                                c.addActor(target);
                                target.setY(0);
                            return true;
                        }
                    });

                }
            }
            actuel=prochaine;
            i--;

        }
        addAction(seq);

    }


    /* fonction permettant de tester si une case est occupée ou non par un mur ou un autre ennemi*/
    public boolean caseLibre(Case caseC){
        Map m=caseC.getMap();
        Mur mur=caseC.getMur();
        Ennemis ennemi=caseC.getEnnemi();
        if ((ennemi!=this)&&(mur==null)){
            return true;
        }
        else return false;
    }
}