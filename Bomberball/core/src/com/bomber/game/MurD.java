package com.bomber.game;

import com.badlogic.gdx.graphics.g2d.Batch;

public class MurD extends Mur {

    public MurD(){
       super(Bomberball.multiTexture[1]);
       setName("MurD");
    }

    @Override
    public boolean destructible() {
        return true;
    }

}
