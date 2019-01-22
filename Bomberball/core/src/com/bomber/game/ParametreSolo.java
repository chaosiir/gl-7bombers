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
    private Label nbDeplaJ;

    private TextButton retour;
    private TextButton facile;
    private TextButton moyen;
    private TextButton difficile;

    private ButtonGroup<TextButton> diffic;

    private TextField nbBonusT;
    private TextField nbEnnemisT;
    private TextField porteeBombeT;
    private TextField nbDeplaEnnemisT;
    private TextField nbBombeT;
    private TextField nbDeplaJT;





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
        nbDeplaJ= new Label("Nombre de deplacement du joueur :",skin);

        retour= new TextButton("Retour",skin);
        facile= new TextButton("Facile",skin,"toggle");
        moyen = new TextButton("Moyen",skin,"toggle");
        difficile= new TextButton("Difficile",skin,"toggle");

        diffic =new ButtonGroup<TextButton>();
        diffic.add(moyen);
        diffic.add(facile);
        diffic.add(difficile);

        nbBonusT= new TextField("5",skin);
        nbEnnemisT= new TextField("2",skin);
        porteeBombeT = new TextField("2",skin);
        nbDeplaEnnemisT = new TextField("3",skin);
        nbBombeT= new TextField("1",skin);
        nbDeplaJT= new TextField("5",skin);



        nbBonusT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbBonusT.getText());
                    jeu.nbBonus=x;
                }
                catch (NumberFormatException e){}



            }
        });
        nbEnnemisT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbEnnemisT.getText());
                    jeu.nbEnnemis=x;
                }
                catch (NumberFormatException e){}


            }
        });
        porteeBombeT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(porteeBombeT.getText());
                    jeu.porteeBombe=x;
                }
                catch (NumberFormatException e){}


            }
        });
        nbDeplaEnnemisT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbDeplaEnnemisT.getText());
                    jeu.nbDeplaEnnemis=x;

                }
                catch (NumberFormatException e){}

            }
        });

        nbBombeT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbBombeT.getText());
                    jeu.nbBombe=x;
                }
                catch (NumberFormatException e){}
            }
        });

        nbDeplaJT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbDeplaJT.getText());
                    jeu.nbDeplaP=x;
                }
                catch (NumberFormatException e){}
            }
        });






        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(difficulte).padBottom(30);
        table.add(facile).uniform();
        table.add(moyen).uniform();
        table.add(difficile).uniform();
        table.row();
        table.add(nbBonus).padBottom(30);
        table.add(nbBonusT);
        table.row();
        table.add(nbEnnemis).padBottom(30);
        table.add(nbEnnemisT);
        table.row();
        table.add(nbBombe).padBottom(30);
        table.add(nbBombeT);
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeT);
        table.row();
        table.add(nbDeplaEnnemis).padBottom(30);
        table.add(nbDeplaEnnemisT);
        table.row();
        table.add(nbDeplaJ).padBottom(30);
        table.add(nbDeplaJT);
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
