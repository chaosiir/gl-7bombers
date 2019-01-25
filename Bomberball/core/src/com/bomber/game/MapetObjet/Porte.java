package com.bomber.game.MapetObjet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;

/**
 * Classe Porte
 * c'est la case sur laquelle le joueur doit arrivé pour gagner
 */
public class Porte extends Image {

    /**
     * Constructeur de la classe Porte
     */
    public Porte(){
        super(Bomberball.multiTexture[3]);
        setName("Porte");
    }


}
