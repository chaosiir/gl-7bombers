package com.bomber.game.Ecrans.Solo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;

import java.awt.*;
import java.io.File;

public class Defaite extends Etat implements Screen {

    Bomberball game;
    Image back;
    Label explication;
    Skin skin;
    TextButton ok;
    TextButton rejouer;
    File frecommencer;
    File f;
    Etat precedent;

    /**
     * Générateur de la classe Defaite
     * @param game
     * @param jeu
     */
    public Defaite(Bomberball game, Jeu jeu) {
        super(jeu);
        this.game = game;
        f = Gdx.files.internal("./SaveTempo/tmp.txt").file();
        frecommencer = Gdx.files.internal("./SaveTempo/debut.txt").file();

    }

    public void setEtat(Etat e){
        this.precedent=e;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int xmax = Toolkit.getDefaultToolkit().getScreenSize().width;
        int ymax = Toolkit.getDefaultToolkit().getScreenSize().height;


        back = new Image(new Texture(Gdx.files.internal("backmain.png")));
        back.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");


        explication = new Label("Defaite", skin);
        explication.setBounds(xmax / 2 , ymax / 2, explication.getWidth(), explication.getHeight()); //Positionnement à la main


        ok = new TextButton("retour", skin);
        ok.setBounds(xmax / 2 - 50, ymax / 2 - Bomberball.taillecase, ok.getWidth(), ok.getHeight()); //Positionnement à la main

        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                frecommencer.delete();
                f.delete();


                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.defaite.removeActor(jeu);

                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        rejouer=new TextButton("rejouer",skin);
        rejouer.setBounds(ok.getX()+ok.getWidth()+50,ok.getY(),rejouer.getWidth(),rejouer.getHeight());
        rejouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                f.delete();
                frecommencer.renameTo(f);
                jeu.recommencer=true;
                jeu.removeActor(jeu.map);
                game.defaite.removeActor(jeu);
                jeu.setEtat(precedent);
                game.setScreen((Screen)precedent);

            }
        });

        this.addActor(back);
        this.addActor(explication);
        this.addActor(ok);
        this.addActor(rejouer);
    }



    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX : récupère la position x du pointeur.
     * @param screenY : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    /**
     * Met à jour l'affichage
     * @param delta: Interval de temps entre deux affichages
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
    }

    /**
     * Gère le changement de taille de la fenêtre d'affichage
     *
     * @param width : largeur nouvelle fenêtre
     * @param height : hauteur nouvelle fenêtre
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appellée lors d'un changement d'écran.
     */
    @Override
    public void hide() {
        Bomberball.stg.clear();

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void dispose() {

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}




