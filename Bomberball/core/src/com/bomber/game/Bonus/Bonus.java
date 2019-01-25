package com.bomber.game.Bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/**
 * Classe bonus générale
 */
public abstract class Bonus extends Image  {
    Case c;
    /**
     * Constructeur de la classe abstraite bonus
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

    public abstract void action();




}
