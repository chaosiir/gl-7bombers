package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class Etat {
    private Jeu jeu;
    public Etat(Jeu jeu){
        this.jeu=jeu;
    }
    public abstract boolean keyDown(InputEvent event, int keycode);
    public abstract boolean touchDown(InputEvent event, float x, float y, int pointer, int button);
    }
