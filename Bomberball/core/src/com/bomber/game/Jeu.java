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



    public Jeu (){//creation du jeu
      /*  this.addListener(new InputListener(){//on rajoute au jeu un des fonctions en cas d'input => voir tuto Inputs
            @Override
            public boolean keyDown(InputEvent event, int keycode) {//si une touche est pressée le return renvoi si la touche a été traitée => pas utile ici
                if(keycode==Input.Keys.ESCAPE && !(etat instanceof Solo) && !(etat instanceof Multijoueur)){
                        Gdx.app.exit();//si c'est escape on quitte le jeu
                    return true;
                }
                System.out.println(etat.toString());
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
*/



    }
    public void setEtat(Etat e){
        this.etat=e;
}

}
