package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Personnage extends Actor {

    private boolean vivant;
    private Case c;
    private int taille;//taille de l'explosion de la bombe, 3 par defaut
    private int nbBombe;//bombe posable par tour, 1 par défaut
    private int pm;//points de mouvement, 5 par defaut
    private boolean poussee;


    public Personnage(boolean vivant, Case c, int taille) {
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
        if (c.getMap().getGrille()[c.posX()][c.posY()+1].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+1].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()+1]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()+1].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerBas(){
        if (c.getMap().getGrille()[c.posX()][c.posY()-1].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-1].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()-1]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()-1].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerDroite(){
        if (c.getMap().getGrille()[c.posX()+1][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()+1][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()+1][c.posY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()+1][c.posY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()+1][c.posY()].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerGauche(){
        if (c.getMap().getGrille()[c.posX()-1][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()-1][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.posX()-1][c.posY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()-1][c.posY()].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void ramasserBonus(){
        if (c.getBonus()!=null){
            c.getBonus().action(this);
        }
    }



}
