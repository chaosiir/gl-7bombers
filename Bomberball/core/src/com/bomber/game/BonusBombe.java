package com.bomber.game;

import java.io.Serializable;

public class BonusBombe extends Bonus {
    /**
     * Constructeur de la classe BonusBombe
     * @param C
     * @return un bonus de bombe ur la case C
     */
    public BonusBombe(Case C) {
        super(C,Bomberball.multiTexture[6]);
    }

    @Override
    /**
     * Permet au personnage sur la case de récupérer le bonus de bombe
     */
    public void action(){
        c.getPersonnage().setNbBombe(c.getPersonnage().getNbBombe()+1);
        c.suppBonus();
    }

    public static void main(String[] args){
        Bonus B=new BonusBombe(new Case());
        B.getC().setPersonnage(new Personnage(true,B.getC(),1,1,1,1));
        int Nb=B.getC().getPersonnage().getNbBombe();
        try{
            B.action();
        }catch (Exception e){
            System.out.println("fail action");
        }
        if (B.getC().getPersonnage().getNbBombe()==Nb) {
            System.out.println("fail action");
        }
    }
}
