package com.bomber.game;

public class BonusExplo extends Bonus {
    public BonusExplo(Case C) {
        super(C);
    }
    @Override
    public void action(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
        System.out.println("BONUS D'EXPLOSION RAMASSE");
    }
}
