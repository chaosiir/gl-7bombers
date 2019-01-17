package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

public class Porte extends Image {

    public Porte(){
        super(Bomberball.multiTexture[3]);
        setName("Porte");
    }
    public void afficher(Batch b,int x,int y,Texture [] multt){
        Sprite s;
        s=new Sprite(multt[3]);
        s.setPosition(x*50+600,y*50+100);
        s.setSize(50,50);
        s.draw(b);
    }
}
