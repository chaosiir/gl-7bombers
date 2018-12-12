package com.bomber.game;

import com.badlogic.gdx.graphics.g2d.Batch;

public class MurD extends Mur {

    public MurD(){
       super(Bomberball.multiTexture[1]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(Bomberball.multiTexture[1],getParent().getX(),getParent().getY(),50,50);
    }
    public boolean destructible() {
        return true;
    }

}
