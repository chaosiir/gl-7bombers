package com.bomber.game.Ennemis;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomber.game.*;
import com.bomber.game.MapetObjet.Case;
import com.bomber.game.MapetObjet.Map;

import java.util.LinkedList;

/**
 * Classe EnnemiPassifAgressif
 * suit un chemin predefini sauf si il repere le personnage dans ce cas se raproche de lui
 */
public class EnnemiPassifAgressif extends Ennemis {
    /**
     * Accesseur du chemin de l'ennemi
     * @return une LinkedList<Case>
     */
    public LinkedList<Case> getChemin() {
        return chemin;
    }
    /**
     * Modificateur du chemin de l'ennemi
     * @param chemin
     */

    public void setChemin(LinkedList<Case> chemin) {
        this.chemin = chemin;
    }

    private LinkedList<Case> chemin;//chemin que l'ennemis doit suivre

    // indice de la case en cours sur le chemin défini
    private int i = 0;
    public Boolean retour=false;//dans quel sens suit on le chemin
    private int portee;//portée de detection

    public int pos;
    /* determine la portée de la vision de l'ennemi. Si portee=2, le joueur sera détecté
    dans un carré de taille 5x5 autour de l'ennemi*/

    private boolean agro = false;

    /**
     * acceusseur de portee
     * @return int
     */
    public int getPortee() {
        return portee;
    }

    /**
     * modificateur portee
     * @param int portee
     */
    public void setPortee(int portee) {
        this.portee = portee;
    }

    /**
     * accesseut agro
     * @return agro
     */
    public boolean isAgro() {
        return agro;
    }

    /**
     * modificateur agro
     * @param boolean agro
     */
    public void setAgro(boolean agro) {
        this.agro = agro;
    }

    /**
     * renvoi un ennemis passif agressif sur la case c avec pm deplacement une detection de portee et si il est aggressif
     * @param vivant
     * @param c
     * @param pm
     * @param portee
     * @param agro
     */
    public EnnemiPassifAgressif(boolean vivant, Case c, int pm, int portee, boolean agro) {
        super(Bomberball.multiTexture[23],vivant, c, pm);
        this.portee = portee;
        this.agro = agro;
        this.chemin=new LinkedList<Case>();
        setAnimationdroite();
        pos=0;

    }

    /**
     * accesseur de agro
     * @return boolean
     */
    public boolean getAgro(){
        return agro;
    }

