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

/**
 *  Ecran de victoire s'affichant dans le mode campagne.
 */
public class VictoireCampagne extends Etat implements Screen {
    Bomberball game;
    int niveaugag; //Niveau que le joueur a gagné
    Image back;

    Label explication;
    Skin skin;
    TextButton continuer;
    TextButton recommencer;
    TextButton quitter;
    Table table;

    File frecommencer;
    File f;
    File recupniv;
    File nivplayertmp;
    File niveau;
    FileWriter fw;
    Scanner scanner;

    /**
     * Affiche un écran de victoire lorsqu'il a fini un niveau intermédaire de la campagne (tous sauf le dernier).
     * @param game
     * @param jeu
     */
    public VictoireCampagne(Bomberball game, Jeu jeu) {
        super(jeu);
        this.game = game;
        recupniv = Gdx.files.internal("./Campagne/").file();
        f = Gdx.files.internal("./SaveTempo/tmp.txt").file();
        frecommencer = Gdx.files.internal("./SaveTempo/debut.txt").file();
        niveau = Gdx.files.internal("./Campagne/niveau.txt").file();//Récupérer l'avancement du joueur
        nivplayertmp = Gdx.files.internal("./Campagne/niveautmp.txt").file();


    }

    /**
     * Fixe niveaugag à la valeur x. Sera utile pour mettre à jour la progression du joueur.
     * @param x
     */
    public void setNiveaugag(int x) {
        this.niveaugag = x;
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
     * Fonction détectant un appui d'un des touche de la souris. On n'utilise pas cette fonctionnalité par la suite.
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
        table = new Table();

        Bomberball.copier(niveau, nivplayertmp);

        try {
            scanner = new Scanner(nivplayertmp);


        } catch (IOException e) {
        }

        final int niv = scanner.nextInt();

        nivplayertmp.delete();


        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top); // Middle of the screen start at the top
        table.setPosition(0, 2 * Gdx.graphics.getHeight() / 4);

        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        back = new Image(new Texture(Gdx.files.internal("backmain.png")));
        back.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        back.setName("Je suis ton arrière plan");


        explication = new Label("Niveau reussi", skin);

        explication.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2, explication.getWidth(), explication.getHeight()); //Positionnement à la main


        continuer = new TextButton("Continuer", skin);
        //continuer.setBounds(xmax/2-50,ymax/2-Bomberball.taillecase,continuer.getWidth(),continuer.getHeight()); //Positionnement à la main

        recommencer = new TextButton("Recommencer", skin);

        quitter = new TextButton("Quitter", skin);

        continuer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (niv == niveaugag && niv < 5) { //A modifier si le nombre de niveau augmente
                    try {
                        fw = new FileWriter(niveau);
                        fw.write("" + (niv + 1));
                        fw.close();
                    } catch (IOException e) {
                    }

                }
                jeu.removeActor(jeu.map);
                jeu.map = null;
                game.victoireCampagne.removeActor(jeu);
                frecommencer.delete();
                f.delete();

                final File liste[] = recupniv.listFiles();
                Array<String> tmp = new Array<String>();
                if (liste != null && liste.length != 0) {
                    for (File fi : liste) {
                        if (!fi.getName().equals("niveau.txt")) {
                            tmp.add(fi.getName());
                        }
                    }
                }
                tmp.sort();
                File lo = Gdx.files.internal("./Campagne/" + tmp.get(niveaugag)).file();
                if (liste != null && tmp.get(niveaugag) != null) {
                    jeu.map = Map.mapFromStringN(Bomberball.loadFile(lo));
                }

                game.campagne.setMapactuel(niveaugag + 1);
                game.campagne.u = 0;
                jeu.setEtat(game.campagne);
                game.setScreen(game.campagne);
            }
        });

        recommencer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (niv == niveaugag && niv < 5) { //A modifier si le nombre de niveau augmente
                    try {
                        fw = new FileWriter(niveau);
                        fw.write("" + (niv + 1));
                        fw.close();
                    } catch (IOException e) {
                    }

                }
                f.delete();
                frecommencer.renameTo(f);
                frecommencer.delete();
                jeu.recommencer = true;
                jeu.map = null;
                jeu.removeActor(jeu.map);
                game.victoireCampagne.removeActor(jeu);
                jeu.setEtat(game.campagne);
                game.setScreen(game.campagne);
            }
        });

        quitter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (niv == niveaugag && niv < 5) { //A modifier si le nombre de niveau augmente
                    try {
                        fw = new FileWriter(niveau);
                        fw.write("" + (niv + 1));
                        fw.close();
                    } catch (IOException e) {
                    }

                }
                jeu.removeActor(jeu.map);
                jeu.map = null;
                jeu.difficulte = -1;
                game.victoireCampagne.removeActor(jeu);
                frecommencer.delete();
                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });

        table.add(continuer);
        table.add(recommencer);
        table.add(quitter);

        this.addActor(back);
        this.addActor(table);
        this.addActor(explication);
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
