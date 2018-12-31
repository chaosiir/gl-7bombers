package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuPrincipal extends Etat {
    private Stage menuprincipal;
    private Sprite back;
    private Skin skin;
    private Table table;
    private TextButton soloButton;
    private  TextButton multiButton;
    private TextButton editeurButton;
    private TextButton quitButton;

    public MenuPrincipal(Jeu jeu) {
        super(jeu);
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    public void createMenu(){
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        menuprincipal = new Stage(new ScreenViewport());

        table=new Table(); //Tableau
        table.setWidth(menuprincipal.getWidth());
        table.align(Align.center | Align.top); // Middle of the screen start at the top
        table.setPosition(0, Gdx.graphics.getHeight());

        soloButton = new TextButton("Mode Solo",skin);
        multiButton = new TextButton("Mode multijoueur",skin);
        editeurButton = new TextButton("Mode Editeur de niveau",skin);
        quitButton = new TextButton("Quitter le jeu",skin);

        soloButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Clicked","Yes, You did");
            }
        });

        table.padTop(30); //Espace de 30 entre le premier texte et le haut

        table.add(soloButton).padBottom(30); //Espace de 30 entre les 2 boutons
        table.row(); //Permet de les mettre sous forme de colonne
        table.add(quitButton);

        menuprincipal.addActor(table);

        back= new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }

}
