package com.bomber.game.Bonus;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bomber.game.Bomberball;
import com.bomber.game.MapetObjet.Case;

/**
 * Classe abstraite Bonus
 *
 *classe abstraite pour acceder à tous les types de bonus de la meme façon
 *
 */
public abstract class Bonus extends Image  {
    Case c;//case sur laquelle se trouve le bonus
    /**
     *instancie une classe bonus sur une case
     * @param Case C
     * @param Texture t
     * @return un bonus sur la case C avec la texture t
     */
    protected Bonus(Case C,Texture t){
        super(t);
        this.setName("bonus");//tous les bonus s'appellent "bonus"
        this.setBounds(Bomberball.taillecase/4,Bomberball.taillecase/4,Bomberball.taillecase,Bomberball.taillecase);//tous les bonus ont la meme taille et position
        this.setScale(0.5f);//le bonus fait la moitié de la taille d'une case
        c=C;
    }

    /**
     * sert à effectuer l'action du bonus
     */
    public abstract void action();




}
