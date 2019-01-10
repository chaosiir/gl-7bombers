package com.bomber.game;

public class MurI extends Mur{

    public MurI(){
        super(Bomberball.multiTexture[2]);
        setName("MurI");
    }

    /**
     * Indique que le mur est indestructible
     * @return boolean
     */
    public boolean destructible() {
        return false;
    }
}
