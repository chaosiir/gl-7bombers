
package com.bomber.game;
public class Case {
    Map map;
    private int x;
    private int y;
    private Bombe bombe;
    private Bonus bonus;
    private Mur mur;
    private Personnage personnage;
    private boolean explo;

    public Case(Map map, int x, int y, Bombe bombe, Bonus bonus, Mur mur, Personnage personnage, boolean explo) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.bombe = bombe;
        this.bonus = bonus;
        this.mur = mur;
        this.personnage = personnage;
        this.explo = explo;
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