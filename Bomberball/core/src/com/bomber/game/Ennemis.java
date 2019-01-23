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
    protected Case prochaine;
    protected int pm;//points de mouvement, 3 par defaut
    protected LinkedList<Case> prochain_deplacement;
    protected Action animation;
    boolean teleportation=false;

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
        if(!vivant){
            c.setEnnemi(null);
        }
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
    public abstract void setPortee(int x);


    public void deplacer(){
        int i = pm;
        this.miseAjour();
        SequenceAction seq=new SequenceAction();
        Case actuel=c;
        if (!prochain_deplacement.isEmpty()){
            prochaine=prochain_deplacement.removeFirst();
        }

        if(teleportation){
            prochaine=prochain_deplacement.removeFirst();
            teleportation(prochaine.posX(),prochaine.posY());
        }
        while(!prochain_deplacement.isEmpty() && i>0){
            prochaine=prochain_deplacement.removeFirst();
            if(prochaine.getEnnemi()!=null && prochaine.getEnnemi()!=this){
                break;
            }
            else{
                if(actuel.posX()!=prochaine.posX()){
                    if(actuel.posX()<prochaine.posX()){
                        seq.addAction(deplacementdroite());
                    }
                    else {
                        seq.addAction(deplacementgauche());
                    }
                }
                if(actuel.posY()!=prochaine.posY()){
                    if(actuel.posY()<prochaine.posY()){
                        seq.addAction(deplacementhaut());
                    }
                    else {
                        seq.addAction(deplacementbas());

                    }
                }
                actuel=prochaine;
                i--;

            }


        }
        this.addAction(seq);
    }


    /* fonction permettant de tester si une case est occupée ou non par un mur ou un autre ennemi*/
    public boolean caseLibre(Case caseC){
        Mur mur=caseC.getMur();
        Ennemis ennemi=caseC.getEnnemi();
        if ((ennemi==this||ennemi==null)&&(mur==null)){
            return true;
        }
        else return false;
    }
    public Action deplacementdroite(){
        final Case proch=prochaine;
        SequenceAction seq=new SequenceAction();
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {

                ((Ennemis) target).setAnimationdroite();
                c.setEnnemi(null);
                c= proch;
                c.setEnnemi((Ennemis) target);
                target.setX(-Bomberball.taillecase);

                return true;
            }
        });
        MoveToAction mv= new MoveToAction();
        mv.setPosition(0,0);
        mv.setDuration(0.3f);
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;

    }
    public Action deplacementgauche(){
        final Case proch=prochaine;
        SequenceAction seq=new SequenceAction();
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
                target.setX(0);
                c.setEnnemi(null);
                c=proch;
                c.setEnnemi((Ennemis) target);
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;
    }

    public Action deplacementhaut(){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                c.setEnnemi(null);
                ((Ennemis) target).setAnimationdroite();
                target.setY(-Bomberball.taillecase);
                c=proch;
                c.setEnnemi((Ennemis) target);
                return true;
            }
        });
        MoveToAction mv=new MoveToAction();
        mv.setPosition(0,0);
        mv.setDuration(0.3f);
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }
                return true;
            }
        });
        return seq;

    }
    public Action deplacementbas(){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
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
                target.setY(0);
                c=proch;
                c.setEnnemi((Ennemis) target);
                if(c.getPersonnage()!=null){
                    c.getPersonnage().setVivant(false);
                }

                return true;
            }
        });
        return seq;
    }

    public void teleportation(int x,int y){
        SequenceAction seq=new SequenceAction();
        final Case proch=prochaine;
        final int a=x;
        final int b=y;
        MoveByAction mv=new MoveByAction();
        mv.setAmount(0,Bomberball.taillecase);
        mv.setDuration(0.3f);
        seq.addAction(mv);
        seq.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                c.setEnnemi(null);
                c=c.map.getGrille()[a][b];
                c.setEnnemi((Ennemis) target);
                return true;
            }
        });
        MoveByAction action=new MoveByAction();
        action.setAmount(0,-Bomberball.taillecase);
        action.setDuration(0.3f);
        seq.addAction(action);
        this.addAction(seq);
        teleportation=false;

    }
}