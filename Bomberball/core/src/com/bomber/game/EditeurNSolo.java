package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class EditeurNSolo extends Etat implements Screen {

    Bomberball game;
    Image floor;
    Image porte;
    Image murd;
    Image muri;
    Skin skin;

    Table listeObjet;


    public EditeurNSolo(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
    }

    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        floor=new Image(Bomberball.multiTexture[0]);
        floor.setName("floor");
        floor.setSize(50,50);
        porte= new Image(Bomberball.multiTexture[3]);
        porte.setName("porte");
        porte.setSize(50,50);
        murd = new Image(Bomberball.multiTexture[1]);
        murd.setName("murd");
        murd.setSize(50,50);
        muri= new Image(Bomberball.multiTexture[2]);
        muri.setName("muri");
        muri.setSize(50,50);

        listeObjet= new Table();
        listeObjet.setWidth(Bomberball.stg.getWidth());
        listeObjet.align(Align.topLeft); // Middle of the screen start at the top
        listeObjet.setPosition(0, Gdx.graphics.getHeight());

        listeObjet.add(floor).padBottom(30);
        listeObjet.row();
        listeObjet.add(murd).padBottom(30);
        listeObjet.row();
        listeObjet.add(muri).padBottom(30);
        listeObjet.row();
        listeObjet.add(porte).padBottom(30);





        jeu.addActor(listeObjet);







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
       /* Vector2 coord = Bomberball.stg.screenToStageCoordinates(new Vector2((float)x,(float)y)); //Mauvaise pratique car créer dès que l'on clique mais convertit coordonnée
        Actor hitActor= Bomberball.stg.hit(coord.x,coord.y,false); //Retourne référence de l'acteur touché
        //De base, hit fait un setbounds pour voir si l'acteur est dedans | On peut réécrire le hit (par exemple si on a un cercle)
        if(hitActor!=null){
            Gdx.app.log("HIT",hitActor.getName()); //print dans le log de l'application
        }*/


        return true;

    }
}