    /**
     * change l'annimation de l'ennemis pourqu'il regarde à gauche
     */
    @Override
    public void setAnimationgauche() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {//on change regulierement d'image pour faire une annimation
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + ((agro)?"1":"0") + "" + (int) (time * ((agro)?4:1)) % 4+"inv"))));

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
            public boolean act(float delta) {//on alterne rapidement entre gauche et droite
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + 0 + ((((int)(time * 5) % 2) == 0) ? "" : "inv")))));

                return false;
            }
        };
        this.addAction(animation);
    }
    /**
     * change l'annimation de l'ennemis pourqu'il regarde à droite
     */
    @Override
    public void setAnimationdroite() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + ((agro)?"1":"0") + "" + (int) (time * ((agro)?4:1)) % 4))));

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
    public void miseAjour() {//
        prochain_deplacement.clear();
        cheminJoueur();//renvoi le prochain chemin
        while(prochain_deplacement.size()>pm+1){ //Il contient au moins la case où il se trouve
            prochain_deplacement.removeLast();//et le coupe pour qu'il fasse la taille du nombre de deplacement +1
        }
    }
    /**
     *
     * calcul le prochain deplacement de l'ennemis et le place dans prochain_deplacement
     *
     */
    public void cheminJoueur(){
        Map map = this.getC().getMap();//recuperation de la map
        int lignes = 15;
        int colonnes = 13;
        int xc = this.getC().posX();//recuperation des coordonnée de l'ennemis
        int yc = this.getC().posY();
        int h=0; //Si on ne trouve pas un personnage a une portée maximum, on diminue
        Boolean personnage=false;
        int xp=0,yp=0; //On stocke les coordonnées du personnages s'il est détecté

        int i, j;
        int[][] trad = new int[15][13];//creation de la matrice des case libres ou occupés 0=libre 1=occupé
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

        while (!personnage && h < portee) { //Tant que l'on n'a pas trouvé où que l'ennemi ne peut se déplacer
            int var = -(portee - h);
            while (var <= (portee - h) && !personnage) { //Tant que l'on n'a pas testé toutes les possibilités ou que l'on n'a pas trouvé

                int x1=xc+var;
                int x2=xc-var;
                int y1=yc+portee-h-var;
                int y2=yc-(portee-h)+var;

                if(x1<15 && x1>=0 && !personnage){
                    if(y1>=0 && y1<13){
                        if(map.getGrille()[x1][y1].getPersonnage()!=null){
                            personnage=true;
                            xp=x1;
                            yp=y1;
                        }
                    }
                }
                if(x1<15 && x1>=0 && !personnage){
                    if(y2>=0 && y2<13){
                        if(map.getGrille()[x1][y2].getPersonnage()!=null){
                            personnage=true;
                            xp=x1;
                            yp=y2;
                        }
                    }
                }
                if(x2<15 && x2>=0 && !personnage){
                    if(y1>=0 && y1<13){
                        if(map.getGrille()[x2][y1].getPersonnage()!=null){
                            personnage=true;
                            xp=x2;
                            yp=y1;
                        }
                    }
                }
                if(x2<15 && x2>=0 && !personnage){
                    if(y2>=0 && y2<13){
                        if(map.getGrille()[x2][y2].getPersonnage()!=null){
                            personnage=true;
                            xp=x2;
                            yp=y2;
                        }
                    }
                }


                var++;
            }
            h++;
        }

        /** Si le personnage est repéré **/
        if (personnage) {//on devient aggressif
            this.setAgro(true);

            if(tmp[yc+colonnes*xc][yp+colonnes*xp]==1 || tmp[yp+colonnes*xp][yc+colonnes*xc]==1){
                int N = Graphe.ALPHA_NOTDEF;
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

                Graphe graphe = new Graphe(existdij);
                Dijkstra dijkstra = new Dijkstra(yc + colonnes * xc, graphe);
                LinkedList<Integer> disol = dijkstra.afficheChemin(yp + colonnes * xp);


                int pmrestant = pm;

                while (!disol.isEmpty()) {
                    pmrestant--;
                    int casis = disol.removeFirst();//ajout des case du chemin donné par dijkstra dans prochain_deplacement en inversant le sens
                    prochain_deplacement.addFirst(map.getGrille()[casis / colonnes][casis % colonnes]);
                }


            }
            else{
                prochain_deplacement.clear();
                prochain_deplacement.add(c);
            }



        }
        else{
            if(isAgro()){
                this.setAgro(false);
                this.teleportation=true;
                prochain_deplacement.addFirst(c);
                if(chemin.get(pos).getEnnemi()==null && chemin.get(pos).getMur()==null){
                    prochain_deplacement.add(map.getGrille()[chemin.get(pos).posX()][chemin.get(pos).posY()]);
                }
                else{
                    int o=0;
                    while(chemin.get(o).getEnnemi()!=null && chemin.get(o).getMur()!=null && o<chemin.size()){
                        o++;
                    }
                    if(chemin.get(o).getEnnemi()!=null || chemin.get(o).getMur()!=null){
                        prochain_deplacement.add(map.getGrille()[chemin.get(o).posX()][chemin.get(o).posY()]);
                        pos=o;
                    }
                }
            }
            else{
                prochain_deplacement.clear();//sinon on ajoute les case du chemin
                prochain_deplacement.add(c);
                int pmrestant=pm;
                Boolean ok=false;
                if(chemin.size()!=1){
                    if(pos==0){
                        Case casap=chemin.get(1);
                        if(map.getGrille()[casap.posX()][casap.posY()].getMur()==null && map.getGrille()[casap.posX()][casap.posY()].getEnnemi()==null){
                            ok=true;
                        }
                        else{
                            ok=false;
                        }
                    }
                    else if(pos==chemin.size()-1){
                        Case casav=chemin.get(chemin.size()-2);
                        if (map.getGrille()[casav.posX()][casav.posY()].getMur()==null && map.getGrille()[casav.posX()][casav.posY()].getEnnemi()==null){
                            ok=true;
                        }
                        else{
                            ok=false;
                        }
                    }
                    else{
                        Case casap=chemin.get(pos+1);
                        Case casav=chemin.get(pos-1);
                        if((map.getGrille()[casap.posX()][casap.posY()].getMur()==null && map.getGrille()[casap.posX()][casap.posY()].getEnnemi()==null) || (map.getGrille()[casav.posX()][casav.posY()].getMur()==null && map.getGrille()[casav.posX()][casav.posY()].getEnnemi()==null) ){
                            ok=true;

                        }
                        else{
                            ok=false;
                        }
                    }

                }
                while(pmrestant>0 && ok){

                    if(pos==(chemin.size()-1)){ //Si on est au bout du chemin, on retourne en arrière
                        retour=true;
                    }

                    if(pos==0){ //Si on est au début, on ne va pas retourner
                        retour=false;

                    }
                    //Dans le cas général et pas dans ces cas particuliers
                    if(retour){
                        Case aCase=chemin.get(pos-1);
                        if(map.getGrille()[aCase.posX()][aCase.posY()].getMur()==null && (map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi()==null || map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi()==this) ){

                            pmrestant--;
                            prochain_deplacement.add(map.getGrille()[aCase.posX()][aCase.posY()]);
                            pos--;
                        }
                        else{

                            retour=false;
                        }
                    }
                    else {
                        Case aCase = chemin.get(pos + 1);
                        if (map.getGrille()[aCase.posX()][aCase.posY()].getMur() == null && (map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi()==null || map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi()==this) ) {
                            pmrestant--;
                            prochain_deplacement.add(map.getGrille()[aCase.posX()][aCase.posY()]);
                            pos++;
                        } else {
                            retour = true;

                        }
                    }




                }
            }




        }
    }
}

