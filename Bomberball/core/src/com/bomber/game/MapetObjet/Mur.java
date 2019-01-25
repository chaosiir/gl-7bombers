package com.bomber.game.MapetObjet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Classe Mur
 * sert pour accerder à tout les murs
 */
public class Mur extends Image  {// un mur est une image

    /**
     *renvoi un mur avec une certaine texture
     */
    public Mur(Texture t){
        super( t);
    }

}
