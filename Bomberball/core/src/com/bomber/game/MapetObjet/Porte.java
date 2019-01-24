package com.bomber.game.MapetObjet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;


public class Porte extends Image {

    /**
     * Constructeur de la classe Porte
     */
    public Porte(){
        super(Bomberball.multiTexture[3]);
        setName("Porte");
    }

    /**
     * Affiche la porte
     * @param b
     * @param x abscisse
     * @param y ordonnée
     * @param multt
     */
    public void afficher(Batch b,int x,int y,Texture [] multt){
        Sprite s;
        s=new Sprite(multt[3]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }
}
