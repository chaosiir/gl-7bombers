
package com.bomber.game;
public class BonusMove extends Bonus {

    /**
     * Constructeur de la classe BonusMove
     * @param C
     * @return un bonus de mouvement sur la case C
     */
    public BonusMove(Case C) {
        super(C,Bomberball.multiTexture[7]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de mouvement
     */
    public void action(){
        c.getPersonnage().setPm(c.getPersonnage().getPm()+1);
        System.out.println("Mise à jour "+c.getPersonnage().getPm());
        c.suppBonus();
    }
}
