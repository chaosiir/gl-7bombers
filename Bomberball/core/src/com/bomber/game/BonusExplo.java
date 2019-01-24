package com.bomber.game;

import java.io.Serializable;

public class BonusExplo extends Bonus {
    /**
     * Constructeur de la classe BonusExplo
     * @param C
     * @return un bonus de portée sur la case C
     */
    public BonusExplo(Case C) {
        super(C,Bomberball.multiTexture[8]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de portée
     */
    public void action(){
        c.getPersonnage().setTaille(c.getPersonnage().getTaille()+1);
        c.suppBonus();
    }

    public static void main(String[] args){
        Bonus B=new BonusExplo(new Case());
        B.getC().setPersonnage(new Personnage(true,B.getC(),1,1,1,1));
        int Nb=B.getC().getPersonnage().getPortee();
        try{
            B.action();
        }catch (Exception e){
            System.out.println("fail action");
        }
        if (B.getC().getPersonnage().getPortee()==Nb){
            System.out.println("fail action");
        }
    }
}
