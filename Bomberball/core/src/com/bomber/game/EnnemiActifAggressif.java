package com.bomber.game;

import java.util.ArrayList;
import java.util.LinkedList;

public class EnnemiActifAggressif extends Ennemis {
    private LinkedList<Case>  chemin;
    private int portee;


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
    public LinkedList<Case> voisinAccessibles (Case caseC){

        Map m=caseC.getMap();
        Case[][] grille = m.getGrille();
        int a = caseC.posX();
        int b = caseC.posY();
        LinkedList<Case> voisin = new LinkedList<Case>();

        if (caseLibre(grille[a - 1][b])) {
            voisin.add(grille[a - 1][b]);
        }

        if (caseLibre(grille[a][b + 1])) {
            voisin.add(grille[a][b + 1]);
        }

        if (caseLibre(grille[a + 1][b])) {
            voisin.add(grille[a + 1][b]);
        }

        if (caseLibre(grille[a][b - 1])) {
            voisin.add(grille[a][b - 1]);
        }

        return voisin;
    }


    /* Met à jour le chemin de l'ennemi pour qu'il soit maximum */
    public void cheminMax() {

        LinkedList<Case> visites = new LinkedList<Case>();
        visites.add(c);

        chemin = cheminMaxAux(visites, pm - 1);

    }

    /* Calcul par récursivité le chemin qui emmène l'ennemi le plus loin possible de la case oùù il se trouve */
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites, int pmRestants) {
        //case initiale = visites[pm - pmRestants]
        LinkedList<Case> res = new LinkedList<Case>();

        // cas de base: l'ennemi ne peut pas aller plus loin
        if (pmRestants == 0) {
            return res;
        } else {
            LinkedList<Case> voisins = voisinAccessibles(visites.get(pm - pmRestants - 1));

            LinkedList<Case> cheminProvisoire = new LinkedList<Case>();


            // sinon on parcours les cases voisines non visitées
            for (Case a : voisins) {
                if (!visites.contains(a)) {
                    visites.set(pm - pmRestants, a);
                    cheminProvisoire = cheminMaxAux(visites, pmRestants - 1);
                    if (cheminProvisoire.size() > res.size()) {
                        res = cheminProvisoire;
                    }
                }
            }
            return res;
        }
    }

    // Met à jour la variable poursuivre si le joueur est détecté par l'ennemi
    public boolean detection(){
        Map m=c.getMap();
        Case[][] grille = m.getGrille();
        int a = c.posX();
        int b = c.posY();

        // on récupère la position du personnage
        Personnage personnage=m.findActor("Personnage");
        int xPerso = personnage.getC().posX();
        int yPerso = personnage.getC().posY();


        // On crée l'intervale des cases à surveiller
        int a1 = Math.max(a-portee,0);
        int a2 = Math.min(13,a+portee);

        int b1 = Math.max(b-portee,0);
        int b2 = Math.min(11,a+portee);

        // Si le joueur est à portée de l'ennemi, il se fera alors poursuivre
        return (((a1<=xPerso)&&(xPerso<=a2)) && ((a1<=xPerso)&&(xPerso<=a2)));
        }

    /* Met à jour le chemin de l'ennemi pour qu'il trouve le chemin minimum vers le joueur*/
    public void cheminMin() {

        Map m=c.getMap();
        Case[][] grille = m.getGrille();

        // on récupère la case du personnage
        Personnage personnage=m.findActor("Personnage");
        Case cPerso = personnage.getC();

        /* On créer une liste de liste de case pour parcourir tous les chemins partant de la case de
        l'ennemi, case par case, jusqu'à trouver le joueur, et ainsi obtenir le plus court chemin vers lui.
         */

        LinkedList<LinkedList<Case>> listeChemin = new LinkedList<LinkedList<Case>>();

        // On mémorise les cases déjà visitées
        LinkedList<Case> visites = new LinkedList<Case>();
        visites.add(c);

        // Indice de parcours du tableau visites
        int k = 0;

        // Tant que l'on a pas trouvé le joueur, on continu de le chercher
        while (!visites.contains(cPerso)) {
            Case temp = visites.get(k);
            LinkedList<Case> voisins = voisinAccessibles(temp);
            for (Case suivant : voisins) {
                if (!visites.contains(suivant)) {
                    visites.add(suivant);
                    for (LinkedList<Case> cheminPasse : listeChemin) {
                        if (cheminPasse.getLast() == temp) {
                            cheminPasse.add(suivant);
                            if (suivant== cPerso) {
                                // On a trouver un chemin vers le joueur: il est l'une des solutions optimales
                                chemin = cheminPasse;
                            }
                        }
                    }
                }
            }
            k=k+1;
        }
    }


    public int[][] traducteur(){
        Map m=c.getMap();
        int[][] res = new int[15][13];
        for(int i=0;i<15;i++){
            for (int j=0;j<13;j++){
                if(m.getGrille()[i][j].getMur()!= null){
                    res[i][j]=1
                }
                else if(m.getGrille()[i][j].getMur()== null){
                    if(m.getGrille()[i][j]==c || m.getGrille()[i][j].getPersonnage()!=null ){
                        res[i][j]=2;
                    }
                    else{
                        res[i][j]=0;
                    }
                }
            }
        }
        return res;
    }


    public boolean verifAgro(){
        return verifSolotraducteur();
    }


    public void miseAjour() {
        boolean poursuivre = detection();
        if (poursuivre&&verifAgro()) {
            cheminMin();
        } else {
            cheminMax();
        }
    }


    public void main() {
        Map map = Map.genererMapSolo(20, 10);

    }


    }
