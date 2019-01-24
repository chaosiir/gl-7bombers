package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import java.io.Serializable;

public class Personnage extends Image {

    private boolean vivant;
    private Case c;
    private int taille;//taille de l'explosion de la bombe, 3 par defaut
    private int nbBombe;//bombe posable par tour, 1 par défaut
    private int pm;//points de mouvement, 5 par defaut
    private boolean poussee;
    private int taillepoussee;
    private int id;


    /**
     * Constructeur de la classe Personnage
     * @param vivant etat du personnage
     * @param c case où le personnage apparait
     * @param taille portée des bombes du personnage
     * @param nbBombe nombre de bombes que le personnage peut poser à chaque tour
     * @param pm nombre de déplacement que le personnage peut faire à chaque tour
     * @param id identifiant du personnage
     */
    public Personnage(boolean vivant, Case c, int taille, int nbBombe, int pm,int id) {
        super(Bomberball.perso.findRegion("pdown"+id+"2"));

        this.setName("Personnage");
        this.setSize(Bomberball.taillecase,Bomberball.taillecase);
        this.vivant = vivant;
        this.c = c;
        this.taille = taille;
        this.nbBombe = nbBombe;
        this.pm = pm;
        this.poussee=false;
        this.id=id;

    }
    /**
     * Accesseur de l'identifiant du joueur
     * @return entier
     */
    public int getId(){
        return id;
    }

    /**
     * Modificateur de l'identifiant du joueur
     * @param id
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Accesseur de la portée des bombes du joueur
     * @return entier
     */
    public int getTaille() {
        return taille;
    } //Taille d'une explosion

    /**
     * Modificateur de la paortée des bombes du joueur
     * @param taille
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * Accesseur du nombre de bombes du joueur
     * @return entier
     */
    public int getNbBombe() {
        return nbBombe;
    }

    /**
     * Modificateur du nombre de bombe du joueur
     * @param nbBombe
     */
    public void setNbBombe(int nbBombe) {
        this.nbBombe = nbBombe;
    }

    /**
     * Accesseur du nombre de déplacements du joueur
     * @return entier
     */
    public int getPm() {
        return pm;
    }

    /**
     * Modificateur du nombre de déplacements du joueur
     * @param pm
     */
    public void setPm(int pm) {
        this.pm = pm;
    }

    /**
     * Accesseur de de la poussée du joueur
     * @return boolean
     */
    public boolean isPoussee() {
        return poussee;
    }

    /**
     * Modificateur de la poussée du joueur
     * @param poussee
     */
    public void setPoussee(boolean poussee) {
        this.poussee = poussee;
    }

    /**
     * Accesseur de la case du joueur
     * @return une case
     */
    public Case getC() {
        return c;
    }

    /**
     * Modificateur de la case du joueur
     * @param c
     */
    public void setC(Case c) {
        this.c = c;
    }

    /**
     * Accesseur de l'état du joueur
     * @return boolean
     */
    public boolean isVivant() {
        return vivant;
    }

    /**
     * Modificateur de l'état du joueur
     * @param vivant
     */
    public void setVivant(boolean vivant) {
        this.vivant = vivant;
        if(!vivant){
          c.setPersonnage(null);
        }
    }

    /**
     * Crée une bombe sur la case où se trouve le joueur
     * @return boolean
     */
    public boolean poserBombe() {
        if (c.getBombe() == null && this.nbBombe>0) {
            Bombe b = new Bombe(taille, c);
            c.setBombe(b);
            return true;
        }
        return false;
    }

    /**
     * affiche le joueur
     * @param b
     * @param x abscisse
     * @param y ordonnée
     * @param multt texture
     */

    public void afficher(Batch b, int x, int y, Texture[] multt){
        Sprite s;
        s=new Sprite(multt[4]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }
    /**
     * Déplace le joueur d'une case vers le haut s'il n'y a pas d'obstacles, tout en récupérant les bonus
     * @return boolean
     */
    public boolean deplacerHaut(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pup"+id+"0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideHaut()){

            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()+1]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()+1].setPersonnage((Personnage) this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action();}
            c.addActor(this);
            setY(-Bomberball.taillecase);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;

                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pup"+id+""+(int)(time*8)%4))));

