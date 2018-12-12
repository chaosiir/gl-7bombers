package com.bomber.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Jeu extends Group {
    Map map;
    Etat etat;

    public Jeu (){
        this.etat=new Multijoueur(this);
        this.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode==131){
                    Gdx.graphics.setWindowedMode(1200,600);
                }
                return etat.keyDown( event, keycode);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return etat.touchDown( event,  x,  y,  pointer,  button);
            }
        });

        map=Map.generatePvp(60);
        this.addActor(map);
        int i;
        int j;
        for (i=0;i<map.tailleX();i++){
            for(j=0;j<map.tailleY();j++){
                map.addActor(map.getGrille()[i][j]);
            }
        }
    }



}
