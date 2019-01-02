package com.bomber.game;

public class BonusMove extends Bonus {
    public BonusMove(Case C) {
        super(C);
    }

    @Override
    public void action(){
        c.getPersonnage().setPm(c.getPersonnage().getPm()+1);
        c.suppBonus();
        System.out.println("BONUS DE DEPLACEMENT RAMASSE");
    }
}
