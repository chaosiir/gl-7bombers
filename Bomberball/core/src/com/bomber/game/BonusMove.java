
package com.bomber.game;
public class BonusMove extends Bonus {

    public BonusMove(Case C) {
        super(C);
    }

    public void actionM(){
        c.getPersonnage().setPm(c.getPersonnage().getPm()+1);
        c.suppBonus();
    }
}
