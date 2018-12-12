package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MurI extends Mur{

    public MurI(){
        super(Bomberball.multiTexture[2]);
    }

    public boolean destructible() {
        return false;
    }
}
