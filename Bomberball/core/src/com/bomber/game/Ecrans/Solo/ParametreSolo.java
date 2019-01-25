package com.bomber.game.Ecrans.Solo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;

/**
 * Classe ParametreSolo
 * Elle affiche les paramètres modifiables par le joueur lorsqu'il joue en mode solo
 *
 */
public class ParametreSolo extends Etat implements Screen {
    Bomberball game;                            //Instance de la classe principale
    private Skin skin;                          //Caractéristiques des éléments graphiques
    private Image back;                         //Image de l'arrière-plan
    private Table table;                        //Permet d'organiser l'écran
    private Label difficulte;                   //Texte indiquant que l'on peut modifier la difficulté
    private Label nbBonus;                      //Texte indiquant que l'on peut modifier le nombre de bonus
    private Label nbBombe;                      //Texte indiquant que l'on peut modifier le nombre de bombes
    private Label nbEnnemis;                    //Texte indiquant que l'on peut modifier le nombre d'ennemis
    private Label porteeBombe;                  //Texte indiquant que l'on peut modifier la portée d'une bombe
    private Label nbDeplaJ;                     //Texte indiquant que l'on peut modifier le nombre de déplacements du joueur
    private Label nbBlocD;                      //Texte indiquant que l'on peut modifier la difficulté

    private TextButton retour;                  //Bouton retour
    private TextButton facile;                  //Bouton facile
    private TextButton moyen;                   //Bouton moyen
    private TextButton difficile;               //Bouton difficile

    private ButtonGroup<TextButton> diffic;     //Permet qu'il n'y ait que facile, moyen ou difficile activé à la fois

    private TextField nbBonusT;                 //Permet de rentrer le nombre de bonus
    private TextField nbEnnemisT;               //Permet de rentrer le nombre d'ennemis
    private TextField porteeBombeT;             //Permet de rentrer la portée des bombes
    private TextField nbBombeT;                 //Permet de rentrer le nombre de bombes
    private TextField nbDeplaJT;                //Permet de rentrer le nombre de déplacements du joueur
    private TextField nbBlocDT;                 //Permet de rentrer le nombre de blocs destructibles




    /**
     * Constructeur de la classe ParametreSolo
     * @param game la classe principale
     * @param jeu
     */
    public ParametreSolo(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        // called when this screen is set as the screen with game.setScreen();
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        difficulte=new Label("Difficulte :",skin);
        nbBonus=new Label("Nombre de bonus :",skin);
        nbEnnemis= new Label("Nombre d'ennemis :",skin);
        porteeBombe= new Label("Portee d'une bombe :",skin);
        nbBombe= new Label("Nombre de Bombes :", skin);
        nbDeplaJ= new Label("Nombre de deplacement du joueur :",skin);
        nbBlocD=new Label("Nombre de bloc destructible :",skin);

        retour= new TextButton("Retour",skin);
        facile= new TextButton("Facile",skin,"toggle");
        moyen = new TextButton("Moyen",skin,"toggle");
        difficile= new TextButton("Difficile",skin,"toggle");

        diffic =new ButtonGroup<TextButton>();
        diffic.add(moyen);
        diffic.add(facile);
        diffic.add(difficile);

        nbBonusT= new TextField("5",skin);
        nbEnnemisT= new TextField("2",skin);
        porteeBombeT = new TextField("2",skin);
        nbBombeT= new TextField("1",skin);
        nbDeplaJT= new TextField("5",skin);
        nbBlocDT = new TextField("40",skin);


        facile.addListener(new ClickListener(){                                                 //On récupère la difficulté du niveau choisi
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
        nbBonusT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    int x = Integer.parseInt(nbBonusT.getText());
                    if (x >= 0) {
                        jeu.nbBonus = x;
                    }
                }
                catch (NumberFormatException e){}



            }
        });
        nbBlocDT.addListener(new ChangeListener() {                                             //On récupère les autres paramètres
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int nbBD=Integer.parseInt(nbBlocDT.getText());
                    if(nbBD>=0){
                        jeu.nbBlocD=nbBD;
                    }
                }
                catch (NumberFormatException e){
                }
            }
        });
        nbEnnemisT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbEnnemisT.getText());
                    jeu.nbEnnemis=x;
                }
                catch (NumberFormatException e){}


            }
        });
        porteeBombeT.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(porteeBombeT.getText());
                    jeu.porteeBombe=x;
                }
                catch (NumberFormatException e){}


            }
        });


        nbBombeT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbBombeT.getText());
                    jeu.nbBombe=x;
                }
                catch (NumberFormatException e){}
            }
        });

        nbDeplaJT.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try{
                    int x=Integer.parseInt(nbDeplaJT.getText());
                    jeu.nbDeplaP=x;
                }
                catch (NumberFormatException e){}
            }
        });






        table=new Table();                                                  //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(difficulte).padBottom(30);
        table.add(facile).uniform();
        table.add(moyen).uniform();
        table.add(difficile).uniform();
        table.row();
        table.add(nbBlocD).padBottom(30);
        table.add(nbBlocDT);
        table.row();
        table.add(nbBonus).padBottom(30);
        table.add(nbBonusT);
        table.row();
        table.add(nbEnnemis).padBottom(30);
        table.add(nbEnnemisT);
        table.row();
        table.add(nbBombe).padBottom(30);
        table.add(nbBombeT);
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeT);
        table.row();
        table.add(nbDeplaJ).padBottom(30);
        table.add(nbDeplaJT);
        table.row();
        table.add(retour).padBottom(30);

        back.setName("Arrière plan: parametre solo");

        this.addActor(back);
        this.addActor(table);

        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {


                jeu.setEtat(game.menuSolo);
                game.setScreen(game.menuSolo);
            }
        });
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    /**
     * Met à jour l'affichage
     * @param delta: Intervalle de temps entre deux affichages
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void pause() {

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void resume() {

    }

    /**
     * Fonction appelée lors d'un changement d'écran.
     */
    @Override
    public void hide() {
        Bomberball.stg.clear();

    }

    /**
     * Fonction nécessaire à l'implémentation de l'écran. On n'utilise pas cette fonctionnalité par la suite.
     */
    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Indique l'action à effectuer lorsqu'on clique avec la souris en fonction de l'élément sur lequel on a cliqué
     * @param x abscisse du pointeur sur l'écran
     * @param y ordonnée du pointeur sur l'écran
     * @param pointer pointeur de l'événement (jamais utilisé)
     * @param button bouton de la souris appuyé
     */
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }
}
