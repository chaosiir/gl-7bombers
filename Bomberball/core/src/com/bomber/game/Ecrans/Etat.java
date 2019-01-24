package com.bomber.game.Ecrans;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bomber.game.Jeu;

public abstract class Etat extends Group implements InputProcessor{//classe etat
    protected Jeu jeu;
    public Etat(Jeu jeu){
        this.jeu=jeu;// on prend le jeu pour un changement d'etat eventuel (à voir si besoin)
    }


    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
