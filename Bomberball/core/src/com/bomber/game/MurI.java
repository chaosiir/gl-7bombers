package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

public class MurI extends Mur{

    public MurI(){
        super(Bomberball.multiTexture[2]);
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(Bomberball.multiTexture[2],getParent().getX(),getParent().getY(),50,50);
    }

    public boolean destructible() {
        return false;
    }
}
