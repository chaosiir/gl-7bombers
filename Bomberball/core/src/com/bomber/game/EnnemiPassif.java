package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;

import java.io.Serializable;
import java.util.LinkedList;

public class EnnemiPassif extends Ennemis {
    @Override
    public LinkedList<Case> getProchain_deplacement() {
        return super.getProchain_deplacement();
    }

    @Override
    public int getPortee() {
        return 0;
    }

    @Override
    public Case getC() {
        return super.getC();
    }

    @Override
    public boolean isVivant() {
        return super.isVivant();
    }

    @Override
    public int getPm() {
        return super.getPm();
    }

    @Override
    public boolean isAgro() {
        return false;
    }

    public EnnemiPassif(boolean vivant, Case c, int pm) {
        super(Bomberball.multiTexture[17],vivant,c,pm);
        this.chemin=new LinkedList<Case>();
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

}
