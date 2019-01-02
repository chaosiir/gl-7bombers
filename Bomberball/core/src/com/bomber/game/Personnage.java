package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Personnage extends Image {

    private boolean vivant;
    private Case c;
    private int taille;//taille de l'explosion de la bombe, 3 par defaut
    private int nbBombe;//bombe posable par tour, 1 par défaut
    private int pm;//points de mouvement, 5 par defaut
    private boolean poussee;


    public Personnage(boolean vivant, Case c, int taille) {
        super(Bomberball.perso.findRegion("pdown2"));
        this.setName("Personnage");
        this.setSize(Bomberball.taillecase,Bomberball.taillecase);
        this.vivant = vivant;
        this.c = c;
        this.taille = taille;
    }

    public Personnage(boolean vivant, Case c, int taille, int nbBombe, int pm) {
        this.vivant = vivant;
        this.c = c;
        this.taille = taille;
        this.nbBombe = nbBombe;
        this.pm = pm;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getNbBombe() {
        return nbBombe;
    }

    public void setNbBombe(int nbBombe) {
        this.nbBombe = nbBombe;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public boolean isPoussee() {
        return poussee;
    }

    public void setPoussee(boolean poussee) {
        this.poussee = poussee;
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

    public void poserBombe(){
        Bombe b=new Bombe(taille,this,c);
        c.setBombe(b);
    }

    public void afficher(Batch b, int x, int y, Texture[] multt){
        Sprite s;
        s=new Sprite(multt[4]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }

    public void deplacerHaut(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pup0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (c.getMap().getGrille()[c.posX()][c.posY()+1].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+1].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()+1]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()+1].setPersonnage(this);
            c=tmp;
            c.addActor(this);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    System.out.println(time);
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pup%d",(int)(time*8)%4)))));

                    return time>1;
                }
            });
            this.setY(getY()-Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(0,Bomberball.taillecase);
            action.setDuration(1);
            this.addAction(action);
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerBas(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pdown0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (c.getMap().getGrille()[c.posX()][c.posY()-1].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-1].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()-1]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()-1].setPersonnage(this);
            c=tmp;
            c.addActor(this);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    System.out.println(time);
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pdown%d",(int)(time*8)%4)))));

                    return time>1;
                }
            });
            this.setY(getY()+Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(0,-Bomberball.taillecase);
            action.setDuration(1);
            this.addAction(action);
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerDroite(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (c.getMap().getGrille()[c.posX()+1][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()+1][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()+1][c.posY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()+1][c.posY()]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()+1][c.posY()].setPersonnage(this);
            c=tmp;
            c.addActor(this);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    System.out.println(time);
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pr%d",(int)(time*8)%4)))));

                    return time>1;
                }
            });
            this.setX(getX()-Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(Bomberball.taillecase,0);
            action.setDuration(1);
            this.addAction(action);
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerGauche(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pl0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (c.getMap().getGrille()[c.posX()-1][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()-1][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()-1][c.posY()]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()-1][c.posY()].setPersonnage(this);
            c=tmp;
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    System.out.println(time);
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pl%d",(int)(time*8)%4)))));

                    return time>1;
                }
            });
            c.addActor(this);
            this.setX(getX()+Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(-Bomberball.taillecase,0);
            action.setDuration(1);
            this.addAction(action);
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void ramasserBonus(){
        if (c.getBonus()!=null){
            c.getBonus().action(this);
        }
    }



}
