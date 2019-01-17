package com.bomber.game;

import java.io.Serializable;

public class BonusPousser extends Bonus {

    public BonusPousser(Case C) {
        super(C,Bomberball.multiTexture[19]);
    }
    @Override
    public void action(){
        c.getPersonnage().setPoussee(true);
        c.suppBonus();
    }
}
