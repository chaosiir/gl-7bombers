package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

public class BonusExplo extends Bonus {
    /**
     * Constructeur de la classe BonusExplo
     * @param C
     * @return un bonus de portée sur la case C
     */
    public BonusExplo(Case C) {
        super(C,Bomberball.multiTexture[8]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de portée
     */
    public void action(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
    }
}
