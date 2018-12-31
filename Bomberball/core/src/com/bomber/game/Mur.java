package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mur extends Image {// un mur est une image = un acteur avec un sprite => voir tuto Acteur

    public Mur(Texture t){
        super(t);
    }



    public boolean destructible(){
        return true;
    }
}
