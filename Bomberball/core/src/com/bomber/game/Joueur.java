package com.bomber.game;

import java.util.Scanner;

//classe joueur pour le pvp
//associé à un personnage, identifié par un numéro
//servira à compter le nombre de victoire
public class Joueur {
    private int num;
    private int nbVictoire;
    private Personnage p;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNbVictoire() {
        return nbVictoire;
    }

    public void setNbVictoire(int nbVictoire) {
        this.nbVictoire = nbVictoire;
    }

    public Personnage getP() {
        return p;
    }

    public void setP(Personnage p) {
        this.p = p;
    }

    public void jouerTour(Map m){
        int [][] traduit = new int[13][15];
        int i;
        int choix;
        Scanner sc = new Scanner(System.in);
        int pm = p.getPm();
        int bombe = p.getNbBombe();
        while (pm>0 || bombe>0){
            traduit = m.traducteur2();
            for (i=0;i<13;i++){
                System.out.println(" "+traduit[i][0]+" "+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                        traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]+" "+traduit[i][14]);
            }
            System.out.println("personnage à la postion "+p.getC().getX()+" "+p.getC().getY());
            System.out.println("il vous reste "+pm+" deplacements");
            System.out.println("il vous reste "+bombe+" bombes");
            System.out.println("taille bombe "+p.getTaille());
            System.out.println("1 pour déplacerHaut, 2 pour Bas, 3 pour Gauche, 4 pour Droite, 5 pour poser une bombe, 6 pour fin de tour");
            choix=sc.nextInt();
            switch (choix){
                case 4 : if (pm>0){pm=(p.deplacerHaut())? pm-1:pm;} break;
                case 3 : if (pm>0){pm=(p.deplacerBas())? pm-1:pm;} break;
                case 1 : if (pm>0){pm=(p.deplacerGauche())? pm-1:pm;} break;
                case 2 : if (pm>0){pm=(p.deplacerDroite())? pm-1:pm;} break;
                case 5 : if (bombe>0){bombe=(p.poserBombe())? bombe-1:bombe;} break;
                case 6 : pm=0; bombe=0; break;
            }
        }
    }
}
