package com.bomber.game;

import java.util.LinkedList;

public class EnnemiActifAggressif extends Ennemis {
    private int portee;
    /* determine la portée de la vision de l'ennemi. Si portee=2, le joueur sera détecté
    dans un carré de taille 5x5 autour de l'ennemi*/

    private boolean agro = false;

    public int getPortee() {
        return portee;
    }

    public void setPortee(int portee) {
        this.portee = portee;
    }

    public boolean isAgro() {
        return agro;
    }

    public void setAgro(boolean agro) {
        this.agro = agro;
    }

    public EnnemiActifAggressif(boolean vivant, Case c, int pm, int portee, boolean agro) {
        super(vivant, c, pm);
        this.portee = portee;
        this.agro = agro;
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
    public LinkedList<Case> cheminMax(int pmMax, Case cChemin) {

        LinkedList<Case> visites = new LinkedList<Case>();
        visites.add(cChemin);

        return cheminMaxAux(visites, pmMax, pmMax - 1);

    }

    /* Calcul par récursivité le chemin qui emmène l'ennemi le plus loin possible de la case où il se trouve */
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites,int pmMax, int pmRestants){
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
                    if (visites.size()==(pmMax-pmRestants)){
                        visites.add(a) ;
                    }
                    else{
                        visites.set(pmMax - pmRestants,a) ;
                    }
                    cheminProvisoire = cheminMaxAux(visites, pmMax,pmRestants - 1);
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
        int b2 = Math.min(11,b+portee);

        // Si le joueur est à portée de l'ennemi, il se fera alors poursuivre
        return (((a1<=xPerso)&&(xPerso<=a2)) && ((a1<=xPerso)&&(xPerso<=a2)));
        }

    /* Met à jour le chemin de l'ennemi pour qu'il trouve le chemin minimum vers le joueur
    *  Pré: requis On suppose qu'il existe bien un chemin reliant l'ennemi au joueur. */
    public void cheminMin() {

        Map m=c.getMap();
        Case[][] grille = m.getGrille();

        // on récupère la case du personnage
        Personnage personnage=m.findActor("Personnage");
        Case cPerso = personnage.getC();

        /* On créer une liste de liste de case pour parcourir tous les chemins partants de la case de
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
                    // On parcours les nouvelles cases voisines et on les sauvegarde dans voisins
                    visites.add(suivant);
                    int n = listeChemin.size();
                    int i=0;
                    /* On parcours tous les chemins existants  dont la dernière case est "suivant" et on rajoute alors
                    alors ceux qui permettent d'atteindre la nouvelle case visitée,
                    */
                    for (i=0;i<n;i++) {
                        LinkedList<Case> cheminNouveau = listeChemin.get(i);
                        if (cheminNouveau.getLast() == temp) {
                            cheminNouveau.add(suivant);
                            listeChemin.add(cheminNouveau);
                            if (suivant== cPerso) {
                                // On a trouver un chemin vers le joueur: il est l'une des solutions optimales
                                prochain_deplacement = cheminNouveau;
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
                    res[i][j]=1;
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
        Map map=c.getMap();
        int[][] t = traducteur();
        return map.verifSolo(t);
    }


    public void miseAjour() {
        boolean poursuivre = detection();
        if (poursuivre&&verifAgro()) {
            cheminMin();
            agro=true;
        } else {
            int i= 0;
            Case suivante = c;
            LinkedList<Case> cheminProvisoire = cheminMax(pm, suivante);
            suivante= cheminProvisoire.get(1);
            while (i<pm && caseLibre(suivante) ){
                prochain_deplacement.add(suivante);
                i = i+1;
                suivante= cheminProvisoire.get(i);
            }
            agro=false;
        }
    }

}