package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
/**
 * Classe ChoixMenuMultijoueur
 * Elle affiche les paramètres que le joueur peut choisir pour lancer une partie multijoueur et le moyen de lancer cette partie
 * @author Paul-Louis Renard
 *
 */
public class ChoixMenuMultijoueur extends Etat implements Screen {
    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }

    Bomberball game;
    private Skin skin;
    private Image back;
    private Table table;
    private Label nbjoueur;
    private Label nbBonus;

    private Label porteeBombe;

    private TextButton retour;
    private TextButton deux;
    private TextButton trois;
    private TextButton quatre;
    private TextButton choixmap;
    private TextButton lancerP;

    private Slider nbBonusS;
    private Slider porteeBombeS;

    public ChoixMenuMultijoueur(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        nbjoueur=new Label("Nombre de joueurs :",skin);
        nbBonus=new Label("Nombre de bonus :",skin);

        porteeBombe= new Label("Portee d'une bombe :",skin);


        retour= new TextButton("Retour",skin);
        deux= new TextButton("2",skin);
        trois = new TextButton("3",skin);
        quatre= new TextButton("4",skin);
        choixmap = new TextButton("Choix de la map",skin);
        lancerP = new TextButton("Lancer la partie",skin);


        nbBonusS= new Slider(0f,20f,1f,false,skin);
        porteeBombeS=  new Slider(0f,20f,1f,false,skin);

        lancerP.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(back);
                jeu.removeActor(table);
                jeu.setEtat(game.multijoueur);
                game.setScreen(game.multijoueur);
            }
        });


        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.map=null;
                jeu.removeActor(jeu.findActor("Map"));
                jeu.removeActor(back);
                jeu.removeActor(table);
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        choixmap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(back);
                jeu.removeActor(table);
                jeu.setEtat(game.choixMapMultiJ);
                game.setScreen(game.choixMapMultiJ);
            }
        });

        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(nbjoueur).padBottom(30);
        table.add(deux);
        table.add(trois);
        table.add(quatre);
        table.row();
        table.add(nbBonus).padBottom(30);
        table.add(nbBonusS);
        table.row();
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeS);
        table.row();
        table.add(choixmap).padBottom(30);
        table.row();
        table.add(lancerP).padBottom(30);
        table.row();
        table.add(retour).padBottom(30);

        back.setName("Arrière plan: menu multijoueur principal");

        jeu.addActor(back);
        jeu.addActor(table);


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
}
