package com.bomber.game.MapetObjet;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;

public class Bombe extends Image {
    int taille; //taille de l'explosion
    Case c;
    /**
     * Constructeur de la classe Bombe
     * @return une bombe de portée taille sur la case c
     */
    public Bombe(int taille, Case c) {
        super(Bomberball.multiTexture[5]);
        this.taille = taille;
        this.setPosition(Bomberball.taillecase/2-this.getWidth()/2,Bomberball.taillecase/2 -this.getHeight()/2);

        this.setName("bombe");
        this.c = c;
    }


    /**
     * Accesseur de la taille de la bombe
     * @return la taille
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Modificateureur de la taille de la bombe
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }


    /**
     * Accesseur de la case de la bombe
     * @return la case
     */
    public Case getC() {
        return c;
    }

    /**
     * Modificateur de la case de la bombe
     */
    public void setC(Case c) {
        this.c = c;
    }

    /**
     * Informe la case de la bombe qu'une explosion y prend sa source
     * Supprime la bombe une fois que l'explosion a eu lieu
     */


    public void explosion(){
        this.c.explosionHaute(taille);                                          //on fait une explosion de chaque côté
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
            public boolean act(float delta) {                                   //au bout d'une seconde on enlève l'affichage de l'explosion
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