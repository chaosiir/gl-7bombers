package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.LinkedList;

public class EnnemiPassif extends Ennemis {
    int pos;
    Boolean retour=false;


    public EnnemiPassif(boolean vivant,Case c,int pm) {
        super(Bomberball.multiTexture[17],vivant,c,pm);
        this.chemin=new LinkedList<Case>();
        setAnimationdroite();
        pos=0;
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
        prochain_deplacement.add(c);
        Map map=this.getC().getMap();
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
                //System.out.println(this.getC().posX()+" "+this.getC().posX()+" ENNEMIS RETOUR");
                        Case aCase=chemin.get(pos-1);
                       // System.out.println("Case "+aCase.posX()+" "+aCase.posY());
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
               // System.out.println(this.getC().posX()+" "+this.getC().posX()+" ENNEMIS NON RETOUR");
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
        System.out.println("NOUVEAU DEPLACEMENT");
        for(Case ca:prochain_deplacement){
            System.out.println(""+ca.posX()+" "+ca.posY());
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
