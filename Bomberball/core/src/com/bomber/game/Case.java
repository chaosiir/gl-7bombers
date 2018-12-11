
package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Case {
    Map map;
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;
    private Personnage personnage;
    private Ennemis ennemi;
    private boolean explo;
    private Porte porte;

    public Case() {
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
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) { this.y = y; }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setEnnemi(Ennemis e){ this.ennemi=e;}

    public Ennemis getEnnemi(){ return ennemi; }


    public void afficher(Batch b, Texture[] multt) {
        Sprite s;

        if (mur == null) {
            s = new Sprite(multt[0]);


        } else {
            if (mur.destructible()) {
                s = new Sprite(multt[1]);
            } else {
                s = new Sprite( multt[2]);
            }
        }
        s.setPosition(x * 50 + 600, y * 50 + 100);
        s.setSize(50, 50);
        b.draw(s,s.getX(),s.getY(),0,0,s.getWidth(),s.getHeight(),s.getScaleX(),s.getScaleY(),0);
        if (porte != null) {
            porte.afficher(b, x, y,multt);
        }
        if (personnage != null) {
            personnage.afficher(b, x, y,multt);
        }
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