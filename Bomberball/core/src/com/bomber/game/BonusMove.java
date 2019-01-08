
package com.bomber.game;
public class BonusMove extends Bonus {

    public BonusMove(Case C) {
        super(C,Bomberball.multiTexture[7]);
    }
    @Override
    public void action(){
        c.getPersonnage().setPm(c.getPersonnage().getPm()+1);
        System.out.println("Mise à jour "+c.getPersonnage().getPm());
        c.suppBonus();
    }
}
