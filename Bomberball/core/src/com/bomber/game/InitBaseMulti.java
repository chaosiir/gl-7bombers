package com.bomber.game;
import java.util.Random;
import java.math.*;

public class InitBaseMulti {
    private int[][] grille;
    private Case [] cDestru;
    //0 : vide
    //1 : indestructible
    //2 : destructible


    //permet de creer une liste de couples d'entiers, correspondant aux cases potentiellemnt destructibles en multijoueur
    public Case[] casesDestru(){
        int i;
        int j;
        int cpt=1;
        Case a = new Case();
        a.setY(1);
        for (i=4;i<11;i++){
            a.setX(i);
            cDestru[cpt]=a;
            cpt++;
        }
        a.setY(2);
        for (i=3;i<12;i++){
                if (i%2==0) {
                    a.setX(i);
                    cDestru[cpt]=a;
                    cpt++;
                }
        }
        a.setY(3);
        for (i=2;i<13;i++){
            a.setX(i);
            cDestru[cpt]=a;
            cpt++;
        }
        for (j=4;j<8;j++){
            a.setY(j);
            for (i=1;i<14;i++){
                if (j%2==0 || i%2==1){
                    a.setX(i);
                    cDestru[cpt]=a;
                    cpt++;
                }
            }
        }
        a.setY(9);
        for (i=2;i<13;i++){
            a.setX(i);
            cDestru[cpt]=a;
            cpt++;
        }
        a.setY(10);
        for (i=3;i<12;i++){
            if (i%2==0) {
                a.setX(i);
                cDestru[cpt]=a;
                cpt++;
            }
        }
        a.setY(11);
        for (i=4;i<11;i++){
            a.setX(i);
            cDestru[cpt]=a;
            cpt++;
        }
        return cDestru;
    }

    public int[][] generateDestru(int[][] grille, Case[] cDestru,int nbDestru){
        int i;
        int j;
        int r;
        int x;
        int y;
        Case[] temp;
        for (i=1;i<nbDestru+1;i++){
            r = 1 + (int)(Math.random() * (89-i));
            x=cDestru[r].getX();
            y=cDestru[r].getY();
            cDestru[r]=cDestru[89+1-i];//probleme pour la suppression d'un élément
            grille[x][y]=2;
        }
        for (i=1;i<13;i++){
            for (j=1;j<11;j++){
                if (j%2==0 && i%2==0){
                    grille[i][j]=1;
                }
            }
        }
        return grille;
    }

    public static void main(String [] args){
        int i;
        int j;
        for (i=1;i<=15;i++){
            int random = (int)(Math.random() * 3);
            System.out.println(random);
        }
    }

}
