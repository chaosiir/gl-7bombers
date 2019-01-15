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
        this.chemin = chemin;
    }

    private LinkedList<Case> chemin;

    // indice de la case en cours sur le chemin défini
    private int i = 0;


    public void miseAjour() {
        for(Case cas : chemin){
            if(caseLibre(c.getMap().getGrille()[cas.posX()][cas.posY()])){
                prochain_deplacement.add(c.getMap().getGrille()[cas.posX()][cas.posY()]);
            }
            else {
                break;
            }
        }
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
