package grille;

public class Bombe {
    int taille; //taille de l'explosion
    Personnage personnage;
    Case c;

    public Bombe(int taille, Personnage personnage, Case c) {
        this.taille = taille;
        this.personnage = personnage;
        this.c = c;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public Case getC() {
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }


    public void explosion(){
        this.c.explosionHaute(taille);
        this.c.explosionBasse(taille);
        this.c.explosionDroite(taille);
        this.c.explosionGauche(taille);
    }
}