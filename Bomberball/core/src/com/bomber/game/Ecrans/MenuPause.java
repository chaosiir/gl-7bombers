package com.bomber.game.Ecrans;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bomber.game.Bomberball;
import com.bomber.game.Jeu;

import java.io.File;
/**
 * Classe MenuPause
 * Elle affiche le menu pause dans le mode solo et multijoueur
 * @author Théo Loïs, Paul-Louis Renard
 *
 */
public class MenuPause extends Etat implements Screen {



    private Etat etatAnterieur;             //Etat précédent pour reprendre la partie dans un bon mode de jeu
    private Image back;                     //Image de l'arrière-plan
    private Skin skin;                      //Caractéristiques des éléments graphiques
    private Table table;                    //Permet d'organiser l'écran
    private TextButton reprendreButton;     //Permet de reprendre
    private  TextButton recommencerButton;  //Permet de recommencer
    private  TextButton quitterButton;      //Permet de quitter
    File f;                                 //Sauvegarde temporaire pour mettre le menu pause
    File source;                            //Sauvegarde pour pouvoir recommencer
    Bomberball game;                        //Instance de la classe principale


    /**
     * Constructeur de la classe MenuPause
     * @param game
     * @param jeu
     */
    public MenuPause(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game = game;
        f = Gdx.files.internal("./SaveTempo/tmp.txt").file();
        source = Gdx.files.internal("./SaveTempo/debut.txt").file();

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

        reprendreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.menuPause.removeActor(jeu);
                jeu.setEtat(etatAnterieur);
                game.setScreen((Screen)etatAnterieur);
            }
        });

        recommencerButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                f.delete();
                source.renameTo(f);                                                 //Pour recommencer, on renomme debut en tmp pour qu'il soit chargé
                jeu.recommencer=true;
                jeu.removeActor(jeu.map);
                game.menuPause.removeActor(jeu);
                jeu.setEtat(etatAnterieur);
                game.setScreen((Screen)etatAnterieur);
            }
        });

        quitterButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                f.delete();
                source.delete();

                jeu.porteeBombe=-1;
                jeu.nbDeplaP=-1;
                jeu.nbBombe=-1;
                jeu.nbEnnemis=-1;

                jeu.removeActor(jeu.map);
                game.menuPause.removeActor(jeu);
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        table.padTop(30);                                       //Espace de 30 entre le premier texte et le haut

        table.add(reprendreButton).padBottom(30);
        table.row();

        table.add(recommencerButton).padBottom(30);             //Espace de 30 entre les 2 boutons
        table.row();

        table.add(quitterButton);                               //Espace de 30 entre les 2 boutons
        table.row();

        back.setName("Arrière plan: menu principal");
        this.addActor(back);
        this.addActor(table);
    }
}