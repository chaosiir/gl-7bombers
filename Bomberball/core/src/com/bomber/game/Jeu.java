package com.bomber.game;
import java.util.*;

public class Jeu {
    Map map;


    public static void main(String [] args) {

        /*System.out.println("Sélectionnez votre mode de jeu, 1 pour multi, 2 pour solo");
        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();
        if (choix == 1 || true){
            System.out.println("Debut de partie multi, sélectionnez le nombre de joueurs 2,3 ou 4");
            choix=sc.nextInt();
            int nbJoueur=choix;
            System.out.println("Nombre de cases destructibles, max 93");
            choix=sc.nextInt();
            Map m= new Map();
            Case[][] g = new Case [16][14];
            m.setGrille(g);
            m.setGrille(m.generatePvp(choix));
        }*/

        int i;
        int j;
        System.out.println("debut du test");
        Map m= new Map();
        Case[][] g = new Case [16][14];
        m.setGrille(g);
        m.setGrille(m.generatePvp(60));
        m.genBonus(30);
        //m.setGrille(g);
        Personnage p= new Personnage(true,m.getGrille()[2][2],3,2,5);
        //Bombe b = new Bombe(3,p,m.getGrille()[2][3]);
        //(m.getGrille())[2][3].setBombe(b);
        (m.getGrille())[2][2].setPersonnage(p);
        System.out.println("a");
        int [][] traduit = new int[16][14];
        traduit = m.traducteur();
        for (i=1;i<=15;i++){
            System.out.println(""+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                    traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]);
        }
        int pm=p.getPm();
        int choix=0;
        Scanner sc = new Scanner(System.in);
        while(true){
            pm=p.getPm();
            System.out.println(pm);
            while (pm>0){
                System.out.println("personnage à la postion "+p.getC().getX()+" "+p.getC().getY());
                System.out.println("c");
                System.out.println("il vous reste "+pm+" deplacements");
                System.out.println("1 pour déplacerHaut, 2 pour Bas, 3 pour Gauche, 4 pour Droite, 5 pour poser une bombe, 6 pour fin de tour");
                choix=sc.nextInt();
                switch (choix){
                    case 1 : p.deplacerHaut(); pm--; break;
                    case 2 : p.deplacerBas(); pm--; break;
                    case 3 : p.deplacerGauche(); pm--; break;
                    case 4 : p.deplacerDroite(); pm--; break;
                    case 5 : p.poserBombe(); break;
                    case 6 : pm=0; break;
                }
                traduit = m.traducteur();
                for (i=1;i<=15;i++){
                    System.out.println(""+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                            traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]);
                }
            }
            m.explosion();
        }
    }
}
