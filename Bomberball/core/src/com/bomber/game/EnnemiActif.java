package com.bomber.game;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnnemiActif extends Ennemis {




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

        prochain_deplacement = cheminMaxAux(visites,pm-1);

    }

    /* Calcul par récursivité le chemin qui emmène l'ennemi le plus loin possible de la case où il se trouve */
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites,int pmRestants){
        //case initiale = visites[pm - pmRestants -1]
        LinkedList<Case> res = new LinkedList<Case>();

        // cas de base: l'ennemi ne peut pas aller plus loin
        if (pmRestants ==0) {
            return res;
        }
        else {
            LinkedList<Case> voisins = voisinAccessibles(visites.getLast());

            LinkedList<Case> cheminProvisoire = new LinkedList<Case>();


            // sinon on parcours les cases voisines non visitées
            for (Case a : voisins) {
                if (!visites.contains(a)) {
                    if (visites.size()==(pm-pmRestants)){
                        visites.add(a) ;
                    }
                    else{
                        visites.set(pm - pmRestants,a) ;
                    }
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
}
