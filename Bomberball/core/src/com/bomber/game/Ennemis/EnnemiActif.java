package com.bomber.game.Ennemis;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomber.game.*;
import com.bomber.game.MapetObjet.Case;
import com.bomber.game.MapetObjet.Map;

import java.util.LinkedList;
/**
 * Classe EnnemiActif
 * ennemis qui suit le chemin le plus long accessible
 */
public class EnnemiActif extends Ennemis {
    /**
     * renvoit un ennemis actif sur la case c et avec pm mouvement
     * @param vivant
     * @param c
     * @param pm
     */
    public EnnemiActif(boolean vivant, Case c, int pm) {
        super(Bomberball.multiTexture[16], vivant, c, pm);
        setAnimationdroite();

    }
    /**
     * change l'annimation de l'ennemis pourqu'il regarde à gauche
     */
    @Override
    public void setAnimationgauche() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {//change rapidemement d'image pour faire une annimation
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + (int) (time * 6) % 4 + "inv"))));

                return false;
            }
        };
        this.addAction(animation);
    }
    /**
     * change l'annimation de l'ennemis pourqu'il dance lorsque le joueur meurt
     */
    public void setAnimationdefaite() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {//change rapidement de coté
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + 0 + ((((int) (time * 5) % 2) == 0) ? "" : "inv")))));

                return false;
            }
        };
        this.addAction(animation);
    }

    /**
     * accesseur portee
     * @return int
     */
    @Override
    public int getPortee() {
        return 0;
    }

    /**
     * accesseur chemin
     * @return LinkedList<Case>
     */
    @Override
    public LinkedList<Case> getChemin() {
        return null;
    }
    /**
     * change l'annimation de l'ennemis pourqu'il regarde à gauche
     */
    @Override
    public void setAnimationdroite() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {//change rapidemement d'image pour faire une annimation
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("bat" + 0 + "" + (int) (time * 6) % 4))));

                return false;
            }
        };
        this.addAction(animation);

    }
    /**
     *
     * calcul le prochain deplacement de l'ennemis et le place dans prochain_deplacement
     *
     */
    public void miseAjour() {
        prochain_deplacement.clear();
        recherchecheminmaxPL();
        while(prochain_deplacement.size()>pm+1){ //Il contient au moins la case où il se trouve
            prochain_deplacement.removeLast();
        }
    }

    /**
     * accesseur agro
     * @return boolean
     */
    @Override
    public boolean isAgro() {
        return false;
    }

    /**
     * modificateur portee
     * @param int x
     */
    @Override
    public void setPortee(int x) {
//rien
    }



    /**
     * place le chemin le plus long accessible pour l'ennemis et le place dans prochain chemin
     * @author Paul-Louis
     *
     */
    public void recherchecheminmaxPL() {
        Map map = this.getC().getMap();
        int lignes = 15;
        int colonnes = 13;
        int xc = this.getC().posX();
        int yc = this.getC().posY();
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
                    if (trad[i][j] != 1 && trad[i][j + 1] != 1) {
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



        int xa = -1, ya = -1; //Stockage de la case finale



        /** On teste s'il existe un chemin avant de le chercher**/
        int l = 0; //Si on ne trouve pas de chemin pm, on se rabat sur un chemin de taille pm-l;
        Boolean trouve = false;
        while (!trouve && l < pm) { //Tant que l'on n'a pas trouvé où que l'ennemi ne peut se déplacer
            k = 0;
            while (k <= (pm - l) && !trouve) { //Tant que l'on n'a pas testé toutes les possibilités ou que l'on n'a pas trouvé
                int x1=xc+k;
                int x2=xc-k;
                int y1=yc+pm-l-k;
                int y2=yc-(pm-l)+k;
                if(x1<15 && x1>=0 && !trouve){
                    if(y1>=0 && y1<13){
                        if(tmp[yc+colonnes*xc][y1+colonnes*x1]==1 || tmp[y1+colonnes*x1][yc+colonnes*xc]==1){
                            trouve=true;
                            xa=x1;
                            ya=y1;
                        }
                    }
                }
                if(x1<15 && x1>=0 && !trouve){
                    if(y2>=0 && y2<13){
                        if(tmp[yc+colonnes*xc][y2+colonnes*x1]==1 || tmp[y2+colonnes*x1][yc+colonnes*xc]==1){
                            trouve=true;
                            xa=x1;
                            ya=y2;
                        }
                    }
                }
                if(x2<15 && x2>=0 && !trouve){
                    if(y1>=0 && y1<13){
                        if(tmp[yc+colonnes*xc][y1+colonnes*x2]==1 || tmp[y1+colonnes*x2][yc+colonnes*xc]==1){
                            trouve=true;
                            xa=x2;
                            ya=y1;
                        }
                    }
                }
                if(x2<15 && x2  >=0 && !trouve){
                    if(y2>=0 && y2<13){
                        if(tmp[yc+colonnes*xc][y2+colonnes*x2]==1 || tmp[y2+colonnes*x2][yc+colonnes*xc]==1){
                            trouve=true;
                            xa=x2;
                            ya=y2;
                        }
                    }
                }
                k++;
            }
            l++;
        }

        if (!trouve) {
            prochain_deplacement.clear();
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

            for(int f: disol){//on recupere le chemin donnée par dijkstra et on inverse son sens et on la met dans prochain_deplacement
                prochain_deplacement.addFirst(map.getGrille()[f/colonnes][f%colonnes]);
            }
        }
    }
}