                    return time>0.35;
                }
            });
            MoveByAction action=new MoveByAction();
            action.setAmount(0,Bomberball.taillecase);
            action.setDuration(0.35f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getBombe()!=null){
                c.setBombe(null);
                taillepoussee = 0;
                do {
                    taillepoussee++;
                } while (c.getMap().getGrille()[c.posX()][c.posY()+taillepoussee].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()+taillepoussee].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()+taillepoussee].getMur()==null);

                Bombe b= new Bombe(this.taille,c.getMap().getGrille()[c.posX()][c.posY()+taillepoussee-1]);
                b.setY(-(taillepoussee-1)*Bomberball.taillecase);
                MoveToAction move=new MoveToAction();
                move.setPosition(Bomberball.taillecase/4,Bomberball.taillecase/4);
                move.setDuration(taillepoussee*0.2f);
                MoveByAction attente=new MoveByAction();
                attente.setAmount(0,0);
                attente.setDuration(taillepoussee*0.2f);
                this.addAction(attente);
                c.getMap().getGrille()[c.posX()][c.posY()+taillepoussee-1].setBombe(b);
                b.addAction(move);
            }


            return true;
        }

        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }
    /**
     * Déplace le joueur d'une case vers le bas s'il n'y a pas d'obstacles, tout en récupérant les bonus
     * @return boolean
     */
    public boolean deplacerBas(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pdown"+id+"0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (this.caseVideBas()){


            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pdown"+id+""+(int)(time*8)%4))));
                    if(time>0.35) {
                        Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()-1]);
                        c.setPersonnage(null);
                        c.removeActor(target);
                        c.getMap().getGrille()[c.posX()][c.posY()-1].setPersonnage((Personnage) target);
                        c=tmp;
                        if(c.getBonus()!=null){c.getBonus().action();}
                        c.addActor(target);
                        setY(0);
                    }
                    return time>0.35;
                }
            });

            MoveByAction action=new MoveByAction();
            action.setAmount(0,-Bomberball.taillecase);
            action.setDuration(0.35f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()!=null) {
                taillepoussee = 0;
                do {
                    taillepoussee++;
                } while (c.getMap().getGrille()[c.posX()][c.posY() - taillepoussee-1].getPersonnage() == null &&
                        c.getMap().getGrille()[c.posX()][c.posY() - taillepoussee-1].getBombe() == null &&
                        c.getMap().getGrille()[c.posX()][c.posY() - taillepoussee-1].getMur() == null);
                Bombe b = c.getMap().getGrille()[c.posX()][c.posY() - 1].getBombe();
                MoveToAction action1=new MoveToAction();
                action1.setDuration(0.2f*taillepoussee);
                action1.setPosition(Bomberball.taillecase/4,-(taillepoussee-1.25f)*Bomberball.taillecase);
                MoveByAction attente=new MoveByAction();
                attente.setAmount(0,0);
                attente.setDuration(0.2f*taillepoussee);
                this.addAction(attente);
                b.addAction(action1);
                b.addAction(new Action() {
                    float time = 0;

                    @Override
                    public boolean act(float delta) {
                        time += delta;
                        if (time > taillepoussee * 0.2f) {
                            c.getMap().getGrille()[c.posX()][c.posY() - taillepoussee+1].setBombe(new Bombe(taille, c.getMap().getGrille()[c.posX()][c.posY() - taillepoussee+1]));
                            c.getMap().getGrille()[c.posX()][c.posY() ].setBombe(null);
                            return true;
                        }
                        return false;
                    }
                });
            }


            return true;
        }
        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }
    /**
     * Déplace le joueur d'une case vers la droite s'il n'y a pas d'obstacles, tout en récupérant les bonus
     * @return boolean
     */
    public boolean deplacerDroite(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr"+id+"0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideDroite()){
            Case tmp = (c.getMap().getGrille()[c.posX()+1][c.posY()]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()+1][c.posY()].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action();}
            c.addActor(this);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr"+id+""+(int)(time*8)%4))));

                    return time>0.35;
                }
            });
            this.setX(getX()-Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(Bomberball.taillecase,0);
            action.setDuration(0.35f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getBombe()!=null){
                c.setBombe(null);
                taillepoussee=0;
                do {
                    taillepoussee++;
                } while (c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getMur()==null);
                Bombe b= new Bombe(this.taille,c.getMap().getGrille()[c.posX()+taillepoussee-1][c.posY()]);
                b.setX(-(taillepoussee-1)*Bomberball.taillecase);
                MoveToAction move=new MoveToAction();
                move.setPosition(Bomberball.taillecase/4,Bomberball.taillecase/4);
                move.setDuration(taillepoussee*0.2f);
                MoveByAction attente=new MoveByAction();
                attente.setAmount(0,0);
                attente.setDuration(taillepoussee*0.2f);
                this.addAction(attente);
                c.getMap().getGrille()[c.posX()+taillepoussee-1][c.posY()].setBombe(b);
                b.addAction(move);
            }
            if(c.getBonus()!=null){c.getBonus().action();}
            return true;
        }
        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }
    /**
     * Déplace le joueur d'une case vers la gauche s'il n'y a pas d'obstacles, tout en récupérant les bonus
     * @return boolean
     */
    public boolean deplacerGauche(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pl"+id+"0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideGauche()){

            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pl"+id+""+(int)(time*8)%4))));
                    if(time>0.35) {
                        Case tmp = (c.getMap().getGrille()[c.posX()-1][c.posY()]);
                        c.setPersonnage(null);
                        c.getMap().getGrille()[c.posX() - 1][c.posY()].setPersonnage((Personnage) target);
                        c.removeActor(target);
                        c=tmp;
                        if(c.getBonus()!=null){c.getBonus().action();}
                        c.addActor(target);
                        setX(0);
                    }
                    return time>0.35;
                }
            });
            MoveByAction action=new MoveByAction();
            action.setAmount(-Bomberball.taillecase,0);
            action.setDuration(0.35f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()!=null){

                taillepoussee = 0;
                do {
                    taillepoussee++;
                } while (c.getMap().getGrille()[c.posX()-taillepoussee-1][c.posY()].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()-taillepoussee-1][c.posY()].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()-taillepoussee-1][c.posY()].getMur()==null);

                MoveByAction action1=new MoveByAction();
                action1.setDuration(0.2f*taillepoussee);
                action1.setAmountX(-Bomberball.taillecase*(taillepoussee-1));
                MoveByAction attente=new MoveByAction();
                attente.setAmount(0,0);
                attente.setDuration(taillepoussee*0.2f);
                this.addAction(attente);
                c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe().addAction(action1);
                c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe().addAction(new Action() {
                    float time=0;
                    @Override
                    public boolean act(float delta) {
                        time+=delta;
                        if(time>taillepoussee*0.2f){
                            c.getMap().getGrille()[c.posX()-taillepoussee+1][c.posY()].setBombe(new Bombe(taille,c.getMap().getGrille()[c.posX()-taillepoussee+1][c.posY()]));
                            c.getMap().getGrille()[c.posX()][c.posY()].setBombe(null);
                            return true;
                        }
                        return false;
                    }
                });


            }

            return true;
        }
        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }
    /**
     * Attribue le bonus de la case au joueur
     */
    public void ramasserBonus(){
        if (c.getBonus()!=null){
            c.getBonus().action();
        }
    }
    /**
     * Vérifie que la case au-dessus du joueur est libre
     * @return true si la case est libre
     */
    public boolean caseVideHaut(){
        if (c.getMap().getGrille()[c.posX()][c.posY()+1].getMur()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()+1].getPersonnage()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()+1].getEnnemi()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()][c.posY()+1].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()][c.posY()+2].getBombe()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+2].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+2].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+2].getEnnemi()==null){
            return true;
        }
        return false;
    }
    /**
     * Vérifie que la case en-dessous du joueur est libre
     * @return true si la case est libre
     */
    public boolean caseVideBas(){
        if (c.getMap().getGrille()[c.posX()][c.posY()-1].getMur()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()-1].getPersonnage()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()-1].getEnnemi()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()][c.posY()-2].getBombe()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-2].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-2].getMur()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-2].getEnnemi()==null){
            return true;
        }
        return false;
    }
    /**
     * Vérifie que la case à droite du joueur est libre
     * @return true si la case est libre
     */
    public boolean caseVideDroite(){
        if (c.getMap().getGrille()[c.posX()+1][c.posY()].getMur()!=null ||
                c.getMap().getGrille()[c.posX()+1][c.posY()].getPersonnage()!=null ||
                c.getMap().getGrille()[c.posX()+1][c.posY()].getEnnemi()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()+1][c.posY()].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()+2][c.posY()].getBombe()==null &&
                c.getMap().getGrille()[c.posX()+2][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()+2][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()+2][c.posY()].getEnnemi()==null){
            return true;
        }
        return false;
    }
    /**
     * Vérifie que la case à gauche du joueur est libre
     * @return true si la case est libre
     */
    public boolean caseVideGauche(){
        if (c.getMap().getGrille()[c.posX()-1][c.posY()].getMur()!=null ||
                c.getMap().getGrille()[c.posX()-1][c.posY()].getPersonnage()!=null ||
                c.getMap().getGrille()[c.posX()-1][c.posY()].getEnnemi()!=null) { //Y a t-il un mur ou un personnage ?) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()-2][c.posY()].getBombe()==null &&
                c.getMap().getGrille()[c.posX()-2][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()-2][c.posY()].getMur()==null &&
                c.getMap().getGrille()[c.posX()-2][c.posY()].getEnnemi()==null){
            return true;
        }
        return false;
    }


   /* public static void main(String[] args){
        Personnage P=new Personnage(true,new Case(),1,1,1,1);
        Map M=new Map();
        P.getC().setMap(M);

// Test de poserBombe
        try {
            P.poserBombe();
        }catch (Exception e){
            System.out.println("fail poserBombe");
        }
        if (P.getC().getBombe()==null){
            System.out.println("fail poserBombe");
        }

// Test de deplacerHaut
        try {
            P.deplacerHaut();
        }catch (Exception e){
            System.out.println("fail deplacerHaut");
        }
    }*/
}