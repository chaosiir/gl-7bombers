package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;

public class Defaite extends Etat implements Screen {

    Bomberball game;
    Image back;
    Label explication;
    Skin skin;
    TextButton ok;
    TextButton rejouer;
    String txt;

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
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        int xmax = Toolkit.getDefaultToolkit().getScreenSize().width;
        int ymax = Toolkit.getDefaultToolkit().getScreenSize().height;

        //System.out.println("xmax="+xmax+" ymax="+ymax);

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
                jeu.map = null;
                jeu.removeActor(jeu.findActor("Map"));
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        rejouer=new TextButton("rejouer",skin);
        rejouer.setBounds(ok.getX()+ok.getWidth()+50,ok.getY(),rejouer.getWidth(),rejouer.getHeight());
        rejouer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.map = null;
                jeu.removeActor(jeu.findActor("Map"));
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });

        jeu.addActor(back);
        jeu.addActor(explication);
        jeu.addActor(ok);
        jeu.addActor(rejouer);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}



