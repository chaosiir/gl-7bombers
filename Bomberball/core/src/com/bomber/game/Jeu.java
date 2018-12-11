package com.bomber.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;


public class Jeu extends Group {
    Map map;
    Texture[] multiTexture;
    //Etat etat;

    public Jeu (Texture[] multiTexture){
        this.multiTexture=multiTexture;
    }

}
