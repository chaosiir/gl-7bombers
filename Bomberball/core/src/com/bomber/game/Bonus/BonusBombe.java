package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/** classe BonusBombe
 * augmente le nombre de bombe du personnage qui l'utilise de 1
 *
 */
public class BonusBombe extends Bonus {
    /**
     * Constructeur de la classe BonusBombe
     * @param Case
     * @return un bonus de bombe sur la case C
     */
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de bombe et d'augmenter son nombre de bombe de 1.
     * le bonus est ensuite supprimé
     */
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }
}
