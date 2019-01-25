package com.bomber.game.Ennemis;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;
import com.bomber.game.MapetObjet.Map;

import java.util.LinkedList;

/**
 * Classe EnnemiPassif
 * ennemis qui suit un chemin predefini
 */
public class EnnemiPassif extends Ennemis {
    public int pos;
    public Boolean retour=false;

    /**
     * renvoi un ennemis sur la case c qui peut se deplacer de pm mouvement
     * @param vivant
     * @param c
     * @param pm
     */
    public EnnemiPassif(boolean vivant, Case c, int pm) {
        super(Bomberball.multiTexture[17],vivant,c,pm);
        this.chemin=new LinkedList<Case>();
        setAnimationdroite();
        pos=0;
    }

    /**
     * accesseur Chemin
     * @return LinkedList<Case>
     */
    public LinkedList<Case> getChemin() {
        return chemin;
    }

    /**
     * modificateur chemin
     * @param chemin
     */
    public void setChemin(LinkedList<Case> chemin) {
        this.chemin=chemin;
    }

    private LinkedList<Case> chemin;


    private int i = 0;           // indice de la case en cours sur le chemin défini

    /**
     *
     * calcul le prochain deplacement de l'ennemis et le place dans prochain_deplacement
     *
     */
    public void miseAjour() {
        prochain_deplacement.clear();
        prochain_deplacement.add(c);
        Map map=this.getC().getMap();   //recuperation de la map
        int pmrestant=pm;
        Boolean ok=false;
        if(chemin.size()!=1){           //si le chemin n'est pas vide ,test si on peut aller sur la prochaine case
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
        while(pmrestant>0 && ok){       //tant qu'il reste des pm

            if(pos==(chemin.size()-1)){ //Si on est au bout du chemin, on retourne en arrière
                retour=true;
            }

            if(pos==0){ //Si on est au début, on ne va pas retourner
                    retour=false;

            }
                //Dans le cas général et pas dans ces cas particuliers
            if(retour) {
                Case aCase = chemin.get(pos - 1);
                if (map.getGrille()[aCase.posX()][aCase.posY()].getMur() == null && (map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi() == null || map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi() == this)) {

                    pmrestant--;
                    prochain_deplacement.add(map.getGrille()[aCase.posX()][aCase.posY()]);
                    pos--;
                } else {

                    retour = false;
                }
            }
            else {
                Case aCase = chemin.get(pos + 1);
                if (map.getGrille()[aCase.posX()][aCase.posY()].getMur() == null && (map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi() == null || map.getGrille()[aCase.posX()][aCase.posY()].getEnnemi() == this)) {
                    pmrestant--;
                    prochain_deplacement.add(map.getGrille()[aCase.posX()][aCase.posY()]);
                    pos++;
                } else {
                    retour = true;

                }
            }
        }
    }

    /**
     * accesseur de agro
     * @return boolean
     */
    @Override
    public boolean isAgro() {
        return false;
    }

    /**
     * modificateur de portee
     * @param  x
     */
    @Override
    public void setPortee(int x) {
//rien
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
            public boolean act(float delta) {   //change rapidemement d'image pour faire une animation
                time += delta;
                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + (int) (time * 1) % 4))));
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
            public boolean act(float delta) {   //change rapidement de coté
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + 0 + ((((int)(time * 5) % 2) == 0) ? "" : "inv")))));

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

    @Override

    /**
     * change l'annimation de l'ennemis pour qu'il regarde à gauche
     */
    public void setAnimationgauche() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {   //change rapidemement d'image pour faire une animation
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + (int) (time * 1) % 4+"inv"))));

                return false;
            }
        };
        this.addAction(animation);
    }
}
