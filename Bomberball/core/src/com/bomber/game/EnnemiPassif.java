package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.LinkedList;

public class EnnemiPassif extends Ennemis {


    public EnnemiPassif(boolean vivant,Case c,int pm) {
        super(Bomberball.multiTexture[17],vivant,c,pm);
        this.chemin=new LinkedList<Case>();
        setAnimationdroite();
    }

    public LinkedList<Case> getChemin() {
        return chemin;
    }

    public void setChemin(LinkedList<Case> chemin) {
        this.chemin=chemin;
    }

    private LinkedList<Case> chemin;

    // indice de la case en cours sur le chemin défini
    private int i = 0;


    public void miseAjour() {
        prochain_deplacement.clear();
        Map map=this.getC().getMap();
        int m=0;
        int emplacementdeb=0;
        for(Case cas: chemin){ //Trouver emplacement de l'ennemi sur son chemin
            if(cas.posX()==c.posX() && cas.posY()==c.posY()){
                emplacementdeb=m;
            }
            else {
                m++;
            }
        }
        int taillechemin=chemin.size();
        int pmrestant=pm;
        int pos=emplacementdeb;
        while(pmrestant>0){
            if(pos<(taillechemin-1)){
                if(map.getGrille()[chemin.get(pos+1).posX()][chemin.get(pos+1).posY()].getMur()==null && map.getGrille()[chemin.get(pos+1).posX()][chemin.get(pos+1).posY()].getEnnemi()==null ){
                    prochain_deplacement.add(chemin.get(pos+1));
                    pos++;
                    pmrestant--;
                }
                else{ //Si la prochaine case est occupée, on regarde si on peut reculer
                    if(pos>0){ //Si on est pas déjà à la première case
                        if(map.getGrille()[chemin.get(pos-1).posX()][chemin.get(pos-1).posY()].getMur()==null && map.getGrille()[chemin.get(pos-1).posX()][chemin.get(pos-1).posY()].getEnnemi()==null ){
                            prochain_deplacement.add(chemin.get(pos-1));
                            pos--;
                            pmrestant--;
                        }
                    }
                    else{
                        //On ne peut pas bouger donc on quitte sans utiliser tous les pm
                        pmrestant=0;
                    }
                }

            }
            else{ //On est arrivé au bout du chemin, on fait donc demi-tour si possible
                if(map.getGrille()[chemin.get(pos-1).posX()][chemin.get(pos-1).posY()].getMur()==null && map.getGrille()[chemin.get(pos-1).posX()][chemin.get(pos-1).posY()].getEnnemi()==null ){
                    prochain_deplacement.add(chemin.get(pos-1));
                    pos--;
                    pmrestant--;
                }
                else{
                    //On est au bout du chemin et on ne peut reculer donc on quitte sans utiliser tous les pm
                    pmrestant=0;
                }
            }
        }



    }

    @Override
    public boolean isAgro() {
        return false;
    }

    @Override
    public void setAnimationdroite() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + (int) (time * 1) % 4))));

                return false;
            }
        };
        this.addAction(animation);
    }

    public void setAnimationdefaite() {
        this.removeAction(animation);
        animation = new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + 0 + ((((int)(time * 5) % 2) == 0) ? "" : "inv")))));

                return false;
            }
        };
        this.addAction(animation);
    }

    @Override
    public int getPortee() {
        return 0;
    }

    @Override

    public void setAnimationgauche() {
        this.removeAction(animation);
        animation=new Action() {
            float time = 0;

            @Override
            public boolean act(float delta) {
                time += delta;

                setDrawable(new TextureRegionDrawable(new TextureRegion(Bomberball.ennemis.findRegion("ghost" + 0 + "" + (int) (time * 1) % 4+"inv"))));

                return false;
            }
        };
        this.addAction(animation);
    }
}
