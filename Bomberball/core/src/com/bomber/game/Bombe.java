package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

public class Bombe extends Image  {
    int taille; //taille de l'explosion
    Personnage personnage;
    Case c;

<<<<<<< Updated upstream
    public Bombe(int taille, Personnage personnage, Case c) {
        super(Bomberball.multiTexture[5]);
=======
    public Bombe(int taille, Personnage personnage, game.Case c) {
>>>>>>> Stashed changes
        this.taille = taille;
        this.personnage = personnage;
        this.setName("bombe");
        this.c = c;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Case getC() {
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }


    public void explosion(){
        this.c.explosionHaute(taille);
        this.c.explosionBasse(taille);
        this.c.explosionDroite(taille);
        this.c.explosionGauche(taille);
        Image explo=new Image(Bomberball.multiTexture[9]);
        explo.setName("explo");
        explo.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        c.addActor(explo);
        c.addAction(new Action() {
            float time=0;
            @Override
            public boolean act(float delta) {
                time+=delta;
                if(time>1){
                    c.removeActor(c.findActor("explo"));
                    return true;
                }
                return false;
            }
        });
        this.c.suppBombe();
    }
}
