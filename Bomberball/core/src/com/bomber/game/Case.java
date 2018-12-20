
package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Case extends Group {
    Map map;
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;
    private Personnage personnage;
    private boolean explo;
    private Porte porte;

    public Case() {
        this.setPosition((x)*Bomberball.taillecase,(y)*Bomberball.taillecase);
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setColor(1,0,0,1);
                return true;
            }
        });
        Image background=new Image(Bomberball.multiTexture[0]);
        background.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.addActor(background);


    }


    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public boolean isExplo() {
        return explo;
    }

    public void setExplo(boolean explo) {
        this.explo = explo;
    }

    public Bombe getBombe() {
        return bombe;
    }

    public void setBombe(Bombe bombe) {
        this.bombe = bombe;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Mur getMur() {
        return mur;
    }


    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public void setMur(Mur mur) {
        this.mur = mur;
        mur.setBounds(0,0,Bomberball.taillecase,Bomberball.taillecase);
        this.addActor(mur);
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
        this.addActor(personnage);
    }

    public int posY() {
        return y;
    }

    public void setposY(int y) { this.y = y;
        this.setY(y*Bomberball.taillecase);}

    public int posX() {
        return x;
    }

    public void setposX(int x) {
        this.x = x;
        this.setX(2*Bomberball.taillecase+x*Bomberball.taillecase);
    }



    public void explosionHaute(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
           this.setMur(null);
           this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x][y+1].explosionHaute(longueur-1);
            }
        }

    }

    public void explosionBasse(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x][y-1].explosionBasse(longueur-1);
            }
        }

    }

    public void explosionDroite(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x+1][y].explosionDroite(longueur-1);
            }
        }

    }

    public void explosionGauche(int longueur){
        if(this.personnage!=null){
            this.personnage.setVivant(false);
            this.explo=true;
        } else if (this.mur instanceof MurD){
            this.setMur(null);
            this.explo=true;
        } else if (this.mur instanceof MurI){
            //rien
        }else {
            this.explo=true;
            if (longueur>0){
                this.getMap().getGrille()[x-1][y].explosionGauche(longueur-1);
            }
        }
    }



    public void suppBombe(){
        this.bombe=null;
    }
    public void suppBonus(){
        this.bonus=null;
    }


}