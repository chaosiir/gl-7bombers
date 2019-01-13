package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.Serializable;

public class Personnage extends Image  {

    private boolean vivant;
    private Case c;
    private int taille;//taille de l'explosion de la bombe, 3 par defaut
    private int nbBombe;//bombe posable par tour, 1 par défaut
    private int pm;//points de mouvement, 5 par defaut
    private boolean poussee;
    private int id;
    private int taillepoussee;


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
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getTaille() {
        return taille;
    } //Taille d'une explosion

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

    public boolean poserBombe() {
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

    public boolean deplacerHaut(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pup"+id+"0"))));
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        if (caseVideHaut()){

            Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()+1]);
            c.setPersonnage(null);
            c.removeActor(this);
            c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
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

                    return time>0.5;
                }
            });
            MoveByAction action=new MoveByAction();
            action.setAmount(0,Bomberball.taillecase);
            action.setDuration(0.5f);
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
                move.setPosition(0,0);
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
                    if(time>0.5) {
                        Case tmp = (c.getMap().getGrille()[c.posX()][c.posY()-1]);
                        c.setPersonnage(null);
                        c.removeActor(target);
                        c.getMap().getGrille()[c.posX()][c.posY()].setPersonnage(null);
                        c.getMap().getGrille()[c.posX()][c.posY()-1].setPersonnage((Personnage) target);
                        c=tmp;
                        if(c.getBonus()!=null){c.getBonus().action();}
                        c.addActor(target);
                        setY(0);
                    }
                    return time>0.5;
                }
            });

            MoveByAction action=new MoveByAction();
            action.setAmount(0,-Bomberball.taillecase);
            action.setDuration(0.45f);
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
                action1.setPosition(0,-(taillepoussee-1)*Bomberball.taillecase);
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

    public boolean deplacerDroite(){
        this.setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr"+id+"0"))));
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
                    setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.perso.findRegion("pr"+id+""+(int)(time*8)%4))));

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
                taillepoussee=0;
                do {
                    taillepoussee++;
                } while (c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getPersonnage()==null &&
                        c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getBombe()==null &&
                        c.getMap().getGrille()[c.posX()+taillepoussee][c.posY()].getMur()==null);
                Bombe b= new Bombe(this.taille,c.getMap().getGrille()[c.posX()+taillepoussee-1][c.posY()]);
                b.setX(-(taillepoussee-1)*Bomberball.taillecase);
                MoveToAction move=new MoveToAction();
                move.setPosition(0,0);
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
            action.setDuration(0.45f);
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

    public void ramasserBonus(){
        if (c.getBonus()!=null){
            c.getBonus().action();
        }
    }

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