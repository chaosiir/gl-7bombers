package com.bomber.game;

public class BonusBombe extends Bonus {
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }

    public void actionB(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }
}
