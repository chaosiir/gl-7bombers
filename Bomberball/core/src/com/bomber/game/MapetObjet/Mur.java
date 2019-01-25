package com.bomber.game.MapetObjet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Classe Mur
 * sert pour accerder à tout les murs
 */
public abstract class Mur extends Image  {// un mur est une image = un acteur avec un sprite => voir tuto Acteur
    /**
    *renvoi un mur avec la texture donnée
     */
    public Mur(Texture t){
        super( t);
    }

}
