package com.bomber.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Jeu extends Group {//le jeu est un group d'acteur il manque aussi les menus comme acteur/layer
    Map map;//la map du jeu
    Etat etat;//etat du jeu c'est lui qui prend les inputs et fait l'affichage
    int nbBonus=-1;
    int nbEnnemis=-1;
    int porteeBombe=-1;
    int nbDeplaEnnemis=-1;
    int nbBombe=-1;
    int pmtmp1=-1;
    int pmtmp2=-1;
    int pmtmp3=-1;
    int pmtmp4=-1;

    int nbtmp1=-1;
    int nbtmp2=-1;
    int nbtmp3=-1;
    int nbtmp4=-1;

    boolean recommencer=false;

    boolean poussee1=false;
    boolean poussee2=false;
    boolean poussee3=false;
    boolean poussee4=false;


    public Jeu (){//creation du jeu
        this.addListener(new InputListener(){//on rajoute au jeu un des fonctions en cas d'input => voir tuto Inputs
            @Override
            public boolean keyDown(InputEvent event, int keycode) {//si une touche est pressée le return renvoi si la touche a été traitée => pas utile ici
                if(keycode==Input.Keys.ESCAPE && !(etat instanceof Solo) && !(etat instanceof Multijoueur)){
                        Gdx.app.exit();//si c'est escape on quitte le jeu
                }
                return etat.keyDown( event, keycode);//sinon on l'envoi à l'etat
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {//meme chose en cas de clic

                return etat.touchDown( event,  x,  y,  pointer,  button);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return etat.mouseMoved(event,x,y);
            }
        });




       /* map=Map.genererMapSolo(60,10);//on creer la map et on l'ajoute en tant qu'acteur !!!! à terme faire une fonction creer map dans
        //etat car on ne sera pas toujours en multi ou avec une map prete
        this.addActor(map);
        int i;
        int j;
        for (i=0;i<map.tailleX();i++){
            for(j=0;j<map.tailleY();j++){
                map.addActor(map.getGrille()[i][j]);//ajout des case en acteur meme chose => dans creer map
            }
        }*/
    }
    public void setEtat(Etat e){
        this.etat=e;
}

}
