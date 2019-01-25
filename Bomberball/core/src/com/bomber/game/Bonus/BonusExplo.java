package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/** classe BonusExplo
 * augmente la portée des bombes du personnage qui l'utilise de 1
 *
 */
public class BonusExplo extends Bonus {
    /**
     * Constructeur de la classe BonusExplo
     * @param Case c
     * @return un bonus de portée sur la case C
     */
    public BonusExplo(Case c) {
        super(c,Bomberball.multiTexture[8]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de portée augmente son attribut taille de 1.
     * le bonus est ensuite supprimé
     */
    public void action(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
    }
}
