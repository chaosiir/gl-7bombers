package grille;

public class Personnage {

    private boolean vivant;
    private Case c;
    private int taille;

    public Personnage(boolean vivant, Case c, int taille) {
        this.vivant = vivant;
        this.c = c;
        this.taille = taille;
    }

    public Case getC() {
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }

    public boolean isVivant() {
        return vivant;
    }

    public void setVivant(boolean vivant) {
        this.vivant = vivant;
    }

    public void poserBombe(){
        Bombe b=new Bombe(taille,this,c);
        c.setBombe(b);
    }

    public void deplacerHaut(){
        if (c.getMap().getGrille()[c.getX()][c.getY()+1].getMur()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()+1].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()+1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()][c.getY()+1]);
            c.setPersonnage(null);
            c=tmp;

        }
    }

    public void deplacerBas(){
        if (c.getMap().getGrille()[c.getX()][c.getY()-1].getMur()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()-1].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()][c.getY()-1].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()][c.getY()-1]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()-1].setPersonnage(this);
            c=tmp;

        }
    }

    public void deplacerDroite(){
        if (c.getMap().getGrille()[c.getX()][c.getY()].getMur()==null &&
                c.getMap().getGrille()[c.getX()+1][c.getY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()+1][c.getY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()+1][c.getY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()+1][c.getY()].setPersonnage(this);
            c=tmp;

        }
    }

    public void deplacerGauche(){
        if (c.getMap().getGrille()[c.getX()][c.getY()].getMur()==null &&
                c.getMap().getGrille()[c.getX()-1][c.getY()].getPersonnage()==null &&
                c.getMap().getGrille()[c.getX()-1][c.getY()].getBombe()==null){
            Case tmp = (c.getMap().getGrille()[c.getX()-1][c.getY()]);
            c.setPersonnage(null);
            c.getMap().getGrille()[c.getX()][c.getY()].setPersonnage(null);
            c.getMap().getGrille()[c.getX()-1][c.getY()].setPersonnage(this);
            c=tmp;

        }
    }



}
