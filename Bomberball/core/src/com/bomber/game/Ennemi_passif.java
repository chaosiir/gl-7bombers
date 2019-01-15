package com.bomber.game;

import java.util.LinkedList;

public class Ennemi_passif extends Ennemis{

    public Ennemi_passif(boolean vivant,Case c,int pm){
        super(Bomberball.multiTexture[17]);
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.vivant=vivant;
        this.c=c;
        this.pm=pm;

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
