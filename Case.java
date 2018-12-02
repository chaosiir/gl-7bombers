package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Case {
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;



    private Porte porte;
    private Personnage personnage;

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

    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }

    public void afficher(Batch b,Texture [] multt) {
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
}