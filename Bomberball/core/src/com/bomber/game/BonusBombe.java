package com.bomber.game;

import java.io.Serializable;

public class BonusBombe extends Bonus {
    /**
     * Constructeur de la classe BonusBombe
     * @param C
     * @return un bonus de bombe ur la case C
     */
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de bombe
     */
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }
}
