package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;

public class Solo extends Etat{//etat multijoueur
    private Jeu jeu;
    public Solo(Jeu jeu) {
        super(jeu);
        this.jeu=jeu;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = jeu.findActor("Personnage");
        if ((joueur != null)&&(!joueur.hasActions())) {
            if (keycode == Input.Keys.RIGHT) {
                joueur.deplacerDroite();
            }
            if (keycode == Input.Keys.LEFT) {
                joueur.deplacerGauche();
            }
            if (keycode == Input.Keys.DOWN) {
                joueur.deplacerBas();
            }
            if (keycode == Input.Keys.UP) {
                joueur.deplacerHaut();
            }
            if (keycode == Input.Keys.SPACE) {
                joueur.poserBombe();
            }
        }


        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {//test de fonction clic
      /*  Vector2 cord = jeu.getStage().screenToStageCoordinates(new Vector2(x,Gdx.graphics.getHeight()-y));//test vecteur mais marche pas
        Actor a=jeu.hit(cord.x,cord.y,true);//hit recupere l'acteur à la position x,y
            if(a!=null) {//si il y en a un
                a.setVisible(false);// on le rend invisible (pour le test)
            }*/
        return true;
    }
}
