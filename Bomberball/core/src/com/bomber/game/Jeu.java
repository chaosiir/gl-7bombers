package com.bomber.game;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.bomber.game.Ecrans.Etat;
import com.bomber.game.MapetObjet.Map;

/**
 * Le jeu est un group d'acteur. Il contient la map et quelques informations supplémentaires telles que la difficulté, le nombre d'ennemis,etc.
 */
public class Jeu extends Group {//le jeu est un group d'acteur il manque aussi les menus comme acteur/layer
    public Map map;             //la map du jeu
    Etat etat;                  //etat du jeu c'est lui qui prend les inputs et fait l'affichage
    public int nbBonus=-1;      //stocke le nombre de bonus
    public int nbBlocD=-1;      //stocke le nombre de blocs destructibles
    public int nbDeplaP=-1;     //stocke le nombre de déplacements d'un joueur
    public int difficulte=-1;   //stocke la difficulté

    public int nbEnnemis=-1;    //stocke le nombre d'ennemis
    public int porteeBombe=-1;  //stocke la portée d'une bombe
    public int nbBombe=-1;      //stocke le nombre de bombes

    public int pmtmp1=-1;       //stocke le nombre de points de mouvements par joueur lors de l'appel d'un menu pause
    public int pmtmp2=-1;
    public int pmtmp3=-1;
    public int pmtmp4=-1;

    public int nbtmp1=-1;       //stocke le nombre de bombes par joueur lors de l'appel d'un menu pause
    public int nbtmp2=-1;
    public int nbtmp3=-1;
    public int nbtmp4=-1;

    public int nbJoueur=4;      //stocke le nombre de joueurs pour une map multi

    public boolean recommencer=false;   //permet de recommencer

    public boolean poussee1=false;      //stocke l'autorisation de pousser une bombe pour chaque joueur
    public boolean poussee2=false;
    public boolean poussee3=false;
    public boolean poussee4=false;


    /**
     * Constructeur de Jeu. Par la suite, on accède en public à toutes les variables de Jeu.
     */
    public Jeu (){

    }
    /**
     * Met à jour l'état du jeu.
     *
     * @param e : etat du jeu c'est lui qui prend les inputs et fait l'affichage
     */
    public void setEtat(Etat e){
        this.etat=e;
}

}
