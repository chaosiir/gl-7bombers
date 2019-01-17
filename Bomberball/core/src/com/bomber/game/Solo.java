package com.bomber.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe Solo
 * Elle affiche l'écran du jeu lors d'un jeu solo
 * @author Pascal Ferrari
 *
 */

public class Solo extends Etat implements Screen  {//etat multijoueur
    int pm=5;
    int nb=1;
    Image back;

    Image joueur;
    Image bombe;
    Image mouvement;
    Image pousse;
    Image explosion;

    Label nbmvt;
    Label nbBombe;
    Label porteExplo;
    Label poussee;

    TextButton retour;

    Personnage personnage;

    Skin skin;

    Image player;

    File f;
    File frecommencer;
    FileWriter fw;
    FileWriter fwr;

    private Bomberball bombaaaagh;
    public Solo(Bomberball bombaaaagh,Jeu jeu) {
        super(jeu);
        this.bombaaaagh=bombaaaagh;
        File directory = new File (".");
        try {
            f = new File(directory.getCanonicalPath() + "/SaveTempo/tmp.txt");
            frecommencer = new File(directory.getCanonicalPath() + "/SaveTempo/debut.txt");

        } catch (IOException e) {

        }

    }

    /**
     * Méthode appelée pour afficher la fenêtre
     */
    @Override
    public void show() {
        Bomberball.stg.addActor(this);
        Bomberball.stg.setKeyboardFocus(this);
        Bomberball.input.addProcessor(this);
        jeu.removeActor(jeu.map);
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        back= new Image(new Texture(Gdx.files.internal("backmain.png")) );
        back.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        if(f.exists()){
            jeu.map.suppActor();
            jeu.removeActor(jeu.map);
            jeu.map=null;
            if(jeu.recommencer){
                jeu.map=Map.mapFromStringN(Bomberball.loadFile(f));
                jeu.recommencer=false;
                f.delete();
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.mapFromStringP(Bomberball.loadFile(f),this.jeu);
                f.delete();
                pm=jeu.pmtmp1; //Remise à jour des valeurs de pm et du nb de bombes restantes
                jeu.pmtmp1=-1;
                nb=jeu.nbtmp1;
                jeu.nbtmp1=-1;
            }

        }
        else if(jeu.map==null){
            if(jeu.nbBonus!=-1){
                jeu.map=Map.genererMapSolo(65,10,jeu.nbBonus);
                jeu.nbBonus=-1;
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                jeu.map=Map.genererMapSolo(65,10,5);
                try {
                    fwr = new FileWriter(frecommencer);
                    fwr.write(jeu.map.mapToTextN());
                    fwr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            try {
                fwr = new FileWriter(frecommencer);
                fwr.write(jeu.map.mapToTextN());
                fwr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i=0;i<15;i++){
            for (int j=0;j<13;j++){
                if(jeu.map.getGrille()[i][j].getBonus()!=null){


                    Bonus b=jeu.map.getGrille()[i][j].getBonus();
                    jeu.map.getGrille()[i][j].setBonus(null);
                    jeu.map.getGrille()[i][j].setBonus(b);
                    jeu.map.getGrille()[i][j].getBonus().setScale(0.5f);
                }
            }
        }
        jeu.map.setPosition(Gdx.graphics.getWidth()-(jeu.map.getGrille().length+2f)*Bomberball.taillecase ,0);
        jeu.map.setScaleY(27f/26f);
        joueur=new Image(new Texture(Gdx.files.internal("Panneau_joueur.png")));
        joueur.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        joueur.setHeight(3*Bomberball.taillecase);
        joueur.setPosition(0,Gdx.graphics.getHeight()-3*Bomberball.taillecase);

        personnage=jeu.map.findActor("Personnage");




        mouvement = new Image(new Texture(Gdx.files.internal("Nombre_mouvement.png")));
        mouvement.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        mouvement.setHeight(2*Bomberball.taillecase);
        mouvement.setPosition(0,Gdx.graphics.getHeight()-5*Bomberball.taillecase);

        nbmvt= new Label(""+personnage.getPm(),skin);
        nbmvt.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-4*Bomberball.taillecase-20,nbmvt.getWidth(),nbmvt.getHeight()); //Positionnement à la main

        bombe=new Image(new Texture(Gdx.files.internal("Nombre_bombe.png")));
        bombe.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        bombe.setHeight(2*Bomberball.taillecase);
        bombe.setPosition(0,Gdx.graphics.getHeight()- 7*Bomberball.taillecase);

        nbBombe= new Label(""+personnage.getNbBombe(),skin);
        nbBombe.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-6*Bomberball.taillecase-20,nbBombe.getWidth(),nbBombe.getHeight()); //Positionnement à la main

        explosion=new Image(new Texture(Gdx.files.internal("Portée_bombe.png")));
        explosion.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        explosion.setHeight(2*Bomberball.taillecase);
        explosion.setPosition(0,Gdx.graphics.getHeight()-9*Bomberball.taillecase);

        porteExplo = new Label(""+personnage.getTaille(),skin);
        porteExplo.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-8*Bomberball.taillecase-20,porteExplo.getWidth(),porteExplo.getHeight()); //Positionnement à la main


        pousse=new Image(new Texture(Gdx.files.internal("icone_Bonus_pousser.png")));
        pousse.setWidth(jeu.map.getX()+2f*Bomberball.taillecase);
        pousse.setHeight(2*Bomberball.taillecase);
        pousse.setPosition(0,Gdx.graphics.getHeight()-11*Bomberball.taillecase);
        if(personnage.isPoussee()){
            poussee = new Label("X",skin);
        }
        else{
            poussee = new Label("",skin);
        }
        poussee.setBounds(Bomberball.taillecase*6+50,Gdx.graphics.getHeight()-10*Bomberball.taillecase-20,poussee.getWidth(),poussee.getHeight());


        player=new Image(Bomberball.multiTexture[4]);
        player.setBounds(6*Bomberball.taillecase,Gdx.graphics.getHeight()-2*Bomberball.taillecase,Bomberball.taillecase,Bomberball.taillecase);

        retour = new TextButton("Retour",skin);
        retour.setBounds(0,0,retour.getWidth(),retour.getHeight());







        retour.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jeu.removeActor(jeu.map);
                jeu.map=null;

                jeu.setEtat(bombaaaagh.menuSolo);
                bombaaaagh.setScreen(bombaaaagh.menuSolo);
            }
        });




        this.addActor(back);
        this.addActor(joueur);
        this.addActor(bombe);
        this.addActor(mouvement);
        this.addActor(explosion);
        this.addActor(pousse);
        this.addActor(player);
        this.addActor(nbmvt);
        this.addActor(nbBombe);
        this.addActor(porteExplo);
        this.addActor(poussee);
        this.addActor(retour);
        jeu.addActor(jeu.map);
        this.addActor(jeu);


    }

    @Override
    public boolean keyDown( int keycode) {//delpacement = fleche pas encore implementer
        Personnage joueur = jeu.map.findActor("Personnage");
        if(jeu.findActor("explo")==null) {

            if ((joueur != null) && (!joueur.hasActions())) {
                boolean b = false;
                if (keycode == Input.Keys.RIGHT) {
                    if (pm > 0) {
                        b = joueur.deplacerDroite();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);

                    }
                }

                if (keycode == Input.Keys.LEFT) {
                    if (pm > 0) {
                        b = joueur.deplacerGauche();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.DOWN) {
                    if (pm > 0) {
                        b = joueur.deplacerBas();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.UP) {
                    if (pm > 0) {
                        b = joueur.deplacerHaut();
                        pm = ((b) ? pm - 1 : pm);
                        this.removeActor(nbmvt);
                        nbmvt.setText(""+pm);
                        this.addActor(nbmvt);
                    }
                }
                if (keycode == Input.Keys.SPACE) {
                    if (nb > 0) {
                        b = joueur.poserBombe();
                        nb = ((b) ? nb - 1 : nb);
                        nbBombe.setText(""+nb);
                    }
                }
                if (keycode == Input.Keys.ENTER) {
                    jeu.map.explosion();
                    porteExplo.setText(""+personnage.getTaille());
                    if (joueur.isVivant()) {
                        jeu.map.tourEnnemi();
                        if (joueur.isVivant()) {
                            pm = joueur.getPm();
                            nb = joueur.getNbBombe();
                            nbBombe.setText("" + nb);
                            nbmvt.setText("" + pm);
                        }
                    }
                    if(!joueur.isVivant()) {

                        boolean ennemis = false;
                        for (int k = 0; k < jeu.map.getGrille().length; k++) {
                            for (int j = 0; j < jeu.map.getGrille()[0].length; j++) {
                                if (jeu.map.getGrille()[k][j].getEnnemi() != null) {
                                    ennemis = true;
                                    jeu.map.getGrille()[k][j].getEnnemi().setAnimationdefaite();
                                }
                            }
                        }
                        if (ennemis) {
                            jeu.addAction(new Action() {
                                float time = 0;

                                @Override
                                public boolean act(float delta) {
                                    time += delta;
                                    if (time > 4) {
                                        jeu.removeActor(jeu.map);
                                        jeu.map = null;
                                        bombaaaagh.defaite = new Defaite(bombaaaagh, jeu, "gdjdj");
                                        jeu.setEtat(bombaaaagh.defaite);
                                        bombaaaagh.setScreen(bombaaaagh.defaite);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                        } else {
                            jeu.addAction(new Action() {
                                float time = 0;

                                @Override
                                public boolean act(float delta) {
                                    time += delta;
                                    if (time > 3) {
                                        jeu.removeActor(jeu.map);
                                        jeu.map = null;
                                        bombaaaagh.defaite = new Defaite(bombaaaagh, jeu, "gdjdj");
                                        jeu.setEtat(bombaaaagh.defaite);
                                        bombaaaagh.setScreen(bombaaaagh.defaite);
                                        return true;
                                    }
                                    return false;
                                }
                            });
                        }
                    }
                    if(joueur.isPoussee()){
                        poussee.setText("X");
                    }
                    if(joueur.getC().getPorte()!=null){
                        jeu.removeActor(joueur);
                        jeu.removeActor(jeu.map);
                        jeu.map=null;
                        bombaaaagh.victoire = new Victoire(bombaaaagh, jeu, "                           Victoire");
                        jeu.setEtat(bombaaaagh.victoire);
                        bombaaaagh.setScreen(bombaaaagh.victoire);
                    }
                }
            }
        }
        if (keycode == Input.Keys.ESCAPE) {
            try {
                fw = new FileWriter(f);
                fw.write(jeu.map.mapToTextP());
                if(joueur.getC().getBombe()!=null){
                    fw.write(joueur.getC().posX()+" "+joueur.getC().posY()+" 19 "+joueur.getId()+" "+pm+" "+nb+" "+joueur.getPm()+" "+joueur.isVivant()+" "+joueur.getTaille()+" "+joueur.getNbBombe()+" "+joueur.isPoussee()+"\n");
                }
                else{
                    fw.write(joueur.getC().posX()+" "+joueur.getC().posY()+" 1212 "+" "+joueur.getId()+" "+pm+" "+nb+" "+joueur.getPm()+" "+joueur.isVivant()+" "+joueur.getTaille()+" "+joueur.getNbBombe()+" "+joueur.isPoussee()+"\n");
                }

                fw.write(joueur.getC().posX()+" "+joueur.getC().posY()+" 9999 "+joueur.getId()+"\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bombaaaagh.menuPause.setEtatAnterieur(this);
            jeu.setEtat(bombaaaagh.menuPause);
            bombaaaagh.setScreen(bombaaaagh.menuPause);
        }


        return true;
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
        Bomberball.stg.clear();
        jeu.removeActor(jeu.map);
        Bomberball.input.removeProcessor(this);

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
