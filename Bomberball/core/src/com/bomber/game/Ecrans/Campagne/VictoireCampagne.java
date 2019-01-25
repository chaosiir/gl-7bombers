package com.bomber.game.Ecrans.Campagne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;
import com.bomber.game.MapetObjet.Map;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class VictoireCampagne extends Etat implements Screen {
    Bomberball game;            //Instance de la classe principale
    int niveaugag;              //Niveau que le joueur a gagné
    Image back;                 //Image de l'arrière-plan

    Label explication;          //Message associé à la victoire d'un niveau
    Skin skin;                  //Caractéristiques des éléments graphiques
    TextButton continuer;       //Bouton pour continuer la campagne
    TextButton recommencer;     //Bouton pour recommencer le niveau actuel
    TextButton quitter;         //Bouton pour retourner sur le menu solo
    Table table;                //Contient les boutons

    File frecommencer;          //Fichier qui permet de sauvegarder le début d'une partie
    File f;                     //Fichier pour pouvoir lancer le début
    File recupniv;              //Répertoire où sont contenus les maps de la campagne
    File nivplayertmp;          //Fichier de sauvegarde temporaire du niveau
    File niveau;                //Fichier du niveau
    FileWriter fw;              //Changer le niveau du joueur
    Scanner scanner;

    public VictoireCampagne(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
        recupniv=Gdx.files.internal("./Campagne/").file();
        f=Gdx.files.internal("./SaveTempo/tmp.txt").file();
        frecommencer = Gdx.files.internal("./SaveTempo/debut.txt").file();
        niveau = Gdx.files.internal("./Campagne/niveau.txt").file();        //Récupérer l'avancement du joueur
        nivplayertmp=Gdx.files.internal("./Campagne/niveautmp.txt").file();


    }

    public void setNiveaugag(int x){
        this.niveaugag=x;
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
        table=new Table();

        Bomberball.copier(niveau,nivplayertmp);

        try{
            scanner=new Scanner(nivplayertmp);          //On récupère le niveau dans le fichier temporaire



        }
        catch (IOException e){}

        final int niv=scanner.nextInt();

        nivplayertmp.delete();


        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, 2*Gdx.graphics.getHeight()/4);

        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));

        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");




        explication=new Label("Niveau reussi",skin);

       explication.setBounds(Gdx.graphics.getWidth()/2-200,Gdx.graphics.getHeight()/2,explication.getWidth(),explication.getHeight()); //Positionnement à la main


        continuer= new TextButton("Continuer",skin);

        recommencer=new TextButton("Recommencer",skin);

        quitter= new TextButton("Quitter",skin);

        continuer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {       //Permet de passer au niveau suivant de la campagne
                if(niv==niveaugag && niv<5){                                //Si le joueur a effectué le dernier niveau de sa campagne, on peut augmenter
                    try{                                                    //le nombre de niveau effectué s'il n'a pas déjà atteint le dernier niveau
                        fw=new FileWriter(niveau);
                        fw.write(""+(niv+1));
                        fw.close();
                    }
                    catch (IOException e){}

                }
                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.victoireCampagne.removeActor(jeu);
                frecommencer.delete();                                  //On supprime la sauvegarde pour recommencer
                f.delete();                                             //On supprime le fichier temporaire


                /** Code pour récupérer la liste et prendre le niveau suivant **/

                final File liste[]=recupniv.listFiles();
                Array<String> tmp=new Array<String>();
                if(liste!=null &&liste.length!=0) {                                         //Récupération de tous les fichiers de la campagne sauf niveau.txt
                    for (File fi : liste) {
                        if (!fi.getName().equals("niveau.txt")) {
                            tmp.add(fi.getName());
                        }
                    }
                }
                tmp.sort();                                                                 //Tri pour prendre le bon niveau en fonction de l'indice
                File lo= Gdx.files.internal("./Campagne/"+tmp.get(niveaugag)).file(); //Récupération du bon fichier
                if(liste!=null && tmp.get(niveaugag)!=null){
                    jeu.map=Map.mapFromStringN(Bomberball.loadFile(lo));
                }

                /*****************************************************************/

                game.campagne.setMapactuel(niveaugag+1);
                game.campagne.u=0;
                jeu.setEtat(game.campagne);
                game.setScreen(game.campagne);
            }
        });

        recommencer.addListener(new ClickListener(){                                        //Permet de recommencer la map effectué
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(niv==niveaugag && niv<5){                                                //Si le joueur a effectué le dernier niveau de sa campagne, on peut augmenter
                    try{                                                                    //le nombre de niveau effectué s'il n'a pas déjà atteint le dernier niveau
                        fw=new FileWriter(niveau);
                        fw.write(""+(niv+1));
                        fw.close();
                    }
                    catch (IOException e){}

                }
                f.delete();
                frecommencer.renameTo(f);
                frecommencer.delete();
                jeu.recommencer=true;
                jeu.map=null;
                jeu.removeActor(jeu.map);
                game.victoireCampagne.removeActor(jeu);
                jeu.setEtat(game.campagne);
                game.setScreen(game.campagne);
            }
        });

        quitter.addListener(new ClickListener(){                                        //Permet de quitter et revenir au menu Solo
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(niv==niveaugag && niv<5){                                        //A modifier si le nombre de niveau augmente
                    try{
                        fw=new FileWriter(niveau);
                        fw.write(""+(niv+1));
                        fw.close();
                    }
                    catch (IOException e){}

                }
                jeu.removeActor(jeu.map);
                jeu.map=null;
                jeu.difficulte=-1;
                game.victoireCampagne.removeActor(jeu);
                frecommencer.delete();
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });
/** Ajout des acteurs dans VictoireCampagne**/

        table.add(continuer);
        table.add(recommencer);
        table.add(quitter);

        this.addActor(back);
        this.addActor(table);
        this.addActor(explication);
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
