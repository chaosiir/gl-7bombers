package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe ValiderEditeurMulti
 * Elle permet de sauvegarder sa carte multijoueur qu'il a créé
 * @author Paul-Louis Renard
 *
 */
public class ValiderEditeurMulti extends Etat implements Screen {
    Bomberball game;
    Image back;
    Label explication;
    Skin skin;
    TextButton valider;
    TextButton retour;
    TextButton abandonner;
    TextField inputui;
    Table table;
    File f;
    FileWriter fw;

    /**
     * Constructeur de la classe ValiderEditeurMulti
     * @param game
     * @param jeu
     */
    public ValiderEditeurMulti(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveMapPerso/MapMulti/tmp.txt");

        } catch (IOException e) {

        }
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        int xmax= Toolkit.getDefaultToolkit().getScreenSize().width;
        int ymax=Toolkit.getDefaultToolkit().getScreenSize().height;

        //System.out.println("xmax="+xmax+" ymax="+ymax);

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");


        explication=new Label("Donner un nom a votre map multijoueur:",skin);


        valider= new TextButton("ok",skin);
        retour=new TextButton("retour",skin);
        abandonner=new TextButton("abandonner",skin);

        inputui=new TextField("Nom",skin);

        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center); // Middle of the screen start at the top
        table.setPosition(0, Gdx.graphics.getHeight()/2);

        table.add(explication).padBottom(30);
        table.row();
        table.add(inputui).padBottom(30);
        table.row();
        table.add(valider);
        table.add(retour);
        table.add(abandonner);


        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String nom=inputui.getText();
                if (nom.equals("")){
                    //rien
                }
                else{
                    File directory = new File (".");
                    File fi= null;
                    try {
                        fi = new File(directory.getCanonicalPath() + "/SaveMapPerso/MapMulti/"+nom+".txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    f.renameTo(fi);
                    jeu.setEtat(game.menuPrincipalBis);
                    game.setScreen(game.menuPrincipalBis);
                }

            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.setEtat(game.editeurNMulti);
                game.setScreen(game.editeurNMulti);
            }
        });

        abandonner.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                f.delete();
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        jeu.addActor(back);
        jeu.addActor(table);

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
