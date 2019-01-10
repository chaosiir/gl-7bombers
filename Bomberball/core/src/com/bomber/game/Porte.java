package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

public class Porte extends Image  {

    public Porte(){
        super(Bomberball.multiTexture[3]);
        setName("Porte");
    }

}
