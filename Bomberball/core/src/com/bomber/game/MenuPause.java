package com.bomber.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe MenuPause
 * Elle affiche le menu pause dans le mode solo et multijoueur
 * @author Théo Loïs, Paul-Louis Renard
 *
 */
public class MenuPause extends Etat implements Screen {



    private Etat etatAnterieur;
    private Image back;
    private Skin skin;
    private Table table;
    private TextButton reprendreButton;
    private  TextButton recommencerButton;
    private  TextButton quitterButton;
    File f;
    File source;
    Bomberball game; // Note it's "MyGame" not "Game"


    /**
     * Constructeur de la classe MenuPause
     * @param game
     * @param jeu
     */
    public MenuPause(Bomberball game,Jeu jeu){
        super(jeu);
        this.game = game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveTempo/tmp.txt");
            source = new File(directory.getCanonicalPath() + "/SaveTempo/debut.txt");

        } catch (IOException e) {

        }
    }
    /**
     * Modificateur de l'état antérieur à l'appui sur le bouton pause
     * @param e
     */
    public void setEtatAnterieur(Etat e){
        etatAnterieur = e;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void hide() {
        Bomberball.stg.clear();
        // called when current screen changes from this to a different screen
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
        // never called automatically
    }
    @Override
    public boolean keyDown( int keycode) {
        return true;
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    public void show(){
        /*Creation de l'arriere-plan*/
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top); // Middle of the screen start at the top
        table.setPosition(0, Gdx.graphics.getHeight());

        reprendreButton = new TextButton("Reprendre la partie",skin);
        recommencerButton = new TextButton("Recommencer la partie",skin);
        quitterButton = new TextButton("Quitter la partie",skin);

        /*Creation du bouton de reprise de la partie*/
        reprendreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu pause
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.menuPause.removeActor(jeu);
                //On repasse dans l'etat du jeu tel qu'il etait avant le passage en pause
                jeu.setEtat(etatAnterieur);
                game.setScreen((Screen)etatAnterieur);
            }
        });

        /*Creation du bouton de reinitialisation de la partie*/
        recommencerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On reinitialise la partie en cours
                f.delete();
                source.renameTo(f);
                jeu.recommencer=true;
                //On quitte le menu pause
                jeu.removeActor(jeu.map);
                game.menuPause.removeActor(jeu);
                //On reprend la partie
                jeu.setEtat(etatAnterieur);
                game.setScreen((Screen)etatAnterieur);
            }
        });

        /*Creatio du bouton de retour au menu*/
        quitterButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On reinitialise toutes les donnees relatives a la partie en cours
                f.delete();
                source.delete();

                jeu.porteeBombe=-1;
                jeu.nbDeplaP=-1;
                jeu.nbBombe=-1;
                jeu.nbEnnemis=-1;
                //On quitte le menu pause
                jeu.removeActor(jeu.map);
                game.menuPause.removeActor(jeu);
                //On repasse au menu principal
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        /*Agencement du bouton sur l'ecran*/
        table.padTop(30); //Espace de 30 entre le premier texte et le haut

        table.add(reprendreButton).padBottom(30);
        table.row();

        table.add(recommencerButton).padBottom(30); //Espace de 30 entre les 2 boutons
        table.row();

        table.add(quitterButton); //Espace de 30 entre les 2 boutons
        table.row();

        back.setName("Arrière plan: menu principal");
        this.addActor(back);
        this.addActor(table);
    }
}