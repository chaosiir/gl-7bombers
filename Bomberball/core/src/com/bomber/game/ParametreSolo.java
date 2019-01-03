package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

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

    private Slider nbBonusS;
    private Slider nbEnnemisS;
    private Slider porteeBombeS;
    private Slider nbDeplaEnnemisS;
    private Slider nbBombeS;


    public ParametreSolo(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
    }

    @Override
    public void show() {
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

        nbBonusS= new Slider(0f,20f,1f,false,skin);
        nbEnnemisS =  new Slider(0f,20f,1f,false,skin);
        porteeBombeS=  new Slider(0f,20f,1f,false,skin);
        nbDeplaEnnemisS =  new Slider(0f,20f,1f,false,skin);
        nbBombeS= new Slider(0f,20f,1f,false,skin);



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
        table.add(nbBonusS);
        table.row();
        table.add(nbEnnemis).padBottom(30);
        table.add(nbEnnemisS);
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeS);
        table.row();
        table.add(nbDeplaEnnemis).padBottom(30);
        table.add(nbDeplaEnnemisS);
        table.row();
        table.add(retour).padBottom(30);

        back.setName("Arrière plan: parametre solo");

        jeu.addActor(back);
        jeu.addActor(table);

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });
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
}
