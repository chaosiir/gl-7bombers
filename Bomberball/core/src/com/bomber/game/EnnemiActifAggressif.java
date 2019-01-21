package com.bomber.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.LinkedList;

public class EnnemiActifAggressif extends Ennemis {
    private int portee;
    /* determine la portée de la vision de l'ennemi. Si portee=2, le joueur sera détecté
    dans un carré de taille 5x5 autour de l'ennemi*/

    private boolean agro = false;

    public int getPortee() {
        return portee;
    }

    @Override
    public LinkedList<Case> getChemin() {
        return null;
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
        super(Bomberball.multiTexture[24],vivant, c, pm);
        this.portee = portee;
        this.agro = agro;
        setAnimationdroite();
    }

    public boolean getAgro(){
        return agro;
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
    public LinkedList<Case> cheminMaxAux(LinkedList<Case> visites, int pmMax, int pmRestants) {
        //case initiale = visites[pm - pmRestants -1]
        LinkedList<Case> res = new LinkedList<Case>();

        // cas de base: l'ennemi ne peut pas aller plus loin
        if (pmRestants == 0) {
            return res;
        } else {
            LinkedList<Case> voisins = voisinAccessibles(visites.getLast());

            LinkedList<Case> cheminProvisoire = new LinkedList<Case>();


            // sinon on parcours les cases voisines non visitées
            for (Case a : voisins) {
                if (!visites.contains(a)) {
                    if (visites.size() == (pmMax - pmRestants)) {
                        visites.add(a);
                    } else {
                        visites.set(pmMax - pmRestants, a);
                    }
                    cheminProvisoire = cheminMaxAux(visites, pmMax, pmRestants - 1);
                    if (cheminProvisoire.size() > res.size()) {
                        res = cheminProvisoire;
                    }
                }
            }
            return res;
        }
    }

    // Met à jour la variable poursuivre si le joueur est détecté par l'ennemi
    public boolean detection() {
        Map m = c.getMap();
        Case[][] grille = m.getGrille();
        int a = c.posX();
        int b = c.posY();

        // on récupère la position du personnage
        Personnage personnage = m.findActor("Personnage");
        int xPerso = personnage.getC().posX();
        int yPerso = personnage.getC().posY();


        // On crée l'intervale des cases à surveiller
        int a1 = Math.max(a - portee, 0);
        int a2 = Math.min(13, a + portee);

        int b1 = Math.max(b - portee, 0);
        int b2 = Math.min(11, b + portee);

        // Si le joueur est à portée de l'ennemi, il se fera alors poursuivre
        return (((a1 <= xPerso) && (xPerso <= a2)) && ((a1 <= xPerso) && (xPerso <= a2)));
    }

    /* Met à jour le chemin de l'ennemi pour qu'il trouve le chemin minimum vers le joueur
     *  Pré: requis On suppose qu'il existe bien un chemin reliant l'ennemi au joueur. */
    public void cheminMin() {

        Map m = c.getMap();
        Case[][] grille = m.getGrille();

        // on récupère la case du personnage
        Personnage personnage = m.findActor("Personnage");
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
                    int i = 0;
                    /* On parcours tous les chemins existants  dont la dernière case est "suivant" et on rajoute alors
                    alors ceux qui permettent d'atteindre la nouvelle case visitée,
                    */
                    for (i = 0; i < n; i++) {
                        LinkedList<Case> cheminNouveau = listeChemin.get(i);
                        if (cheminNouveau.getLast() == temp) {
                            cheminNouveau.add(suivant);
                            listeChemin.add(cheminNouveau);
                            if (suivant == cPerso) {
                                // On a trouver un chemin vers le joueur: il est l'une des solutions optimales
                                prochain_deplacement = cheminNouveau;
                            }
                        }
                    }
                }
            }
            k = k + 1;
        }
    }


    public int[][] traducteur() {
        Map m = c.getMap();
        int[][] res = new int[15][13];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 13; j++) {
                if (m.getGrille()[i][j].getMur() != null) {
                    res[i][j] = 1;
                } else if (m.getGrille()[i][j].getMur() == null) {
                    if (m.getGrille()[i][j] == c || m.getGrille()[i][j].getPersonnage() != null) {
                        res[i][j] = 2;
                    } else {
                        res[i][j] = 0;
                    }
                }
            }
        }
        return res;
    }


    public boolean verifAgro() {
        Map map = c.getMap();
        int[][] t = traducteur();
        return map.verifSolo(t);
    }


    @Override
    public void setAnimationgauche() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + ((agro)?"1":"0") + "" + (int) (time * ((agro)?12:6)) % 4+"inv"))));

                return false;
            }
        };
        this.addAction(animation);
    }

    @Override
    public void setAnimationdroite() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + ((agro)?"1":"0") + "" + (int) (time * ((agro)?12:6)) % 4))));

                return false;
            }
        };
        this.addAction(animation);
    }

    @Override
    public void setAnimationdefaite() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" +0+ ((((int)(time * 5) % 2)==0)?"":"inv")))));

                return false;
            }
        };
        this.addAction(animation);
    }

    public void miseAjour() {
     prochain_deplacement.clear();
     prochain_deplacement=recherchecheminmaxPL();
     while(prochain_deplacement.size()>pm+1){ //Il contient au moins la case où il se trouve
            prochain_deplacement.removeLast();
     }


    }
    public LinkedList<Case> recherchecheminmaxPL() {
        Map map = this.getC().getMap();
        int lignes = 15;
        int colonnes = 13;
        int xc = this.getC().posX();
        int yc = this.getC().posY();
        int h=0; //Si on ne trouve pas un personnage a une portée maximum, on diminue
        Boolean personnage=false;
        int xp=0,yp=0; //On stocke les coordonnées du personnages s'il est détecté

        int i, j;
        int[][] trad = new int[15][13];
        for (i = 0; i < 15; i++) {
            for (j = 0; j < 13; j++) {
                if (map.getGrille()[i][j].getMur() != null) { //On ne peut pas passer s'il y a un mur
                    trad[i][j] = 1;
                } else if (map.getGrille()[i][j].getEnnemi() != null && map.getGrille()[i][j].posX() != c.posX() && map.getGrille()[i][j].posY() != c.posY()) {
                    trad[i][j] = 1;
                } else if (map.getGrille()[i][j].getPorte() != null) {
                    trad[i][j] = 1;
                } else {
                    trad[i][j] = 0;
                }
            }
        }

        int tmp[][] = new int[lignes * colonnes][lignes * colonnes];
        int exist[][] = new int[lignes * colonnes][lignes * colonnes]; //On prépare la matrice d'existence de lien (numéroté dans le sens de la gauche vers la droite et on retourne à chaque ligne) Ainsi t[i,j]=j+11*i
        for (i = 0; i < lignes; i++) {
            for (j = 0; j < colonnes; j++) {
                if (j > 0 && j < colonnes - 1) {
                    if (trad[i][j] != 1 && trad[i][j - 1] != 1) {
                        exist[j + colonnes * i][j - 1 + colonnes * i] = 1;
                        exist[j - 1 + colonnes * i][j + colonnes * i] = 1;
                    }
                    if (trad[i][j] != 1 && trad[i][j + 1] != -1) {
                        exist[j + colonnes * i][j + 1 + colonnes * i] = 1;
                        exist[j + 1 + colonnes * i][j + colonnes * i] = 1;
                    }
                }
                if (i > 0 && i < lignes - 1) {
                    if (trad[i][j] != 1 && trad[i - 1][j] != 1) {
                        exist[j + colonnes * i][j + colonnes * (i - 1)] = 1;
                        exist[j + colonnes * (i - 1)][j + colonnes * i] = 1;
                    }
                    if (trad[i][j] != 1 && trad[i + 1][j] != 1) {
                        exist[j + colonnes * i][j + colonnes * (i + 1)] = 1;
                        exist[j + colonnes * (i + 1)][j + colonnes * i] = 1;
                    }
                }
            }
        }

        /** Utilisation de Warshall pour pouvoir vérifier l'existence**/
        int k;
        for (i = 0; i < colonnes * lignes; i++) {
            for (j = 0; j < colonnes * lignes; j++) {
                tmp[i][j] = exist[i][j];
            }
        }
        for (k = 0; k < colonnes * lignes; k++) {
            for (i = 0; i < colonnes * lignes; i++) {
                for (j = 0; j < colonnes * lignes; j++) {
                    if (tmp[i][k] == 1 && tmp[k][j] == 1) {
                        tmp[i][j] = 1;
                    }
                }
            }
        }

        while (!personnage && h < portee) { //Tant que l'on n'a pas trouvé où que l'ennemi ne peut se déplacer
            int var = -(portee - h);
            while (var <= (portee - h) && !personnage) { //Tant que l'on n'a pas testé toutes les possibilités ou que l'on n'a pas trouvé

                if(var>=0){
                    if ((xc + (portee - h - var)) >= 0 && (xc + (portee - h - var)) <= 14) { //Vérification que ces cases existent dans une map
                        if (yc + var >= 0 && yc + var <= 12) {
                            if (map.getGrille()[xc + (portee - h - var)][yc + var].getPersonnage()!=null) {
                                personnage = true;
                                xp = xc + (portee - h) - var;
                                yp = yc + var;
                            }
                        }
                    }
                }
                if(var<0){
                    if ((xc + var) >= 0 && (xc+var) <= 14) { //Vérification que ces cases existent dans une map
                        if ((yc - (var+portee-h)) >= 0 && (yc - (var+portee-h)) <= 12) {
                            if (map.getGrille()[xc + var][yc - (var+portee-h)].getPersonnage()!=null) {
                                personnage = true;
                                xp = xc + var;
                                yp = yc - (var+portee-h);
                            }
                        }
                    }
                }

                var++;
            }
            h++;
        }

        /** Si le personnage est repéré **/
        if(personnage){
            this.setAgro(true);

            int N = Graphe.ALPHA_NOTDEF ;
            int existdij[][] = new int[13 * 15][13 * 15];

            for (i = 0; i < 13 * 15; i++) {
                for (j = 0; j < 13 * 15; j++) {
                    if (exist[i][j] == 1) {
                        existdij[i][j] = 1;
                    } else {
                        existdij[i][j] = N;
                    }
                }
            }

            Graphe graphe=new Graphe(existdij);
            Dijkstra dijkstra=new Dijkstra(yc+colonnes*xc,graphe);
            LinkedList<Integer> disol=dijkstra.afficheChemin(yp+colonnes*xp);

            int pmrestant=pm;

            while(!disol.isEmpty() && pmrestant>0){
                pmrestant--;
                int casis=disol.removeFirst();
                prochain_deplacement.addFirst(map.getGrille()[casis/colonnes][casis%colonnes]);
            }

            return prochain_deplacement;

        }

        /** Si le personnage n'est pas repéré **/
        else{
            this.setAgro(false);

            int xa = -1, ya = -1; //Stockage de la case finale




            /** On teste s'il existe un chemin avant de le chercher**/
            int l = 0; //Si on ne trouve pas de chemin pm, on se rabat sur un chemin de taille pm-l;
            Boolean trouve = false;
            while (!trouve && l < pm) { //Tant que l'on n'a pas trouvé où que l'ennemi ne peut se déplacer
                k = -(pm - l);
                while (k <= (pm - l) && !trouve) { //Tant que l'on n'a pas testé toutes les possibilités ou que l'on n'a pas trouvé

                    if(k>=0){
                        if ((xc + (pm - l - k)) >= 0 && (xc + (pm - l - k)) <= 14) { //Vérification que ces cases existent dans une map
                            if (yc + k >= 0 && yc + k <= 12) {
                                if (tmp[yc + colonnes * xc][yc + k + colonnes * (xc + (pm - l) - k)] == 1 || tmp[yc + k + colonnes * (xc + (pm - l) - k)][yc + colonnes * xc] == 1) {
                                    trouve = true;
                                    xa = xc + (pm - l) - k;
                                    ya = yc + k;
                                }
                            }
                        }
                    }
                    if(k<0){
                        if ((xc + k) >= 0 && (xc+k) <= 14) { //Vérification que ces cases existent dans une map
                            if ((yc - (k+pm-l)) >= 0 && (yc - (k+pm-l)) <= 12) {
                                if (tmp[yc + colonnes * xc][(yc - (k+pm-l)) + colonnes * (xc + k)] == 1 || tmp[(yc - (k+pm-l))+ colonnes * (xc + k)][yc + colonnes * xc] == 1) {
                                    trouve = true;
                                    xa = xc + k;
                                    ya = yc - (k+pm-l);
                                }
                            }
                        }
                    }

                    k++;
                }
                l++;
            }

            if (!trouve) {
                prochain_deplacement.clear();
                return prochain_deplacement;
            }

            /** Implémentation de Dijkstra pour avoir le chemin**/

            else {
                int N = Graphe.ALPHA_NOTDEF ;
                int existdij[][] = new int[13 * 15][13 * 15];

                for (i = 0; i < 13 * 15; i++) {
                    for (j = 0; j < 13 * 15; j++) {
                        if (exist[i][j] == 1) {
                            existdij[i][j] = 1;
                        } else {
                            existdij[i][j] = N;
                        }
                    }
                }

                Graphe graphe=new Graphe(existdij);
                Dijkstra dijkstra=new Dijkstra(yc+colonnes*xc,graphe);
                LinkedList<Integer> disol=dijkstra.afficheChemin(ya+colonnes*xa);

                for(int f: disol){
                    prochain_deplacement.addFirst(map.getGrille()[f/colonnes][f%colonnes]);
                }
                return prochain_deplacement;


            }

        }






































    }
}