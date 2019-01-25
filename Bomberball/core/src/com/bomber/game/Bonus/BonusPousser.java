package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/**
 *  Classe du bonus pour activer la poussée d'une bombe
 */
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
