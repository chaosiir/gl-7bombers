package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;

public class Solo extends Etat{//etat multijoueur
    private Jeu jeu;
    int pm=5;
    int nb=1;
    public Solo(Jeu jeu) {
        super(jeu);
        this.jeu=jeu;

    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = jeu.findActor("Personnage");
        if(jeu.findActor("explo")==null) {
            if ((joueur != null) && (!joueur.hasActions())) {
                boolean b = false;
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    if (joueur.isVivant()) {
                        pm = joueur.getPm();
                        nb = joueur.getNbBombe();
                    } else {
                        joueur.getC().removeActor(joueur);
                    }

                }
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

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}
