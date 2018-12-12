package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Mur extends Image {

    public Mur(Texture t){
        super( t);
    }



    public boolean destructible(){
        return true;
    }
}
