package com.bomber.game;

import java.io.Serializable;

public class BonusBombe extends Bonus {
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }
    @Override
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }
}
