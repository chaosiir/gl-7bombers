package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.*;

public class Solo extends Etat implements Screen {//etat multijoueur
    int pm=5;
    int nb=1;
    Image back;

    Image joueur;
    Image bombe;
    Image mouvement;
    Image pousse;
    Image explosion;
    private Bomberball bombaaaagh;
    public Solo(Bomberball bombaaaagh,Jeu jeu) {
        super(jeu);
        this.bombaaaagh=bombaaaagh;

    }

    @Override
    /**
     * Indique la reaction lors de l'appui d'une touche en mode solo (deplacement, depot de bombe, fin de tour)
     * @param event evenement traité
     * @param keycode code de la touche appuyée
     */
    public boolean keyDown(InputEvent event, int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = jeu.map.findActor("Personnage");
        if(jeu.findActor("explo")==null) {
            if ((joueur != null) && (!joueur.hasActions())) {
                boolean b = false;
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                    }
                }
                if (keycode == Input.Keys.ENTER) {

                    jeu.map.explosion();
                    jeu.map.tourEnnemi();
                    if (joueur.isVivant()) {
                        pm = joueur.getPm();
                        nb = joueur.getNbBombe();
                    } else {
                       joueur.getC().removeActor(joueur);
                    }
                    if(joueur.getC().getPorte()!=null){
                        jeu.removeActor(joueur);
                        jeu.map=null;
                        jeu.removeActor(jeu.map);
                        jeu.setEtat(bombaaaagh.menuPrincipalBis);
                        bombaaaagh.setScreen(bombaaaagh.menuPrincipalBis);
                    }
                }
            }
        }


        return true;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {//test de fonction clic
      /*  Vector2 cord = jeu.getStage().screenToStageCoordinates(new Vector2(x,Gdx.graphics.getHeight()-y));//test vecteur mais marche pas
        Actor a=jeu.hit(cord.x,cord.y,true);//hit recupere l'acteur à la position x,y
            if(a!=null) {//si il y en a un
                a.setVisible(false);// on le rend invisible (pour le test)
            }*/
        return true;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }

    @Override
    public void show() {
        pm=5;
        nb=1;
        int ymax= Toolkit.getDefaultToolkit().getScreenSize().height;
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());





        if(jeu.map==null){
            if(jeu.nbBonus!=-1){
                jeu.map=Map.genererMapSolo(65,10,jeu.nbBonus);
                jeu.nbBonus=-1;
            }
            else{
                jeu.map=Map.genererMapSolo(65,10,5);
            }

        }
        jeu.map.setPosition(Gdx.graphics.getWidth()-(jeu.map.getGrille().length+2f)*Bomberball.taillecase ,0);
        jeu.map.setScaleY(27f/26f);
        joueur=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur.setHeight(3*Bomberball.taillecase);
        joueur.setPosition(0,Gdx.graphics.getHeight()-joueur.getHeight());

        mouvement = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement.setHeight(3*Bomberball.taillecase);
        mouvement.setPosition(0,Gdx.graphics.getHeight()-joueur.getHeight()-mouvement.getHeight());

        bombe=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe.setHeight(3*Bomberball.taillecase);
        bombe.setPosition(0,Gdx.graphics.getHeight()-joueur.getHeight()-bombe.getHeight()-mouvement.getHeight());

        explosion=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion.setHeight(3*Bomberball.taillecase);
        explosion.setPosition(0,Gdx.graphics.getHeight()-joueur.getHeight()-bombe.getHeight()-mouvement.getHeight()-explosion.getHeight());



        jeu.addActor(back);
        jeu.addActor(joueur);
        jeu.addActor(bombe);
        jeu.addActor(mouvement);
        jeu.addActor(explosion);
        jeu.addActor(jeu.map);

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
