package com.bomber.game;
import org.lwjgl.Sys;

import java.util.Scanner;




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
        Case[][] g = new Case [13][15];
        m.setGrille(g);
        m.setGrille(m.generatePvp(60));
        m.genBonus(30);
        Scanner sc = new Scanner(System.in);
        System.out.println("Nombre de joueurs ?");
        int choix=0;
        choix=sc.nextInt();
        int nbJ = choix;
        Joueur[] listeJ = new Joueur[choix];
        /*Case[] caseJoueur = new Case[4];
        caseJoueur[0] = m.getGrille()[1][1];
        caseJoueur[1] = m.getGrille()[11][1];
        caseJoueur[2] = m.getGrille()[1][13];
        caseJoueur[3] = m.getGrille()[11][13];*/
        for (i=0;i<choix;i++){
            switch (i){
                case 0 : Joueur player1=new Joueur();
                    Personnage p1= new Personnage(true,m.getGrille()[1][1],3,1,5);
                    player1.setP(p1);
                    listeJ[0]= player1;
                    (m.getGrille())[1][1].setPersonnage(p1);
                    break;
                case 1 : Joueur player2=new Joueur();
                    Personnage p2= new Personnage(true,m.getGrille()[11][1],3,1,5);
                    player2.setP(p2);
                    listeJ[1]= player2;
                    (m.getGrille())[11][1].setPersonnage(p2);
                    break;
                case 2 : Joueur player3=new Joueur();
                    Personnage p3= new Personnage(true,m.getGrille()[1][13],3,1,5);
                    player3.setP(p3);
                    listeJ[2]= player3;
                    (m.getGrille())[1][13].setPersonnage(p3);
                    break;
                case 3 : Joueur player4=new Joueur();
                    Personnage p4= new Personnage(true,m.getGrille()[11][13],3,1,5);
                    player4.setP(p4);
                    listeJ[3]= player4;
                    (m.getGrille())[11][13].setPersonnage(p4);
                    break;
            }
        }
        //m.setGrille(g);
        //Personnage p= new Personnage(true,m.getGrille()[2][2],3,2,5);
        //Bombe b = new Bombe(3,p,m.getGrille()[2][3]);
        //(m.getGrille())[2][3].setBombe(b);
        //(m.getGrille())[2][2].setPersonnage(p);
        //System.out.println("a");
        int [][] traduit = new int[13][15];
        traduit = m.traducteur2();
        /*for (i=0;i<13;i++){
            System.out.println(" "+traduit[i][0]+" "+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                    traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]+" "+traduit[i][14]);
        }*/
        Personnage p=listeJ[0].getP();
        int pm;
        int tour = 0;
        int encours = 0;
        int compteurVivant=nbJ;
        while(compteurVivant>1){
            /*pm=p.getPm();
            while (pm>0){
                traduit = m.traducteur2();
                for (i=0;i<13;i++){
                    System.out.println(" "+traduit[i][0]+" "+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                            traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]+" "+traduit[i][14]);
                }
                System.out.println("personnage à la postion "+p.getC().getX()+" "+p.getC().getY());
                System.out.println("il vous reste "+pm+" deplacements");
                System.out.println("1 pour déplacerHaut, 2 pour Bas, 3 pour Gauche, 4 pour Droite, 5 pour poser une bombe, 6 pour fin de tour");
                choix=sc.nextInt();
                switch (choix){
                    case 4 : pm=(p.deplacerHaut())? pm-1:pm; break;
                    case 3 :pm=(p.deplacerBas())? pm-1:pm; break;
                    case 1 : pm=(p.deplacerGauche())? pm-1:pm; break;
                    case 2 : pm=(p.deplacerDroite())? pm-1:pm; break;
                    case 5 : p.poserBombe(); break;
                    case 6 : pm=0; break;
                }
            }*/
            if (listeJ[tour%nbJ].getP().isVivant()) {
                System.out.println("TOUR DU JOUEUR "+(tour%nbJ+1));
                listeJ[tour%nbJ].jouerTour(m);
                m.explosion();
                m.clean();
                tour++;
                compteurVivant = 0;
                for (i = 0; i < nbJ; i++) {
                    if (listeJ[i].getP().isVivant()) {
                        compteurVivant++;
                    }
                }
            }
        }
        compteurVivant = 0;
        for (i = 0; i < nbJ; i++) {
            if (listeJ[i].getP().isVivant()) {
                compteurVivant++;
                System.out.println("VICTOIRE DU JOUEUR "+(i+1));
            }
        }
        if (compteurVivant==0){
            System.out.println("MATCH NUL");
        }
    }
}



