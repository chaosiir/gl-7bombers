package com.bomber.game.Ecrans.Editeur;

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
/**
 * Classe ErreurEditeurM
 * Elle affiche un message d'erreur si le joueur a réalisé une map multijoueur invalide
 * @author Paul-Louis Renard
 *
 */
public class ErreurEditeurM extends Etat implements Screen {
    Bomberball game;    //Instance de la classe principale
    Image back;         //Image de l'arrière-plan
    Label explication;  //Explication de l'erreur
    Skin skin;          //Caractéristiques des éléments graphiques
    TextButton ok;      //Boutton ok
    /**
     * Générateur de la classe ErreurEditeurM
     * @param game
     * @param jeu
     */
    public ErreurEditeurM(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
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

        explication=new Label("Il doit y avoir 4 joueurs differents",skin);
        explication.setBounds(xmax/2-300,ymax/2,explication.getWidth(),explication.getHeight()); //Positionnement à la main
        explication.setWrap(true);


        ok= new TextButton("ok",skin);
        ok.setBounds(xmax/2-50,ymax/2-Bomberball.taillecase,ok.getWidth(),ok.getHeight()); //Positionnement à la main

        ok.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.erreurEditeurM.removeActor(jeu);
                jeu.setEtat(game.editeurNMulti);
                game.setScreen(game.editeurNMulti);
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
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }
}
