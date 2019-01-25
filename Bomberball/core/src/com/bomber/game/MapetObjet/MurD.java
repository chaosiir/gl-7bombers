package com.bomber.game.MapetObjet;

import com.bomber.game.Bomberball;

/**
 * Classe MurD
 * mur destructible peut être detruit et être dans une case avec un bonus
 */
public class MurD extends Mur {
    /**
     * constructeur de MurD
     */
    public MurD(){
       super(Bomberball.multiTexture[1]);
       setName("MurD");
    }


}
