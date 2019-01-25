package com.bomber.game.Ecrans.Solo;

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
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;

/**
 * Classe MenuSolo
 * Elle affiche le menu spécifique au mode solo
 *
 */
public class MenuSolo extends Etat implements Screen {
    private Bomberball game;                    //Instance de la classe principale
    private Skin skin;                          //Caractéristiques des éléments graphiques
    private Image back;                         //Image de l'arrière-plan
    private Table table;                        //Permet d'organiser l'écran
    private TextButton demarrerpartie;          //Bouton pour démarrer une partie solo aléatoire ou personnalisé
    private TextButton campagne;                //Bouton pour accèder au mode campagne
    private  TextButton choixmap;               //Bouton pour choisir la map
    private  TextButton parametre;              //Bouton pour arriver dans les paramètres
    private  TextButton retour;                 //Bouton pour retourner sur le menu principal



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
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());



        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        demarrerpartie = new TextButton("Demarrer une partie",skin);
        campagne = new TextButton("campagne",skin);
        choixmap = new TextButton("Choix d'une map personnalisee",skin);
        parametre = new TextButton("Parametre",skin);
        retour= new TextButton("Retour",skin);

        demarrerpartie.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.setEtat(game.jeuSolo);                                      //Permet de transmettre à l'état de transmettre les commandes
                game.setScreen(game.jeuSolo);                                   //Permet de changer d'écran
            }
        });
        campagne.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.setEtat(game.choixCampagne);
                game.setScreen(game.choixCampagne);
            }
        });
        choixmap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.setEtat(game.choixMapSoloJ);
                game.setScreen(game.choixMapSoloJ);
            }
        });

        parametre.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.setEtat(game.parametreSolo);
                game.setScreen(game.parametreSolo);
            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Bomberball.stg.clear();
                jeu.removeActor(jeu.map);
                Bomberball.input.removeProcessor(game.menuSolo);
                jeu.map=null;
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

    /**
     * Met à jour l'affichage
     * @param delta: Interval de temps entre deux affichages
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
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
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }

    /**
     * Indique l'action à effectuer lorsqu'on clique avec la souris en fonction de l'élément sur lequel on a cliqué
     * @param screenX abscisse du pointeur sur l'écran
     * @param screenY ordonnée du pointeur sur l'écran
     * @param pointer pointeur de l'événement (jamais utilisée)
     * @param button bouton de la souris appuyé
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX: récupère la position x du pointeur.
     * @param screenY : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
