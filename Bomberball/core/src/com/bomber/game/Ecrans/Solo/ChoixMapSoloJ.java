package com.bomber.game.Ecrans.Solo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bomber.game.Bomberball;
import com.bomber.game.Bonus.Bonus;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.Map;
import com.bomber.game.MapetObjet.Personnage;

import java.io.File;
/**
 * Classe ChoixMapSoloJ
 * Elle affiche des maps solo que le joueur a déjà créé et sur lesquelles il veut jouer
 *
 */
public class ChoixMapSoloJ extends Etat implements Screen {
    Bomberball game;        //Instance de la classe principale
    List<String> list;      //Affiche le nom des map solo précèdemment créée
    Image back;             //Image de l'arrière-plan
    Skin skin;              //Caractéristiques des éléments graphiques

    TextButton valider;     //Bouton pour valider la map sélectionnée
    TextButton retour;      //Bouton pour revenir sur l'éditeur multi
    Table table;            //Contient les boutons
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
        f =Gdx.files.internal( "./SaveMapPerso/Mapsolo/").file();
    }


    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
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



        table=new Table();                                                     //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.setPosition(Gdx.graphics.getWidth()/2,150, Align.bottom); //Positionnement à la main

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int i=list.getSelectedIndex();
                if (i!=-1){
                    File f1=Gdx.files.internal("./SaveMapPerso/Mapsolo/"+list.getItems().get(i)+".txt").file();

                        jeu.map=Map.mapFromStringN(Bomberball.loadFile(f1));
                        game.choixMapSoloJ.removeActor(map);
                        game.choixMapSoloJ.removeActor(back);
                        game.choixMapSoloJ.removeActor(scrollPane);
                        game.choixMapSoloJ.removeActor(table);
                        jeu.removeActor(map);
                        if(map!=null){
                            map.suppActor();
                        }
                        map=null;

                        Bomberball.input.removeProcessor(game.choixMapSoloJ);
                        game.choixMapSoloJ.removeActor(jeu);
                        jeu.setEtat(game.menuSolo);
                        game.setScreen(game.menuSolo);

                }
            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.choixMapSoloJ.removeActor(back);
                game.choixMapSoloJ.removeActor(scrollPane);
                game.choixMapSoloJ.removeActor(table);
                if(map!=null){
                    map.suppActor();
                }
                game.choixMapSoloJ.removeActor(map);
                map=null;
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);

            }
        });

        list.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                               //Affichage de la map dans la mini-map
                String s=list.getSelected();
                File f1=Gdx.files.internal("./SaveMapPerso/Mapsolo/"+s+".txt").file();
                    String text=Bomberball.loadFile(f1);
                    map=Map.mapFromStringN(text);
                    map.setBounds(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*1/5+20,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
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


            }
        });

        table.add(valider);
        table.add(retour);


        this.addActor(back);
        this.addActor(scrollPane);
        this.addActor(table);
        this.addActor(jeu);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);                   //nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)

    }

    /**
     * Gère le changement de taille de la fenêtre d'affichage
     *
     * @param width : largeur nouvelle fenêtre
     * @param height : hauteur nouvelle fenêtre
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appellé lors d'un changement d'écran.
     */
    @Override
    public void hide() {
        Bomberball.stg.clear();
        jeu.removeActor(map);

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }

    /**Fonction qui gère l'appui
     * @param keycode
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Indique l'action à effectuer lorsqu'on clique avec la souris en fonction de l'élément sur lequel on a cliqué
     * @param screenX abscisse du pointeur sur l'écran
     * @param screenY ordonnée du pointeur sur l'écran
     * @param pointer pointeur de l'événement (jamais utilisée)
     * @param button bouton de la souris appuyé
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX: récupère la position x du pointeur.
     * @param screenY : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
