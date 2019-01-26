package com.bomber.game.MapetObjet;

import com.bomber.game.Bomberball;

/**
 * Class MurI
 * les murs indestructibles ne peuvent pas être detruits par des explosions
 */
public class MurI extends Mur {

    /**
     * constructeur de MurI
     */
    public MurI(){
        super(Bomberball.multiTexture[2]);
        setName("MurI");
    }

}
