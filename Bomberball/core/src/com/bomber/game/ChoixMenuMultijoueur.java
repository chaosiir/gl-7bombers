package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
/**
 * Classe ChoixMenuMultijoueur
 * Elle affiche les paramètres que le joueur peut choisir pour lancer une partie multijoueur et le moyen de lancer cette partie
 * @author Paul-Louis Renard
 *
 */
public class ChoixMenuMultijoueur extends Etat implements Screen {


    Bomberball game;
    private Skin skin;
    private Image back;
    private Table table;
    private Label nbjoueur;
    private Label nbBonus;
    private Label nbBlocD;
    private Label porteeBombe;
    private Label nbDeplace;
    private Label nbBombe;

    private TextButton retour;
    private TextButton deux;
    private TextButton trois;
    private TextButton quatre;
    private TextButton choixmap;
    private TextButton lancerP;

    private ButtonGroup<TextButton> nbJ;

    private TextField nbBonusS;
    private TextField porteeBombeS;
    private TextField nbBlocDT;
    private TextField nbDeplac;
    private TextField nbBombeT;

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

        /*Creation du bouton de lancement de la partie*/
        lancerP.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Quitte le menu multijoueur
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);

                //Recupere les parametres changes par le joueur
                try{
                    int nbBD=Integer.parseInt(nbBlocDT.getText());
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




                //Passage au mode multijoueur
                jeu.setEtat(game.multijoueur);
                game.setScreen(game.multijoueur);
            }
        });

        /*Creation du bouton de retour au menu principal*/
        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Quitte le menu multijoueur
                game.choixMenuMultijoueur.removeActor(jeu.findActor("Map"));
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.choixMenuMultijoueur.removeActor(jeu);

                jeu.nbJoueur=4;

                //Passage au menu principal
                jeu.setEtat(game.menuPrincipalBis);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        /*Creation du bouton d'acces au menu de choix des maps multijoueurs*/
        choixmap.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Quitte le menu multijoueur
                game.choixMenuMultijoueur.removeActor(back);
                game.choixMenuMultijoueur.removeActor(table);


                jeu.removeActor(jeu.map);
                game.choixMenuMultijoueur.removeActor(jeu);

                //Passage au menu de selection des maps multijoueurs
                jeu.setEtat(game.choixMapMultiJ);
                game.setScreen(game.choixMapMultiJ);
            }
        });

        /*Creation des boutons de selection du nombre de joueur*/
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

        /*Creation du menu de selection des parametres*/
        table=new Table(); //Tableau
        //table.setDebug(true);
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.topLeft);
        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(nbjoueur).padBottom(30);
        HorizontalGroup h=new HorizontalGroup();
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
