package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Multijoueur extends Etat{

    public Multijoueur(Jeu jeu) {
        super(jeu);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        System.out.println(""+x+" "+y);
        return true;
    }
}
