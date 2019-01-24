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
import java.io.IOException;
/**
 * Classe ChoixMapMultiE
 * Elle affiche des maps que le joueur a déjà créé en multijoueur et qu'il veut remodifié
 * @author Paul-Louis Renard
 *
 */
public class ChoixMapMultiE extends Etat implements Screen {

    Bomberball game;
    List<String> list;
    Image back;
    Skin skin;

    TextButton valider;
    TextButton retour;
    TextButton supprimer;
    Table table;
    ScrollPane scrollPane;
    Map map;

    File f;
    /**
     * Constructeur de la fenêtre
     * @param game  La classe principal du jeu
     * @param jeu   Un jeu contenant les acteurs
     */
    public ChoixMapMultiE(Bomberball game, Jeu jeu){
        super(jeu);
        this.game=game;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveMapPerso/MapMulti/");

        } catch (IOException e) {

        }
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        /*Creation de l'arriere-plan*/
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
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
        final File liste[]=f.listFiles();
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
        supprimer=new TextButton("supprimer",skin);



        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.setPosition(Gdx.graphics.getWidth()/2,150, Align.bottom); //Positionnement à la main

        /*Creation du bouton de validation de la map choisie*/
        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int i=list.getSelectedIndex();
                if (i!=-1){
                    File f1;
                    File f2;
                    File directory = new File (".");
                    try {
                        //La map dans l'editeur devient celle actuellement selectionnee dans le menu
                        f2 = new File(directory.getCanonicalPath() + "/SaveMapPerso/MapMulti/tmp.txt");
                        f1=new File(directory.getCanonicalPath()+"/SaveMapPerso/MapMulti/"+list.getItems().get(i)+".txt");
                        f2.delete();
                        Bomberball.copier(f1,f2);
                        table.removeActor(valider);
                        table.removeActor(retour);
                        game.choixMapMultiE.removeActor(back);
                        game.choixMapMultiE.removeActor(scrollPane);
                        game.choixMapMultiE.removeActor(table);

                        jeu.removeActor(map);
                        if(map!=null){
                            map.suppActor();
                        }
                        map=null;

                        //On quitte le menu de choix pour revenir a l'editeur
                        game.choixMapMultiE.removeActor(jeu);
                        Bomberball.input.removeProcessor(game.choixMapMultiE);
                        jeu.setEtat(game.editeurNMulti);
                        game.setScreen(game.editeurNMulti);

                    } catch (IOException e) {

                    }
                }
            }
        });

        /*Creation du bouton de retour a l'editeur multijoueur*/
        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //On quitte le menu de selection de map
                table.removeActor(valider);
                table.removeActor(retour);
                game.choixMapMultiE.removeActor(back);
                game.choixMapMultiE.removeActor(scrollPane);
                game.choixMapMultiE.removeActor(table);

                jeu.removeActor(map);
                map=null;

                //On repasse dans l'editeur multi
                game.choixMapMultiE.removeActor(jeu);
                jeu.setEtat(game.editeurNMulti);
                game.setScreen(game.editeurNMulti);
            }
        });

        /*Creation de l'espace de selection des maps*/
        list.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String s=list.getSelected();
                File f1;
                File directory = new File (".");
                try {
                    //On recupere le fichier texte associee au nom de map selectionnee et on le convertit en image avant de l'afficher
                    f1=new File(directory.getCanonicalPath()+"/SaveMapPerso/MapMulti/"+s+".txt");
                    String text=Bomberball.loadFile(f1);
                    map=Map.mapFromStringN(text);
                    map.setBounds(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*1/5+20,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                    map.setScale(0.8f);
                    jeu.addActor(map);

                } catch (IOException e) {

                }

            }
        });

        /*Creation du bouton de suppression d'une map en memoire*/
        supprimer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //La map selectionnee est supprimee des maps sauvegardees
                String s=list.getSelected();
                if(s!=null){
                    File f1;
                    File directory = new File (".");
                    try{
                        f1=new File(directory.getCanonicalPath()+"/SaveMapPerso/MapMulti/"+s+".txt");
                        f1.delete();
                    }
                    catch (IOException e){     }
                    map.suppActor();
                    map = null;
                    jeu.removeActor(map);
                }
                Array<String> tmp=new Array<String>();
                final File liste[]=f.listFiles();
                if(liste!=null && liste.length!=0){
                    for(File fi: liste){
                        if (!fi.getName().equals("tmp.txt")){

                            tmp.add(fi.getName().substring(0,fi.getName().length()-4));
                        }



                    }
                }
                list.setItems(tmp);
            }
        });

        table.add(valider);
        table.add(retour);
        table.add(supprimer);


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
    Bomberball.stg.clear();
    jeu.removeActor(map);
    jeu.removeActor(jeu.findActor("Map"));

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown( int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
