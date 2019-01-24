package com.bomber.game;

import java.io.Serializable;

public class BonusPousser extends Bonus {

    /**
     * Constructeur de la classe BonusPousser
     * @param C
     * @return un bonus de poussée sur la case C
     */
    public BonusPousser(Case C) {
        super(C,Bomberball.multiTexture[19]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de poussée
     */
    public void action(){
        c.getPersonnage().setPoussee(true);
        c.suppBonus();
    }

    public static void main(String[] args){
        Bonus B=new com.bomber.game.BonusBombe(new Case());
        B.getC().setPersonnage(new Personnage(true,B.getC(),1,1,1,1));
        try{
            B.action();
        }catch (Exception e){
            System.out.println("fail action");
        }
        if (!B.getC().getPersonnage().isPoussee()) {
            System.out.println("fail action");
        }
    }
}
