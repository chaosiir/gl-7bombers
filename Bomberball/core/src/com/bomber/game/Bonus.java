package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bonus extends Image {
    Case c;

    public Bonus(Case C){
            c=C;
    }
    protected Bonus(Case C,Texture t){
        super(t);
        this.setName("bonus");
        this.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        c=C;
    }

    public void action(){
    }

    public String identifier(){
        return null;
    }



}
