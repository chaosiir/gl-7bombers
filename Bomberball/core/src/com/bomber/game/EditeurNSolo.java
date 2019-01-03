package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    Image back;
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
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        int ymax= Toolkit.getDefaultToolkit().getScreenSize().height;

        skin=new Skin(Gdx.files.internal("uiskin.json"));
        floor=new Image(Bomberball.multiTexture[0]);
        floor.setName("floor");
        floor.setBounds(0,ymax-Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);
        
        murd = new Image(Bomberball.multiTexture[1]);
        murd.setName("murd");
        murd.setBounds(0,ymax-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        muri= new Image(Bomberball.multiTexture[2]);
        muri.setName("muri");
        muri.setBounds(0,ymax-3*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);


        porte= new Image(Bomberball.multiTexture[3]);
        porte.setName("porte");
        porte.setBounds(0,ymax-4*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);







        jeu.addActor(back);
        jeu.addActor(floor);
        jeu.addActor(murd);
        jeu.addActor(muri);
        jeu.addActor(porte);













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
        Actor hitActor= jeu.getStage().hit(x,Gdx.graphics.getHeight()-y,false); //Retourne référence de l'acteur touché
        //De base, hit fait un setbounds pour voir si l'acteur est dedans | On peut réécrire le hit (par exemple si on a un cercle)
        if(hitActor!=null){
            Gdx.app.log("HIT",hitActor.getName()); //print dans le log de l'application
        }


        return true;

    }
}
