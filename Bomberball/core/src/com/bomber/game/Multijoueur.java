package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Multijoueur extends Etat implements Screen {//etat multijoueur
    int pm;
    int nb;
    private Bomberball game;
    private Personnage joueurs[]=new Personnage[4];
    private int tour=0;

    public Multijoueur(Bomberball game,Jeu jeu) {
        super(jeu);
        this.game=game;

    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = joueurs[tour];
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
                    tour=(tour+1)%4;
                    int nbviv=0;
                    int viv=0;
                    for(int i=0;i<4;i++){
                        if(joueurs[i].isVivant()){
                            nbviv++;
                            viv=i;
                        }
                    }
                    if(nbviv==0){
                        jeu.map=null;
                        jeu.removeActor(jeu.findActor("Map"));

                        for(int i=0;i<4;i++){
                            jeu.removeActor(joueurs[i]);
                        }

                        game.victoire=new Victoire(game,jeu,"Match nul");
                        jeu.setEtat(game.victoire);
                        game.setScreen(game.victoire);


                    }
                    else if(nbviv==1){
                        jeu.map=null;
                        jeu.removeActor(jeu.findActor("Map"));
                        game.victoire=new Victoire(game,jeu,"Victoire joueur "+(viv+1));
                        for(int i=0;i<4;i++){
                            jeu.removeActor(joueurs[i]);
                        }
                        jeu.setEtat(game.victoire);
                        game.setScreen(game.victoire);
                    }
                    else {
                        while (!joueurs[tour].isVivant()) {
                            tour = (tour + 1) % 4;
                        }
                        pm = joueurs[tour].getPm();
                        nb = joueurs[tour].getNbBombe();
                    }



                }
            }
        }


        return true;
    }
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {//test de fonction clic

        return true;
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        return false;
    }


    @Override
    public void show() {
        if(jeu.map==null){
            if(jeu.nbBonus!=-1){
                jeu.map=Map.generatePvp(65, jeu.nbBonus);
                jeu.nbBonus=-1;
            }
            else{
                jeu.map=Map.generatePvp(65,5);
            }
        }
        int a=0;
        for(int i=0;i<jeu.map.getGrille().length;i++){
            for (int j=0;j<jeu.map.getGrille()[1].length;j++){
                Personnage p=jeu.map.getGrille()[i][j].getPersonnage();
                if(p!=null){
                    joueurs[a]=p;
                    a++;
                }
            }
        }
        pm=5;
        nb=1;
        jeu.addActor(jeu.map);
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

    }

    @Override
    public void dispose() {

    }
}
