package com.bomber.game;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Ennemis extends Actor {
    protected Case c;
    protected boolean vivant;
    protected int pm;//points de mouvement, 5 par defaut

    public Ennemis(){}

    public Ennemis(boolean vivant, Case c, int pm) {
        this.vivant = vivant;
        this.c = c;
        this.pm = pm;
    }

}
