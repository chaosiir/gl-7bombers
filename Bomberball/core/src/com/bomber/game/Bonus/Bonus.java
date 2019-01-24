package com.bomber.game.Bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

public class Bonus extends Image  {
    Case c;

    /**
     * Constructeur de la classe Bonus
     * @return un bonus sur la case C
     */
    public Bonus(Case C){
        c=C;
    }

    /**
     *
     * @param C
     * @param t
     * @return un bonus sur la case C avec la texture t
     */
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
