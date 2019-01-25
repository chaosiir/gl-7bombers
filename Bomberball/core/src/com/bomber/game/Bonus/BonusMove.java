
package com.bomber.game.Bonus;

import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;
/** classe BonusMove
 * augmente le nombre de mouvement du personnage qui l'utilise de 1
 *
 */
public class BonusMove extends Bonus {
    /**
     * Constructeur de la classe BonusMove
     * @param Case c
     * @return un bonus de mouvement sur la case C
     */
    public BonusMove(Case C) {
        super(C,Bomberball.multiTexture[7]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de mouvement d'augmenter son nombre de mouvement de 1.
     * le bonus est ensuite supprimé
     */
    public void action(){
        c.getPersonnage().setPm(c.getPersonnage().getPm()+1);
        c.suppBonus();
    }
}
