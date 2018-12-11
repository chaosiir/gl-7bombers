package com.bomber.game;

public class Personnage {

    private boolean vivant;
    private Case c;
    private int taille;//taille de l'explosion de la bombe, 2 par defaut
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

    public void deplacerHaut(){
        if (c.getMap().getGrille()[c.getX()][c.getY()+1].getMur()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()+1].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()+1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()][c.getY()+1]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()+1].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerBas(){
        if (c.getMap().getGrille()[c.getX()][c.getY()-1].getMur()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()-1].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()-1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()][c.getY()-1]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()-1].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerDroite(){
        if (c.getMap().getGrille()[c.getX()+1][c.getY()].getMur()==null &&
                c.getMap().getGrille()[c.getX()+1][c.getY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()+1][c.getY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()+1][c.getY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()+1][c.getY()].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action(this);}
        }
    }

    public void deplacerGauche(){
        if (c.getMap().getGrille()[c.getX()-1][c.getY()].getMur()==null &&
                c.getMap().getGrille()[c.getX()-1][c.getY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()-1][c.getY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()-1][c.getY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()-1][c.getY()].setPersonnage(this);
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
