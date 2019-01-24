package com.bomber.game;

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
/**
 * Classe MenuSolo
 * Elle affiche le menu spécifique au mode solo
 * @author Paul-Louis Renard
 *
 */
public class MenuSolo extends Etat implements Screen {
    private Bomberball game;
    private Skin skin;
    private Image back;
    private Table table;
    private TextButton demarrerpartie;
    private TextButton campagne;
    private  TextButton choixmap;
    private  TextButton parametre;
    private  TextButton retour;



    /**
     * Constructeur de la classe MenuSolo
     * @param game
     * @param jeu
     */
    public MenuSolo(Bomberball game,Jeu jeu){
        super(jeu);
        this.game = game;

    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        /*Creation de l'arriere-plan*/
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        // called when this screen is set as the screen with game.setScreen();
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top); // Middle of the screen start at the top
        table.setPosition(0, Gdx.graphics.getHeight());

        demarrerpartie = new TextButton("Demarrer une partie",skin);
        campagne = new TextButton("campagne",skin);
        choixmap = new TextButton("Choix d'une map personnalisee",skin);
        parametre = new TextButton("Parametre",skin);
        retour= new TextButton("Retour",skin);

        /*Creation du bouton de demarrage d'une partie solo*/
        demarrerpartie.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu solo
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                //On passe au mode solo
                jeu.setEtat(game.jeuSolo);
                game.setScreen(game.jeuSolo);
            }
        });

        /*Creation du bouton du mode campagne*/
        campagne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu solo
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                //On passe au menu de selection de maps de la campagne
                jeu.setEtat(game.choixCampagne);
                game.setScreen(game.choixCampagne);
            }
        });

        /*Creation du bouton permettant d'acceder au menu de selection des maps editees*/
        choixmap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu solo
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                //On passe au menu de selection de maps editees
                jeu.setEtat(game.choixMapSoloJ);
                game.setScreen(game.choixMapSoloJ);
            }
        });

        /*Creation du bouton pearmettant d'acceder au menu des parametres*/
        parametre.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu solo
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                //On passe au menu des parametres
                jeu.setEtat(game.parametreSolo);
                game.setScreen(game.parametreSolo);
            }
        });

        /*Creation du bouton de retour au menu principal*/
        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu solo
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.map=null;
                //On passe au menu principal
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        table.padTop(30);

        table.add(demarrerpartie);
        table.add(campagne);
        table.add(choixmap);
        table.add(parametre);
        table.add(retour);

        back.setName("Arrière plan: menu solo principal");

        this.addActor(back);
        this.addActor(table);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
        jeu.removeActor(jeu.map);
        Bomberball.input.removeProcessor(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
