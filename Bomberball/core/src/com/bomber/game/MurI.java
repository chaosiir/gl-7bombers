package com.bomber.game;

public class MurI extends Mur{

    public MurI(){
        super(Bomberball.multiTexture[2]);
        setName("MurI");
    }

    public boolean destructible() {
        return false;
    }
}
