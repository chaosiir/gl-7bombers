package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class Etat {//classe etat
    protected Jeu jeu;
    public Etat(Jeu jeu){
        this.jeu=jeu;// on prend le jeu pour un changement d'etat eventuel (à voir si besoin)
    }



    public abstract boolean keyDown(InputEvent event, int keycode);//traitement des inputs
    public abstract boolean touchDown(InputEvent event, float x, float y, int pointer, int button);
    public abstract boolean mouseMoved(InputEvent event, float x, float y);



}
