
package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
// !!! a faire très important lorqu'on enleve les mur / perso/ acteur  => enlever l'acteur
//ce serait mieux de supprimer les parametre et de prendre les acteurs par nom à chaque fois (à voir si plus pratique => on peut le recuperer
// en damandant à un groupe de nous donner un acteur  avec un nom via group.getActor(nom) => voir tuto Acteur
public class Case extends Group {// case est un group d'acteur  (bombe/mur /bonus /personnage)
    Map map;
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;
    private Personnage personnage;
    private boolean explo;
    private Porte porte;

    public Case() {
        this.setPosition((x)*Bomberball.taillecase,(y)*Bomberball.taillecase);//definition de la position  = coordonnées * taille d'une case
        Image background=new Image(Bomberball.multiTexture[0]);//de base une image s'affiche vide (sol) si il y a qqc il sera afficher au dessus
        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);//definition de la taille de l'image
        //et de la position (0,0) = sur la case car position relative
        this.addActor(background);// une image est un acteur => voir tuto acteur


    }


    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isExplo() {
        return explo;
    }

    public void setExplo(boolean explo) {
        this.explo = explo;
    }

    public Bombe getBombe() {
        return bombe;
    }

    public void setBombe(Bombe bombe) {
        this.bombe = bombe;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Mur getMur() {
        return mur;
    }


    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public void setMur(Mur mur) {
        this.mur = mur;
        mur.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.addActor(mur);// rajout d'un mur à la bonne taille est possition
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {// meme chose que pour mur
        this.personnage = personnage;
        this.addActor(personnage);
    }

    public int posY() {
        return y;
    }

    public void setposY(int y) { this.y = y;
        this.setY(y*Bomberball.taillecase);}//convertion du y de position dans la grille à la coordonnee de l'ecran

    public int posX() {
        return x;
    }

    public void setposX(int x) {
        this.x = x;
        this.setX(2*Bomberball.taillecase+x*Bomberball.taillecase);
    }



    public void explosionHaute(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
           this.setMur(null);
           this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x][y+1].explosionHaute(longueur-1);
            }
        }

    }

    public void explosionBasse(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x][y-1].explosionBasse(longueur-1);
            }
        }

    }

    public void explosionDroite(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x+1][y].explosionDroite(longueur-1);
            }
        }

    }

    public void explosionGauche(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x-1][y].explosionGauche(longueur-1);
            }
        }
    }



    public void suppBombe(){
        this.bombe=null;
    }
    public void suppBonus(){
        this.bonus=null;
    }


}