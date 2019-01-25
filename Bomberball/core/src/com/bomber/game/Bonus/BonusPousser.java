package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/** classe BonusPousser
 * permet au joueur qui l'utilise de pousser les bombes
 *
 */
public class BonusPousser extends Bonus {

    /**
     * Constructeur de la classe BonusPousser
     * @param Case c
     * @return un bonus de poussée sur la case C
     */
    public BonusPousser(Case C) {
        super(C,Bomberball.multiTexture[19]);
    }
    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de poussée donc de pousser les bombes
     */
    public void action(){
        c.getPersonnage().setPoussee(true);//cette capacité est représentée par un boolean
        c.suppBonus();
    }
}
