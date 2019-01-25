package com.bomber.game.Ecrans.Campagne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ecran pour choisir le niveau de la campagne
 */
public class ChoixCampagne extends Etat implements Screen {
    Bomberball game;                            //Instance de la classe principale
    Image back;                                 //Image de l'arrière-plan
    Skin skin;                                  //Caractéristiques des éléments graphiques
    List<String> list;                          //Affichage de la liste des maps
    ScrollPane scrollPane;                      //Permet de gérer le choix des map s'il y en a plus
    File f;                                     //Dossier contenant la campagne
    File nivplayer;                             //Fichier du niveau
    File nivplayertmp;                          //Fichier de sauvegarde temporaire du niveau
    FileWriter fw;                              //Changer le niveau du joueur
    int niveauactuel=1;                         //Stockage du niveau actuel en local
    Scanner scan;                               //Récupère l'entier
    Map map;                                    //Mini-map affichée

    TextButton facile;                          //Bouton pour choisir la difficulté Facile
    TextButton moyen;                           //Bouton pour choisir la difficulté Moyen
    TextButton difficile;                       //Bouton pour choisir la difficulté Difficile
    Label choixdifficulte;                      //Texte pour présenter la difficulté
    ButtonGroup<TextButton> diffic;             //Permet de laisser un bouton appuyé au détriment des autres
    TextButton valider;                         //Bouton pour accéder au niveau sélectionné
    TextButton retour;                          //Bouton pour revenir au mode solo
    TextButton réinitialiserProg;               //Bouton pour remettre à zéro la progression du joueur
    Table table;                                //Contient les boutons


    /**
     * Choisir le niveau du mode campagne.
     * @param game
     * @param jeu
     */
    public ChoixCampagne(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
        f = Gdx.files.internal("./Campagne/").file();
        nivplayer= Gdx.files.internal("./Campagne/niveau.txt").file();
        nivplayertmp=Gdx.files.internal("./Campagne/niveautmp.txt").file();


    }

    /**
     * Permet de détecter un appui sur une touche
     * @param keycode : vaut le numéro de la touche enfoncée
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Fonction détectant un appui d'une des touches de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX : récupère la position x du pointeur.
     * @param screenY : récupère la position y du pointeur.
     * @param pointer : récupère le pointeur sur événement.
     * @param button : donne le bouton appuyé.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Fonction détectant un mouvement de la souris. On n'utilise pas cette fonctionnalité par la suite.
     * @param screenX : récupère la position x du pointeur.
     * @param screenY : récupère la position x du pointeur.
     * @return false: on ne traite pas cet appui
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.copier(nivplayer,nivplayertmp);
        Bomberball.stg.addActor(this);                                                  //Affiche l'écran
        Bomberball.stg.setKeyboardFocus(this);                                          //Récupère le contrôle des touches
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
        final File liste[]=f.listFiles();                                           //On récupère les fichiers présents dans Campagne
        if(liste!=null && liste.length!=0 ){
            for(File fi: liste){
                if (!fi.getName().equals("niveau.txt")){                            //Le seul fichier que l'on ne récupère pas à pour nom niveau.txt
                    i++;
                    tmp.add(fi.getName().substring(0,fi.getName().length()-4));     //On n'affiche pas le .txt
                }
                
            }
        }

        tmp.sort();                                                                 //Tri nécessaire car on fait la correspondance entre le niveau et l'indice dans la liste

        while(tmp.size!=niveauactuel){                                              //On réduit la liste au nombre de niveaux débloqués
            tmp.removeIndex(tmp.size-1);
        }

        list.setItems(tmp);

        valider=new TextButton("Acceder au niveau",skin);
        retour=new TextButton("retour",skin);
        réinitialiserProg= new TextButton("Reinitialiser la progression",skin,"toggle");



        table=new Table();
        table.setWidth(Bomberball.stg.getWidth());
        table.setPosition(Gdx.graphics.getWidth()/2,150, Align.bottom);                             //Positionnement à la main

        valider.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                                      //Permet de confirmer la map que le joueur veut jouer en campagne
                int i=list.getSelectedIndex();
                if (i!=-1){
                    File f1;
                    File directory = new File (".");                                            //Il se place dans le dossier principal du jeu Assets
                        f1=Gdx.files.internal("./Campagne/"+list.getItems().get(i)+".txt").file();  //Permet de récupèrer le fichier
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
                        Bomberball.input.removeProcessor(game.choixCampagne);                           //On libère les inputs pour le prochain Screen
                        game.campagne.setMapactuel(i+1);
                       jeu.setEtat(game.campagne);
                       game.setScreen(game.campagne);

                }
            }
        });

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                                   //Permet de retourner au niveau précèdent
                table.removeActor(valider);
                table.removeActor(retour);
                game.choixCampagne.removeActor(back);
                game.choixCampagne.removeActor(scrollPane);
                game.choixCampagne.removeActor(table);


                jeu.difficulte=-1;                                                                  //Permet de réinitialiser la difficulté à défault
                jeu.removeActor(map);
                map=null;
                game.choixCampagne.removeActor(jeu);
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });

        list.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                                   //Permet de voir si le joueur a cliqué sur un élément de la liste
                String s=list.getSelected();
                File f1;
                File directory = new File (".");

                    f1=Gdx.files.internal("./Campagne/"+s+".txt").file();
                    String text=Bomberball.loadFile(f1);
                    map=Map.mapFromStringN(text);
                    map.setBounds(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()*1/5+20,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                    map.setScale(0.8f);
                    jeu.addActor(map);


            }
        });

        réinitialiserProg.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {                   //Réinitialise le niveau de la campagne à 1
                try {
                    fw=new FileWriter(nivplayer);
                    fw.write("1");
                    fw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if(map!=null) {
                    map.suppActor();
                }
                map = null;
                jeu.removeActor(map);
                Array<String> tmp=new Array<String>();
                File liste1[]=f.listFiles();
                if(liste1!=null && liste1.length!=0){
                    for(File fi: liste1){
                        if (!fi.getName().equals("niveau.txt")){

                            tmp.add(fi.getName().substring(0,fi.getName().length()-4));
                        }



                    }
                }
                tmp.sort();

                while(tmp.size!=1){
                    tmp.removeIndex(tmp.size-1);
                }

                list.setItems(tmp);
                map = null;
                jeu.removeActor(map);

            }
        });

        facile.addListener(new ClickListener(){ //Stocke la difficulté choisie
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.difficulte=1;
            }
        });

        moyen.addListener(new ClickListener(){ //Stocke la difficulté choisie

            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.difficulte=2;
            }
        });
             difficile.addListener(new ClickListener(){ //Stocke la difficulté choisie
                 @Override
                 public void clicked(InputEvent event, float x, float y) {
                     jeu.difficulte=3;
                 }
             });

/** Ajout des acteurs dans Choixcampagne**/

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

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     * @param delta
     */
    @Override
    public void render(float delta) {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas dans le code par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appellé lors d'un changement d'écran.
     */
    @Override
    public void hide() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On ne l'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }
}
