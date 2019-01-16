package com.bomber.game;

public class BonusPousser extends Bonus {

    /**
     * Constructeur de la classe BonusPousser
     * @param C
     * @return un bonus de poussée sur la case C
     */
    public BonusPousser(Case C) {
        super(C,Bomberball.multiTexture[19]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de poussée
     */
    public void action(){
        c.getPersonnage().setPoussee(true);
        c.suppBonus();
    }
}
