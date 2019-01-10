package com.bomber.game;

public class BonusPousser extends Bonus {
    public BonusPousser(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }
    @Override
    /**
     * Autorise le joueur a pousser les bombes
     */
    public void action(){
        c.getPersonnage().setPoussee(true);
        c.suppBonus();
    }
}
