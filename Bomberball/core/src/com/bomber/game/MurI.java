package com.bomber.game;

import java.io.Serializable;

public class MurI extends Mur {

    public MurI(){
        super(Bomberball.multiTexture[2]);
        setName("MurI");
    }

    public boolean destructible() {
        return false;
    }
}
