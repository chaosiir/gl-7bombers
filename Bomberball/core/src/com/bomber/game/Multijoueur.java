package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Multijoueur extends Etat{
    private Jeu jeu;
    public Multijoueur(Jeu jeu) {
        super(jeu);
        this.jeu=jeu;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {

        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        Vector2 cord = jeu.getStage().screenToStageCoordinates(new Vector2(x,Gdx.graphics.getHeight()-y));
        Actor a=jeu.hit(x,y,true);
            if(a!=null) {
                a.setVisible(false);
            }
        return true;
    }
}
