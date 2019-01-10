package com.bomber.game;

import java.util.LinkedList;

public class EnnemiPassif extends Ennemis {


    public LinkedList<Case> getChemin() {
        return chemin;
    }

    public void setChemin(LinkedList<Case> chemin) {
        this.chemin = chemin;
    }

    private LinkedList<Case> chemin;


    public void miseAjour() {
        prochain_deplacement = new LinkedList<Case>();
        int n = chemin.size();
        int i = 0;
        Case suivante = chemin.get(0);
        while (i<n && caseLibre(suivante) ){
            prochain_deplacement.add(suivante);
            i = i+1;
            suivante= chemin.get(i);
        }
    }

}
