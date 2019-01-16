package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.io.IOException;
/**
 * Classe ChoixMapSoloJ
 * Elle affiche des maps solo que le joueur a déjà créé et sur lesquelles il veut jouer
 * @author Paul-Louis Renard
 *
 */
public class ChoixMapSoloJ extends Etat implements Screen {
    Bomberball game;
    List<String> list;
    Image back;
    Skin skin;

    TextButton valider;
    TextButton retour;
    Table table;
    ScrollPane scrollPane;

    Map map;

    File f;

    /**
     * Constructeur de la classe ChoixMapSoloJ
     * @param game
     * @param jeu
     * @return un menu ChoixMapSoloJ
     */
    public ChoixMapSoloJ(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveMapPerso/Mapsolo/");

        } catch (IOException e) {

        }
    }

    public ChoixMapSoloJ(Jeu jeu) {
        super(jeu);
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        skin=new Skin(Gdx.files.internal("uiskin.json"));

        list=new List<String>(skin);
        list.getSelection().setMultiple(false);
        list.setBounds(0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        scrollPane = new ScrollPane(list, skin);
        scrollPane.setBounds(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()*4/5);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setPosition(0,
                Gdx.graphics.getHeight()  - scrollPane.getHeight() );
        scrollPane.setTransform(true);
        scrollPane.setScrollingDisabled(true,false);
        scrollPane.setForceScroll(false,false);
        scrollPane.layout();

        Array<String> tmp=new Array<String>();
        File liste[]=f.listFiles();
        if(liste!=null && liste.length!=0){
            for(File fi: liste){
                if (!fi.getName().equals("tmp.txt")){

                    tmp.add(fi.getName().substring(0,fi.getName().length()-4));
                }



            }
        }
        list.setItems(tmp);

        valider=new TextButton("Valider",skin);
        retour=new TextButton("retour",skin);



        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.setPosition(Gdx.graphics.getWidth()/2,150, Align.bottom); //Positionnement à la main

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int i=list.getSelectedIndex();
                if (i!=-1){
                    File f1;
                    File f2;
                    File directory = new File (".");
                    try {
                        f1=new File(directory.getCanonicalPath()+"/SaveMapPerso/Mapsolo/"+list.getItems().get(i)+".txt");
                        jeu.map=Map.mapFromString(Bomberball.loadFile(f1));
                        jeu.removeActor(jeu.findActor("YOLO"));
                        jeu.removeActor(back);
                        jeu.removeActor(scrollPane);
                        jeu.removeActor(table);
                        jeu.setEtat(game.menuSolo);
                        game.setScreen(game.menuSolo);

                    } catch (IOException e) {

                    }
                }
            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(back);
                jeu.removeActor(scrollPane);
                jeu.removeActor(table);
                jeu.removeActor(jeu.findActor("YOLO"));
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);

            }
        });

        list.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String s=list.getSelected();
                File f1;
                File directory = new File (".");
                try {
                    f1=new File(directory.getCanonicalPath()+"/SaveMapPerso/Mapsolo/"+s+".txt");
                    String text=Bomberball.loadFile(f1);
                    map=Map.mapFromString(text);
                    map.setBounds(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*1/5+20,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                    map.setName("YOLO");
                    map.setScale(0.8f);
                    for (int i=0;i<15;i++){
                        for (int j=0;j<13;j++){
                            if(map.getGrille()[i][j].getBonus()!=null){


                                Bonus b=map.getGrille()[i][j].getBonus();
                                map.getGrille()[i][j].setBonus(null);
                                map.getGrille()[i][j].setBonus(b);
                                map.getGrille()[i][j].getBonus().setScale(0.5f);
                            }
                            if(map.getGrille()[i][j].getPersonnage()!=null){
                                Personnage personnage=map.getGrille()[i][j].getPersonnage();
                                map.getGrille()[i][j].setPersonnage(personnage);
                            }
                        }
                    }
                    jeu.addActor(map);

                } catch (IOException e) {

                }

            }
        });

        table.add(valider);
        table.add(retour);


        jeu.addActor(back);
        jeu.addActor(scrollPane);
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

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }
}
