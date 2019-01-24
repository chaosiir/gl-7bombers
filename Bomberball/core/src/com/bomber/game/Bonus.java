package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

public class Bonus extends Image  {
    com.bomber.game.Case c;

    /**
     * Constructeur de la classe Bonus
     * @return un bonus sur la case C
     */
    public Bonus(com.bomber.game.Case C){
        c=C;
    }

    /**
     *
     * @param C
     * @param t
     * @return un bonus sur la case C avec la texture t
     */
    protected Bonus(com.bomber.game.Case C, Texture t){
        super(t);
        this.setName("bonus");
        this.setBounds(com.bomber.game.Bomberball.taillecase/4,Bomberball.taillecase/4,Bomberball.taillecase,Bomberball.taillecase);
        this.setScale(0.5f);
        c=C;
    }

    public com.bomber.game.Case getC() {
        return c;
    }

    public void action(){
    }

    public String identifier(){
        return null;
    }



}
