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

import java.io.Serializable;

public class Personnage extends Image  {

    private boolean vivant;//statut du personnage,
    private Case c;//case ou se trouve le personnage
    private int taille;//taille de l'explosion de la bombe, 3 par defaut
    private int nbBombe;//bombe posable par tour, 1 par défaut
    private int pm;//points de mouvement, 5 par defaut
    private boolean poussee;


    public Personnage(boolean vivant, Case c, int taille, int nbBombe, int pm) {
        super(Bomberball.perso.findRegion("pdown2"));
        this.setName("Personnage");
        this.setSize(Bomberball.taillecase,Bomberball.taillecase);
        this.vivant = vivant;
        this.c = c;
        this.taille = taille;
        this.nbBombe = nbBombe;
        this.pm = pm;
        this.poussee=true;
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

    /**
     * Associe une bombe à la case associée au personnage
     */
    public boolean poserBombe() {
        // Si le personnage a encore des bombes a poser, une bombe est cree avec la portée du personnage, et deposée sur la case ou il se trouve
        if (c.getBombe() == null && this.nbBombe>0) {
            Bombe b = new Bombe(taille, c);
            c.setBombe(b);
            return true;
        }
        return false;
    }

    public void afficher(Batch b, int x, int y, Texture[] multt){
        Sprite s;
        s=new Sprite(multt[4]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }

    /**
     * Deplace le personnage d'une case vers le haut si celle-ci n'a pas de mur.
     * Tue le personnage s'il y a un ennemi, et s'il y a un bonus, lui attribue.
     * Si le personnage a le bonus appoprie,lui permet de pousser une bombe.
     */
    public boolean deplacerHaut(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pup0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        // Si la case ciblee est libre, le personnage s'y deplace
        if (caseVideHaut()){
            // On commence par changer la case a laquelle le joueur est associe
            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()+1]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()][c.posY()+1].setPersonnage((Personnage) this);
            c=tmp;
            // S'il y a un bonus sur la case ou le joueur arrive, celui-ci est consomme et les caracteristiques du joueur sont modifies en consequence
            if(c.getBonus()!=null){c.getBonus().action();}
            c.addActor(this);
            setY(-Bomberball.taillecase);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;

                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pup%d",(int)(time*8)%4)))));

                    return time>0.5;
                }
            });
            // On effectue ensuite l'animation du deplacement
            MoveByAction action=new MoveByAction();
            action.setAmount(0,Bomberball.taillecase);
            action.setDuration(0.5f);
            this.addAction(action);
            // Si le joueur dispose du bonus de pousse et qu'il y a une bombe sur la case, il peut la pousser
            if (c.getBombe()!=null){
                c.setBombe(null);
                int i = 0;
                // Dans ce cas, la bombe se deplace jusqu'a ce qu'elle rencontre le moindre obstacle
                do {
                    i++;
                } while (c.getMap().getGrille()[c.posX()][c.posY()+i].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()+i].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()+i].getMur()==null);
                c.getMap().getGrille()[c.posX()][c.posY()+i-1].setBombe(new Bombe(this.taille,c.getMap().getGrille()[c.posX()][c.posY()+i-1]));
            }


            return true;
        }
        // S'il y a un ennemi sur la case ou le joueur arrive, le joueur est tue
        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }

    /**
     * Deplace le personnage d'une case vers le bas si celle-ci n'a pas de mur.
     * Tue le personnage s'il y a un ennemi, et s'il y a un bonus, lui attribue.
     * Si le personnage a le bonus appoprie,lui permet de pousser une bombe.
     */
    public boolean deplacerBas(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pdown0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (this.caseVideBas()){


            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pdown%d",(int)(time*8)%4)))));
                    if(time>0.5) {
                        Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()-1]);
                        c.setPersonnage(null);
                        c.removeActor(target);
                        c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
                        c.getMap().getGrille()[c.posX()][c.posY()-1].setPersonnage((Personnage) target);
                        c=tmp;
                        c.addActor(target);
                        setY(0);
                    }
                    return time>0.5;
                }
            });

            MoveByAction action=new MoveByAction();
            action.setAmount(0,-Bomberball.taillecase);
            action.setDuration(0.5f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()!=null){
                c.getMap().getGrille()[c.posX()][c.posY()-1].setBombe(null);
                int i = 0;
                do {
                    i++;
                } while (c.getMap().getGrille()[c.posX()][c.posY()-i].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()-i].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()][c.posY()-i].getMur()==null);
                c.getMap().getGrille()[c.posX()][c.posY()-i+1].setBombe(new Bombe(this.taille,c.getMap().getGrille()[c.posX()][c.posY()-i+1]));
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
     * Deplace le personnage d'une case vers la droite si celle-ci n'a pas de mur.
     * Tue le personnage s'il y a un ennemi, et s'il y a un bonus, lui attribue.
     * Si le personnage a le bonus appoprie,lui permet de pousser une bombe.
     */
    public boolean deplacerDroite(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideDroite()){
            Case tmp = (c.getMap().getGrille()[c.posX()+1][c.posY()]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
            c.getMap().getGrille()[c.posX()+1][c.posY()].setPersonnage(this);
            c=tmp;
            if(c.getBonus()!=null){c.getBonus().action();}
            c.addActor(this);
            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pr%d",(int)(time*8)%4)))));

                    return time>0.5;
                }
            });
            this.setX(getX()-Bomberball.taillecase);
            MoveByAction action=new MoveByAction();
            action.setAmount(Bomberball.taillecase,0);
            action.setDuration(0.5f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getBombe()!=null){
                c.setBombe(null);
                int i = 0;
                do {
                    i++;
                } while (c.getMap().getGrille()[c.posX()+i][c.posY()].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()+i][c.posY()].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()+i][c.posY()].getMur()==null);
                c.getMap().getGrille()[c.posX()+i-1][c.posY()].setBombe(new Bombe(this.taille,c.getMap().getGrille()[c.posX()+i-1][c.posY()]));
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
     * Deplace le personnage d'une case vers la gauche si celle-ci n'a pas de mur.
     * Tue le personnage s'il y a un ennemi, et s'il y a un bonus, lui attribue.
     * Si le personnage a le bonus appoprie,lui permet de pousser une bombe.
     */
    public boolean deplacerGauche(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pl0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideGauche()){

            this.addAction(new Action() {
                float time=0;
                @Override
                public boolean act(float delta) {
                    time+=delta;
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion(String.format("pl%d",(int)(time*8)%4)))));
                    if(time>0.5) {
                        Case tmp = (c.getMap().getGrille()[c.posX()-1][c.posY()]);
                        c.setPersonnage(null);
                        c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
                        c.getMap().getGrille()[c.posX() - 1][c.posY()].setPersonnage((Personnage) target);
                        c.removeActor(target);
                        c=tmp;
                        if(c.getBonus()!=null){c.getBonus().action();}
                        c.addActor(target);
                        setX(0);
                    }
                    return time>0.5;
                }
            });
            MoveByAction action=new MoveByAction();
            action.setAmount(-Bomberball.taillecase,0);
            action.setDuration(0.5f);
            this.addAction(action);
            /*Fin de l'animation*/
            /*Poussage de la bombe*/
            if (c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()!=null){
                c.getMap().getGrille()[c.posX()-1][c.posY()].setBombe(null);
                int i = 0;
                do {
                    i++;
                } while (c.getMap().getGrille()[c.posX()-i][c.posY()].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()-i][c.posY()].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()-i][c.posY()].getMur()==null);

                c.getMap().getGrille()[c.posX()-i+1][c.posY()].setBombe(new Bombe(this.taille,c.getMap().getGrille()[c.posX()-i+1][c.posY()]));
            }

            return true;
        }
        if (c.getEnnemi()!=null){
            this.setVivant(false);
        }
        return false;
    }

    /**
     * Attribue au persoonage le bonus de la case ou il se trouve
     */
    public void ramasserBonus(){
        if (c.getBonus()!=null){
            c.getBonus().action();
        }
    }

    /**
     * Verifie que la case au-dessus du personnage est vide
     */
    public boolean caseVideHaut(){
        if (c.getMap().getGrille()[c.posX()][c.posY()+1].getMur()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()+1].getPersonnage()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()][c.posY()+1].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()][c.posY()+2].getBombe()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+2].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()+2].getMur()==null){
            return true;
        }
        return false;
    }

    /**
     * Verifie que la case au-dessous du personnage est vide
     */
    public boolean caseVideBas(){
        if (c.getMap().getGrille()[c.posX()][c.posY()-1].getMur()!=null ||
                c.getMap().getGrille()[c.posX()][c.posY()-1].getPersonnage()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()][c.posY()-1].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()][c.posY()-2].getBombe()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-2].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()][c.posY()-2].getMur()==null){
            return true;
        }
        return false;
    }

    /**
     * Verifie que la case à droite du personnage est vide
     */
    public boolean caseVideDroite(){
        if (c.getMap().getGrille()[c.posX()+1][c.posY()].getMur()!=null ||
                c.getMap().getGrille()[c.posX()+1][c.posY()].getPersonnage()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()+1][c.posY()].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()+2][c.posY()].getBombe()==null &&
                c.getMap().getGrille()[c.posX()+2][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()+2][c.posY()].getMur()==null){
            return true;
        }
        return false;
    }

    /**
     * Verifie que la case à gauche du personnage est vide
     */
    public boolean caseVideGauche(){
        if (c.getMap().getGrille()[c.posX()-1][c.posY()].getMur()!=null ||
                c.getMap().getGrille()[c.posX()-1][c.posY()].getPersonnage()!=null) { //Y a t-il un mur ou un personnage ?
            return false; //si oui on ne peut pas passer
        }else if (c.getMap().getGrille()[c.posX()-1][c.posY()].getBombe()==null){//N'y a t-il pas de bombe ?
            return true; //Si non on peut passer, car il n'y a ni mur ni personnage
        } else if (!this.isPoussee()){ //A-t-on  le bonus poussée
            return false; //Si non on ne passe pas
        } else if (c.getMap().getGrille()[c.posX()-2][c.posY()].getBombe()==null &&
                c.getMap().getGrille()[c.posX()-2][c.posY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.posX()-2][c.posY()].getMur()==null){
            return true;
        }
        return false;
    }
}