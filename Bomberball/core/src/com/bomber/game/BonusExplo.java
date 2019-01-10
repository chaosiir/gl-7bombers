package com.bomber.game;

public class BonusExplo extends Bonus {

    public BonusExplo(Case C) {
        super(C,Bomberball.multiTexture[8]);
    }
    @Override
    /**
     * Incremente la portee des bombes du joueur sur la case associee au bonus
     */
    public void action(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
    }
}
