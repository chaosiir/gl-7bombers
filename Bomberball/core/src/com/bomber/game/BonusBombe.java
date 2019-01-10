package com.bomber.game;

public class BonusBombe extends Bonus {
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }
    @Override
    /**
     * Incremente le nombre de bombe du joueur sur la case associee au bonus
     */
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }
}
