package com.bomber.game;

public class BonusExplo extends Bonus {
    public BonusExplo(Case C) {
        super(C);
    }

    public void actionE(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
    }
}
