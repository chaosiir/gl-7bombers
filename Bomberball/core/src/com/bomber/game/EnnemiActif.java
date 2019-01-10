package com.bomber.game;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnnemiActif extends Ennemis {
    private LinkedList<Case>  chemin;


    /* fonction permettant de tester si une case est occupée ou non par un mur ou un autre ennemi*/
    public boolean caseLibre(Case caseC){
        Map m=caseC.getMap();
        Mur mur=caseC.getMur();
        Ennemis ennemi=caseC.getEnnemi();
        if ((ennemi==null)||(mur==null)){
            return true;
        }
        else return false;
    }

    /* fonction renvoyant la liste des cases non occupées par un mur ou un autre ennemi autour de la case donnée*/
    public LinkedList<Case> voisinAccessibles (Case caseC){
        Map m=caseC.getMap();
        Case[][] grille = m.getGrille();
        int a=caseC.posX();
        int b=caseC.posY();
        LinkedList<Case> voisin = new LinkedList<Case>();

        if (caseLibre(grille[a-1][b])) {
            voisin.add(grille[a-1][b]);
        }

        if (caseLibre(grille[a][b+1])) {
            voisin.add(grille[a][b+1]);
        }

        if (caseLibre(grille[a+1][b])) {
            voisin.add(grille[a+1][b]);
        }

        if (caseLibre(grille[a][b-1])) {
            voisin.add(grille[a][b-1]);
        }

        return voisin;
    }


    /* Met à jour le chemin de l'ennemi pour qu'il soit maximum */
    public void cheminMax(){

        LinkedList<Case> visites = new LinkedList<Case>();
        visites.add(c);

        chemin = cheminMaxAux(visites,pm-1);

    }

    /* Calcul par récursivité le chemin qui emmène l'ennemi le plus loin possible de la case où il se trouve */
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites,int pmRestants){
        //case initiale = visites[pm - pmRestants]
        LinkedList<Case> res = new LinkedList<Case>();

        // cas de base: l'ennemi ne peut pas aller plus loin
        if (pmRestants ==0) {
            return res;
        }
        else {
            LinkedList<Case> voisins = voisinAccessibles(visites.get( pm - pmRestants - 1));

            LinkedList<Case> cheminProvisoire = new LinkedList<Case>();


            // sinon on parcours les cases voisines non visitées
            for (Case a : voisins) {
                if (!visites.contains(a)) {
                    visites.set( pm - pmRestants,a) ;
                    cheminProvisoire = cheminMaxAux(visites, pmRestants - 1);
                    if (cheminProvisoire.size() > res.size()) {
                        res = cheminProvisoire;
                    }
                }
            }
            return res;
        }
    }

    public void miseAjour(){
        cheminMax();
    }




    public void main(){
        Map map=Map.genererMapSolo(20,10);

    }


}
