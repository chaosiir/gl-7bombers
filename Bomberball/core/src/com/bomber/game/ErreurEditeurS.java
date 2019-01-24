package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;


import java.awt.*;
/**
 * Classe ErreurEditeurS
 * Elle affiche un message d'erreur si le joueur a réalisé une map solo invalide
 * @author Paul-Louis Renard
 *
 */
public class ErreurEditeurS extends Etat implements Screen {
    Bomberball game;
    Image back;
    Label explication;
    Skin skin;
    TextButton ok;
    /**
     * Constructeur de la classe ErreurEditeurS
     * @param game
     * @param jeu
     */
    public  ErreurEditeurS(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        /*Creation de l'arriere-plan*/
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        int xmax=Toolkit.getDefaultToolkit().getScreenSize().width;
        int ymax=Toolkit.getDefaultToolkit().getScreenSize().height;

        //System.out.println("xmax="+xmax+" ymax="+ymax);

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        /*Affichage du message d'erreur*/
        explication=new Label("Il doit y avoir un personnage et une porte",skin);
        explication.setBounds(xmax/2-300,ymax/2,explication.getWidth(),explication.getHeight()); //Positionnement à la main
        explication.setWrap(true);


        ok= new TextButton("ok",skin);
        ok.setBounds(xmax/2-50,ymax/2-Bomberball.taillecase,ok.getWidth(),ok.getHeight()); //Positionnement à la main

        /*Creation du bouton de retour a l'editeur*/
        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le mode erreur
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.erreurEditeurS.removeActor(jeu);
                //On retourne au mode editeur
                jeu.setEtat(game.editeurNSolo);
                game.setScreen(game.editeurNSolo);
            }
        });

        this.addActor(back);
        this.addActor(explication);
        this.addActor(ok);

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
    public boolean touchDown( int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved( int x, int y) {
        return false;
    }
}
