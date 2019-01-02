package com.bomber.game;

public class BonusBombe extends Bonus {
    public BonusBombe(Case C) {
        super(C);
    }


    @Override
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
        System.out.println("BONUS DE NOMBRE DE BOMBE RAMASSE");
    }
}
