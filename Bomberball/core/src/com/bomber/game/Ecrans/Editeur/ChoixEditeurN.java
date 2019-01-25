package com.bomber.game.Ecrans.Editeur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.bomber.game.Bomberball;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.Jeu;

/**
 * Classe ChoixEditeur
 * Elle affiche l'écran pour choisir si le joueur veut éditer une map solo ou multijoueur
 * @author Paul-Louis Renard
 *
 */
public class ChoixEditeurN extends Etat implements Screen {
    private Image back;                 //Image de l'arrière-plan
    private Skin skin;                  //Caractéristiques des éléments graphiques
    private Table table;                //Contient les boutons
    private TextButton soloButton;      //Bouton pour choisir de faire une map solo
    private  TextButton multiButton;    //Bouton pour choisir de faire une map multi
    private TextButton retour;          //Bouton pour revenir au menu principal


    Bomberball game;

    /**
     * Constructeur de la fenêtre
     * @param game  La classe principal du jeu
     * @param jeu   Un jeu contenant les acteurs
     */
    public ChoixEditeurN(Bomberball game,Jeu jeu){
        super(jeu);
        this.game=game;
    }

    /**
     * Méthode appelée pour afficher la fenêtre
     * Elle est appelée à chaque affichage de l'écran
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        /** Mise en place de la table **/
        table=new Table();
        table.setWidth(Bomberball.stg.getWidth());
        table.align(Align.center | Align.top);
        table.setPosition(0, Gdx.graphics.getHeight());

        /*****************************************************************/

        soloButton = new TextButton("Creation d'une map solo",skin);
        multiButton = new TextButton("Creation d'une map multijoueur",skin);
        retour = new TextButton("Retour",skin);

        soloButton.addListener(new ClickListener(){                                 //Permet d'aller à l'éditeur solo
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.choixEditeurN.removeActor(back);
                game.choixEditeurN.removeActor(table);

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.choixEditeurN.removeActor(jeu);                                //Permet de supprimer les map qui pourraient existé en arrière-plan
                Bomberball.input.removeProcessor(game.choixEditeurN);
                jeu.setEtat(game.editeurNSolo);
                game.setScreen(game.editeurNSolo);
            }
        });
        multiButton.addListener(new ClickListener(){                                //Permet d'aller à l'éditeur multi
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.choixEditeurN.removeActor(back);
                game.choixEditeurN.removeActor(table);

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.choixEditeurN.removeActor(jeu);
                Bomberball.input.removeProcessor(game.choixEditeurN);
                jeu.setEtat(game.editeurNMulti);
                game.setScreen(game.editeurNMulti);
            }
        });
        retour.addListener(new ClickListener(){                                 //Permet de retourner au menu principal
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.setEtat(game.menuPrincipalBis);
                game.choixEditeurN.removeActor(table);
                game.choixEditeurN.removeActor(back);

                jeu.removeActor(jeu.map);
                jeu.map=null;
                game.choixEditeurN.removeActor(jeu);
                Bomberball.input.removeProcessor(game.choixEditeurN);
                game.setScreen(game.menuPrincipalBis);
            }
        });

        table.padTop(30);                                                      //Espace de 30 entre le premier texte et le haut

        table.add(soloButton).padBottom(30);                                  //Espace de 30 entre les 2 boutons
        table.row();                                                          //Retourne à la ligne

        table.add(multiButton).padBottom(30);
        table.row();
        table.add(retour);
        back.setName("Arrière plan: choix EditeurN");

        this.addActor(back);
        this.addActor(table);

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
        Bomberball.stg.clear();                                             //Permet d'enlever les acteurs de Bomberball

    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
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
    public boolean keyDown(int keycode) {
        return false;
    }
}
