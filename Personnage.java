package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Personnage {

    public void afficher(Batch b, int x, int y,Texture [] multt){
        Sprite s;
        s=new Sprite(multt[4]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }
}