package com.bomber.game.Ecrans;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bomber.game.Jeu;

/**
 * Sert de classe mere pour les différents écrans et permet de recevoir les différents imputs.
 */
public abstract class Etat extends Group implements InputProcessor{
    protected Jeu jeu;
    public Etat(Jeu jeu){
        this.jeu=jeu;
    }

    /**
     * Permet de détecter un relâchement sur une touche
     * @param keycode : vaut le numéro de la touche enfoncée
     * @return false: on ne traite pas ce relâchement
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    /**
     * Fonction détectant un appui d'un des touche de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param character : récupère certaines touches
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Fonction détectant un appui d'un des touche de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX : récupère la position x du pointeur.
     * @param screenY : récupère la position y du pointeur.
     * @param pointer : récupère le pointeur sur événement.
     * @param button : donne le bouton appuyé.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    /**
     * Fonction détectant un relâchement d'une des touches de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX : récupère la position x du pointeur.
     * @param screenY : récupère la position y du pointeur.
     * @param pointer : récupère le pointeur sur événement.
     * @return false: on ne traite pas ce relâchement
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    /**
     * Fonction détectant une rotation de la molette de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param amount : montant du déplacement
     * @return false: on ne traite pas le scroll
     */

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
