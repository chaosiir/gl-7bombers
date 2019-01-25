package com.bomber.game.Ecrans;

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
import com.bomber.game.Jeu;

import java.awt.*;
import java.io.File;

/**
 * Classe Victoire
 * Elle affiche un message avec un bouton ok puis retourne sur le menu principal.
 *
 */
public class Victoire extends Etat implements Screen {
    Bomberball game;
    Image back;
    Label explication;
    Skin skin;
    TextButton ok;
    String txt;
    File frecommencer;

    /**
     * Constructeur de la classe Solo
     * @param game : L'application Bomberball globale. Excellent jeu ;)
     * @param jeu : Map et informations complémentaire
     * @param st : Chaîne de caractère à écrire au milieu de l'écran.
     */
    public Victoire(Bomberball game, Jeu jeu, String st){
        super(jeu);
        this.game=game;
        txt=st;
        frecommencer = Gdx.files.internal("./SaveTempo/debut.txt").file();
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        int xmax= Toolkit.getDefaultToolkit().getScreenSize().width;
        int ymax=Toolkit.getDefaultToolkit().getScreenSize().height;


        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");


        explication=new Label(txt,skin);
        explication.setBounds(xmax/2-300,ymax/2,explication.getWidth(),explication.getHeight()); //Positionnement à la main


        ok= new TextButton("ok",skin);
        ok.setBounds(xmax/2-50,ymax/2-Bomberball.taillecase,ok.getWidth(),ok.getHeight()); //Positionnement à la main

        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.victoire.removeActor(jeu);
                frecommencer.delete();
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        this.addActor(back);
        this.addActor(explication);
        this.addActor(ok);
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
     * Fonction appellé lors d'un changement d'écran.
     */
    @Override
    public void hide() {
        Bomberball.stg.clear();
        jeu.removeActor(jeu.map);
        Bomberball.input.removeProcessor(this);

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void dispose() {

    }

    /**
     * Permet de détecter un appui sur une touche
     * @param keycode : vaut le numéro de la touche enfoncée
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyDown( int keycode) {
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
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
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
}
