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
        Bomberball.stg.clear();

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }
}



