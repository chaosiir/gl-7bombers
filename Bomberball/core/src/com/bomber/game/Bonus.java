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
        c=C;
    }

    public void action(Personnage p){//  !!!!!!!!! à enlever et on nomme toute les actions avec le meme nom pour les utilisés directement
        if (this instanceof BonusMove){//=> passage de la classe en mode abstait
            ((BonusMove) this).actionM();
        } else if (this instanceof BonusExplo) {
            ((BonusExplo) this).actionE();
        }else if (this instanceof BonusBombe) {
            ((BonusBombe) this).actionB();
        }
    }

    public String identifier(){
        return null;
    }



}
