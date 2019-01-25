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

/**
 * Classe qui s'affiche lors d'une défaite
 */
public class Defaite extends Etat implements Screen {

    Bomberball game;                //Instance de la classe principale
    Image back;                     //Image de l'arrière-plan
    Label explication;              //Affiche "Défaite"
    Skin skin;                      //Caractéristiques des éléments graphiques
    TextButton ok;                  //Bouton ok
    TextButton rejouer;             //Bouton pour rejouer
    String txt;                     //Paramètrage de la défaite
    File frecommencer;              //Fichier où se trouve le fichier pour recommencer
    File f;                         //Fichier de sauvegarde temporaire
    Etat precedent;                 //Permet d'utiliser Défaite pour différents mode de jeu

    /**
     * Générateur de la classe Defaite
     * @param game
     * @param jeu
     * @param st
     */
    public Defaite(Bomberball game, Jeu jeu, String st) {
        super(jeu);
        this.game = game;
        txt = st;
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
                frecommencer.renameTo(f);               //Renomme debut.txt en tmp.txt pour pouvoir charger cette map et recommencer
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
     * Met à jour l'affichage
     * @param delta: Interval de temps entre deux affichages
     */
    @Override
    public void render(float delta) {

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

    /**Fonction qui gère l'appui
     * @param keycode
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param x : récupère la position x du pointeur.
     * @param y : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }
}



