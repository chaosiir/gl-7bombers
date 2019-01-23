package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChoixCampagne extends Etat implements Screen {
    Bomberball game;
    Image back;
    Skin skin;
    List<String> list;
    ScrollPane scrollPane;
    File f;
    File nivplayer;
    File nivplayertmp;
    FileWriter fw;
    int niveauactuel=1;

    TextButton facile;
    TextButton moyen;
    TextButton difficile;
    Label choixdifficulte;

    ButtonGroup<TextButton> diffic;

    TextButton valider;
    TextButton retour;
    TextButton réinitialiserProg;
    Table table;


    Scanner scan;

    Map map;


    public ChoixCampagne(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/Campagne/");
            nivplayer= new File(directory.getCanonicalPath()+"/Campagne/niveau.txt");
            nivplayertmp=new File(directory.getCanonicalPath()+"/Campagne/niveautmp.txt");


        } catch (IOException e) {

        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public void show() {
        Bomberball.copier(nivplayer,nivplayertmp);
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");

        skin=new Skin(Gdx.files.internal("uiskin.json"));

        facile= new TextButton("Facile",skin,"toggle");
        moyen = new TextButton("Moyen",skin,"toggle");
        difficile= new TextButton("Difficile",skin,"toggle");
        choixdifficulte = new Label("Choix de la difficulte: ",skin);

        diffic =new ButtonGroup<TextButton>();
        diffic.add(moyen);
        diffic.add(facile);
        diffic.add(difficile);


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

        try{
            scan=new Scanner(nivplayertmp);
            niveauactuel=scan.nextInt();
        }
        catch (IOException e){
        }

        nivplayertmp.delete();

        int i=0;
        Array<String> tmp=new Array<String>();
        final File liste[]=f.listFiles();
        if(liste!=null && liste.length!=0 ){
            for(File fi: liste){
                if (!fi.getName().equals("tmp.txt") && !fi.getName().equals("niveau.txt") && i<niveauactuel){
                    i++;
                    tmp.add(fi.getName().substring(0,fi.getName().length()-4));
                }
                
            }
        }
        list.setItems(tmp);

        valider=new TextButton("Acceder au niveau",skin);
        retour=new TextButton("retour",skin);
        réinitialiserProg= new TextButton("Reinitialiser la progression",skin,"toggle");



        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.setPosition(Gdx.graphics.getWidth()/2,150, Align.bottom); //Positionnement à la main

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int i=list.getSelectedIndex();
                if (i!=-1){
                    File f1;
                    File directory = new File (".");
                    try {
                        f1=new File(directory.getCanonicalPath()+"/Campagne/"+list.getItems().get(i)+".txt");
                        jeu.map=Map.mapFromStringN(Bomberball.loadFile(f1));
                        table.removeActor(valider);
                        table.removeActor(retour);
                        game.choixCampagne.removeActor(back);
                        game.choixCampagne.removeActor(scrollPane);
                        game.choixCampagne.removeActor(table);

                        jeu.removeActor(map);
                        if(map!=null){
                            map.suppActor();
                        }
                        map=null;
                        game.choixCampagne.removeActor(jeu);
                        Bomberball.input.removeProcessor(game.choixCampagne);
                        game.campagne.setMapactuel(i+1);
                       jeu.setEtat(game.campagne);
                       game.setScreen(game.campagne);

                    } catch (IOException e) {

                    }
                }
            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.removeActor(valider);
                table.removeActor(retour);
                game.choixCampagne.removeActor(back);
                game.choixCampagne.removeActor(scrollPane);
                game.choixCampagne.removeActor(table);


                jeu.difficulte=-1;
                jeu.removeActor(map);
                map=null;
                game.choixCampagne.removeActor(jeu);
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
                    f1=new File(directory.getCanonicalPath()+"/Campagne/"+s+".txt");
                    String text=Bomberball.loadFile(f1);
                    map=Map.mapFromStringN(text);
                    map.setBounds(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*1/5+20,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                    map.setScale(0.8f);
                    jeu.addActor(map);

                } catch (IOException e) {
                }

            }
        });

        réinitialiserProg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    fw=new FileWriter(nivplayer);
                    fw.write("1");
                    fw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        facile.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.difficulte=1;
            }
        });

        moyen.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.difficulte=2;
            }
        });

        difficile.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.difficulte=3;
            }
        });


        table.add(valider);
        table.add(retour);
        table.add(réinitialiserProg);
        table.row();
        table.add(choixdifficulte);
        table.add(facile);
        table.add(moyen);
        table.add(difficile);


        this.addActor(back);
        this.addActor(scrollPane);
        this.addActor(table);
        this.addActor(jeu);

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
}
