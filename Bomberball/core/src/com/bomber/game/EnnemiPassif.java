package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.LinkedList;

public class EnnemiPassif extends Ennemis {


    public EnnemiPassif(boolean vivant,Case c,int pm,LinkedList<Case> chemin) {
        super(Bomberball.multiTexture[17],vivant,c,pm);
        this.chemin=chemin;
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
        prochain_deplacement = new LinkedList<Case>();
        int n = chemin.size();
        Case suivante = chemin.get(0);
        while (i<n && caseLibre(suivante) ){
            prochain_deplacement.add(suivante);
            i = i+1;
            suivante= chemin.get(i);
        }
    }

}
