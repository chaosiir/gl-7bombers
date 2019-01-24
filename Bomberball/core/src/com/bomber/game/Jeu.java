package com.bomber.game;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.MapetObjet.Map;


public class Jeu extends Group {//le jeu est un group d'acteur il manque aussi les menus comme acteur/layer
    public Map map;//la map du jeu
    Etat etat;//etat du jeu c'est lui qui prend les inputs et fait l'affichage
    public int nbBonus=-1;
    public int nbBlocD=-1;
    public int nbDeplaP=-1;
    public int difficulte=-1;

    public int nbEnnemis=-1;
    public int porteeBombe=-1;
    public int nbBombe=-1;

    public int pmtmp1=-1;
    public int pmtmp2=-1;
    public int pmtmp3=-1;
    public int pmtmp4=-1;

    public int nbtmp1=-1;
    public int nbtmp2=-1;
    public int nbtmp3=-1;
    public int nbtmp4=-1;

    public int nbJoueur=4;

    public boolean recommencer=false;

    public boolean poussee1=false;
    public boolean poussee2=false;
    public boolean poussee3=false;
    public boolean poussee4=false;



    public Jeu (){

    }
    public void setEtat(Etat e){
        this.etat=e;
}

}
