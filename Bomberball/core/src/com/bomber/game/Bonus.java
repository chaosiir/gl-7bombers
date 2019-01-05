package com.bomber.game;

public class Bonus {
    Case c;

    public Bonus(Case C){
            c=C;
    }

    public void action(Personnage p){
        if (this instanceof BonusMove){
            ((BonusMove) this).actionM();
        } else if (this instanceof BonusExplo) {
            ((BonusExplo) this).actionE();
        }else if (this instanceof BonusBombe) {
            ((BonusBombe) this).actionB();
        }
    }

    public String identifier(){
        return null;
    }



}
