package com.bomber.game.Ecrans.Multi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;

/**
 * Classe ChoixMenuMultijoueur
 * Elle affiche les paramètres que le joueur peut choisir pour lancer une partie multijoueur et le moyen de lancer cette partie
 *
 */
public class ChoixMenuMultijoueur extends Etat implements Screen {


    Bomberball game;                    //Instance de la classe principale
    private Skin skin;                  //Caractéristiques des éléments graphiques
    private Image back;                 //Image de l'arrière-plan
    private Table table;                //Table pour organiser l'écran
    private Label nbjoueur;             //Texte demandant un nombre de joueur
    private Label nbBonus;              //Texte demandant un nombre de bonus
    private Label nbBlocD;              //Texte demandant un nombre de bloc destructibles
    private Label porteeBombe;          //Texte demandant la portee d'une bombe
    private Label nbDeplace;            //Texte demandant un nombre de déplacement
    private Label nbBombe;              //Texte demandant un nombre de bombe

    private TextButton retour;          //Bouton pour retourner au menu principla
    private TextButton deux;            //Bouton pour jouer à 2
    private TextButton trois;           //Bouton pour jouer à 3
    private TextButton quatre;          //Bouton pour jouer à 4
    private TextButton choixmap;        //Bouton pour amener au choix de la map multijoueur
    private TextButton lancerP;         //Bouton pour lancer une partie

    private ButtonGroup<TextButton> nbJ;//Groupe les boutons 2,3,4 pour qu'il y en ait que un de sélectionné

    private TextField nbBonusS;         //Récupère le nombre de bonus
    private TextField porteeBombeS;     //Récupère la portée de la bombe
    private TextField nbBlocDT;         //Récupère le nombre de bloc destructible
    private TextField nbDeplac;         //Récupère le nombre de déplacement
    private TextField nbBombeT;         //Récupère le nombre de bombe

    public ChoixMenuMultijoueur(Bomberball game, Jeu jeu){
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
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        nbjoueur=new Label("Nombre de joueurs :",skin);
        nbBonus=new Label("Nombre de bonus :",skin);

        porteeBombe= new Label("Portee d'une bombe :",skin);

        nbBlocD=new Label("Nombre de bloc destructible :",skin);
        nbDeplace = new Label("Nombre de deplacement :",skin);
        nbBombe=new Label("Nombre de bombe :",skin);

        nbJ=new ButtonGroup<TextButton>();


        retour= new TextButton("Retour",skin);
        deux= new TextButton("2",skin,"toggle");
        trois = new TextButton("3",skin,"toggle");
        quatre= new TextButton("4",skin,"toggle");

        nbJ.add(quatre);
        nbJ.add(trois);
        nbJ.add(deux);

        choixmap = new TextButton("Choix de la map",skin);
        lancerP = new TextButton("Lancer la partie",skin);


        nbBonusS= new TextField("12",skin);
        porteeBombeS=  new TextField("2",skin);
        nbBlocDT = new TextField("40",skin);
        nbDeplac = new TextField("5",skin);
        nbBombeT= new TextField("1",skin);

        lancerP.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);

                try{
                    int nbBD=Integer.parseInt(nbBlocDT.getText());      //On récupère les paramètres que le joueur rentre
                    if(nbBD>=0){
                        jeu.nbBlocD=nbBD;
                    }
                }
                catch (NumberFormatException e){
                }

                try{
                    int nbB=Integer.parseInt(nbBonusS.getText());
                    if(nbB>=0){
                        jeu.nbBonus=nbB;
                    }
                }
                catch (NumberFormatException e){
                }
                try{
                    int nbP=Integer.parseInt(porteeBombeS.getText());
                    if(nbP>=0){
                        jeu.porteeBombe=nbP;
                    }
                }
                catch (NumberFormatException e){
                }
                try{
                    int nbD=Integer.parseInt(nbDeplac.getText());
                    if(nbD>=1){
                        jeu.nbDeplaP=nbD;
                    }
                }
                catch (NumberFormatException e){}
                try{
                    int nbBo=Integer.parseInt(nbBombeT.getText());
                    if(nbBo>=1){
                        jeu.nbBombe=nbBo;
                    }
                }
                catch (NumberFormatException e){}

                game.choixMenuMultijoueur.removeActor(jeu);

                jeu.setEtat(game.multijoueur);
                game.setScreen(game.multijoueur);
            }
        });


        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.choixMenuMultijoueur.removeActor(jeu.findActor("Map"));
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.choixMenuMultijoueur.removeActor(jeu);

                jeu.nbJoueur=4;

                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        choixmap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);


                jeu.removeActor(jeu.map);
                game.choixMenuMultijoueur.removeActor(jeu);

                jeu.setEtat(game.choixMapMultiJ);
                game.setScreen(game.choixMapMultiJ);
            }
        });

        deux.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.nbJoueur=2;
            }
        });

        trois.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.nbJoueur=3;
            }
        });

        quatre.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.nbJoueur=4;
            }
        });


        table=new Table(); //Tableau
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(nbjoueur).padBottom(30);
        HorizontalGroup h=new HorizontalGroup();                                            //Permet de bien aligner les colonnes
        h.space(10);
        h.addActor(quatre);
        h.addActor(trois);
        h.addActor(deux);
        table.add(h);
        table.row();
        table.add(nbBonus).padBottom(30);
        table.add(nbBonusS);
        table.row();
        table.row();
        table.add(porteeBombe).padBottom(30);
        table.add(porteeBombeS);
        table.row();
        table.add(nbBombe).padBottom(30);
        table.add(nbBombeT);
        table.row();
        table.add(nbBlocD).padBottom(30);
        table.add(nbBlocDT);
        table.row();
        table.add(nbDeplace).padBottom(30);
        table.add(nbDeplac);
        table.row();
        table.add(choixmap).padBottom(30);
        table.row();
        table.add(lancerP).padBottom(30);
        table.row();
        table.add(retour).padBottom(30);

        back.setName("Arrière plan: menu multijoueur principal");

        this.addActor(back);
        this.addActor(table);


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
        Bomberball.stg.clear();

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
    public boolean mouseMoved( int x, int y) {
        return false;
    }
}
