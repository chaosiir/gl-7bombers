package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

public class Bonus extends Image  {
    Case c;

    public Bonus(Case C){
            c=C;
    }
    protected Bonus(Case C,Texture t){
        super(t);
        this.setName("bonus");
        this.setBounds(Bomberball.taillecase/4,Bomberball.taillecase/4,Bomberball.taillecase,Bomberball.taillecase);
        this.setScale(0.5f);
        c=C;
    }

    public void action(){
    }

    public String identifier(){
        return null;
    }



}
