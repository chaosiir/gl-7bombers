
package com.bomber.game;

import java.io.Serializable;

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
       // System.out.println("Mise à jour "+c.getPersonnage().getPm());
        c.suppBonus();
    }

    public static void main(String[] args){
        Bonus B=new BonusBombe(new Case());
        B.getC().setPersonnage(new Personnage(true,B.getC(),1,1,1,1));
        int Nb=B.getC().getPersonnage().getPm();
        try{
            B.action();
        }catch (Exception e){
            System.out.println("fail action");
        }
        if (B.getC().getPersonnage().getPm()==Nb) {
            System.out.println("fail action");
        }
    }
}
