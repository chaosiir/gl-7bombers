package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
/**
 * Classe ParametreSolo
 * Elle affiche les paramètres modifiables par le joueur lorsqu'il joue en mode solo
 * @author Paul-Louis Renard
 *
 */
public class ParametreSolo extends Etat implements Screen {
    Bomberball game;
    private Skin skin;
    private Image back;
    private Table table;
    private Label difficulte;
    private Label nbBonus;
    private Label nbEnnemis;
    private Label nbBombe;
    private Label porteeBombe;
    private Label nbDeplaEnnemis;

    private TextButton retour;
    private TextButton facile;
    private TextButton moyen;
    private TextButton difficile;

    private TextField nbBonusT;
    private TextField nbEnnemisT;
    private TextField porteeBombeT;
    private TextField nbDeplaEnnemisT;
    private TextField nbBombeT;




    public ParametreSolo(Bomberball game,Jeu jeu){
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
        // called when this screen is set as the screen with game.setScreen();
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        difficulte=new Label("Difficulte :",skin);
        nbBonus=new Label("Nombre de bonus :",skin);
        nbEnnemis= new Label("Nombre d'ennemis :",skin);
        porteeBombe= new Label("Portee d'une bombe :",skin);
        nbDeplaEnnemis = new Label("Nombre de deplacement des ennemis :",skin);
        nbBombe= new Label("Nombre de Bombes :", skin);

        retour= new TextButton("Retour",skin);
        facile= new TextButton("Facile",skin);
        moyen = new TextButton("Moyen",skin);
        difficile= new TextButton("Difficile",skin);

        nbBonusT= new TextField("5",skin);
        nbEnnemisT= new TextField("2",skin);
        porteeBombeT = new TextField("2",skin);
        nbDeplaEnnemisT = new TextField("3",skin);
        nbBombeT= new TextField("1",skin);



        /*nbBonusT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int x=(int) nbBonusT.getText();
                jeu.nbBonus=x;


            }
        });
        nbEnnemisS.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int x=(int) nbEnnemisS.getValue();
                jeu.nbEnnemis=x;


            }
        });
        porteeBombeS.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int x=(int) porteeBombeS.getValue();
                jeu.porteeBombe=x;


            }
        });
        nbDeplaEnnemisS.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int x=(int) nbDeplaEnnemisS.getValue();
                jeu.nbEnnemis=x;


            }
        });

        nbBombeS.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int x=(int) nbBombeS.getValue();
                jeu.nbBombe=x;
            }
        });*/






        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(difficulte).padBottom(30);
        table.add(facile);
        table.add(moyen);
        table.add(difficile);
        table.row();
        table.add(nbBonus).padBottom(30);
        table.add(nbBonusT);
        table.row();
        table.add(nbEnnemis).padBottom(30);
        table.add(nbEnnemisT);
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeT);
        table.row();
        table.add(nbDeplaEnnemis).padBottom(30);
        table.add(nbDeplaEnnemisT);
        table.row();
        table.add(retour).padBottom(30);

        back.setName("Arrière plan: parametre solo");

        this.addActor(back);
        this.addActor(table);

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {


                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }
}
