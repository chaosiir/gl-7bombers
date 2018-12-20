package com.bomber.game;

public class MurI extends Mur{

    public MurI(){
        super(Bomberball.multiTexture[2]);
    }

    public boolean destructible() {
        return false;
    }
}
