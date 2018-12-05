package grille;
import java.util.Random;
import java.math.*;

public class Map {
    private Case[][] grille;
    private int x;      //dimensions de la map, typiquement 15x13
    private int y;
    private boolean solomulti;

    public Case[][] getGrille() {return grille; }
    public void setGrille(Case[][] grille) {this.grille = grille;}
    public int getX() {return x; }
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) { this.y = y;}
    public boolean isSolomulti() {return solomulti;}
    public void setSolomulti(boolean solomulti) {this.solomulti = solomulti;}


    //génération de la map PvP de base
    //Renvoie un tableau de case de taille 13x15 avec le pourtour
    public Case[][] generatePvp(int nbDestru){
        int i;
        int j;
        int cpt=0;//compteur de cases potentiellment destructibles, spoiler il y en a 93
        int random;
        if (nbDestru>93){
            nbDestru=93;
        }
        Case[][] g=new Case[16][14];                            //ce qu'on va renvoyer, le tableau de case
        Case[] caseDes = new Case[1000];                        //repertorie les cases potentiellment destructibles dans un  tableau

        for (i=1;i<=15;i++){                                      //on parcourt toutes les cases de la map
            for (j=1;j<=13;j++){
                g[i][j]=new Case(this,i,j,null,null,null,null,false);//création d'une nouvelle case à la postion i,j
                g[i][j].setX(i);
                g[i][j].setY(j);
                if (i==1 || i==15 || j==1 || j==13 || (j%2!=0 && i%2!=0)) { //si la case fait partie des case indestructibles
                    Mur m = new MurI();                                     //on crée un mur indestructible et on le met dans la case
                    g[i][j].setMur(m);
                } else if ((i==2 && (j==4||j==10))||(i==4 && (j==2 || j==12))||(i==12 &&(j==2 || j==12))||(i==14 && (j==4||j==10))){
                    Mur m = new MurD();                  //mise en place des cases destructibles obligatoires autour de zones de départ
                    g[i][j].setMur(m);
                } else if(!((i<=3 && j<=3) || (i>=13 && j<=3) || (i<=3 && j>=11) || (i>=13 && j>=11))){
                    caseDes[cpt]=g[i][j];               //pour toutes les autres cases sauf celles de la zone de départ
                    cpt++;                              //on ajoute la case de coordonnées i,j à la liste des cases potentiellement destru
                }
            }
        }
        int a;
        int b;
        System.out.println(cpt);
        for(i=0;i<nbDestru;i++){
            random = (int)(Math.random() * cpt);
            a=caseDes[random].getX();
            b=caseDes[random].getY();
            Mur m = new MurD();
            g[a][b].setMur(m);
            caseDes[random]=caseDes[cpt-1];
            cpt --;
        }
        return g;
    }

    // 1 : indestructible
    // 2 : entree/sortie
    // 0 : libre

    int [][] traducteur(){//map.traducteur
        int [][] tableau=new int [16][14];
        int i;
        int j;
        for (i=1;i<=15;i++){
            for (j=1;j<=13;j++){
                if (this.grille[i][j].getMur() instanceof MurI){
                    //(MurI)this.grille[i][j].getMur().fdh();
                    tableau[i][j]=2;
                }else if (this.grille[i][j].getMur() instanceof MurD) {
                    tableau[i][j] = 1;
                } else if (this.grille[i][j].getPersonnage()!=null ) {
                    if (this.grille[i][j].getPersonnage().isVivant()) {
                        tableau[i][j] = 3;
                    }else{
                        tableau[i][j] = 5;
                    }

                } else if (this.grille[i][j].getBombe()!=null) {
                    tableau[i][j] = 4;
                } else {
                    tableau[i][j]=0;
                }
            }
        }
        return tableau;

    }

    public static void main(String [] args){
        Map m= new Map();
        Case[][] g = new Case [16][14];
        m.setGrille(g);
        m.setGrille(m.generatePvp(60));
        //m.setGrille(g);
        Personnage p= new Personnage(true,m.getGrille()[2][2],3);
        Bombe b = new Bombe(3,p,m.getGrille()[2][3]);
        (m.getGrille())[2][3].setBombe(b);
        (m.getGrille())[2][2].setPersonnage(p);
        int [][] traduit = new int[16][14];
        traduit = m.traducteur();
        int i;
        int j;
        for (i=1;i<=15;i++){
            System.out.println(""+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                    traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]);
        }
        b.explosion();
        p.deplacerDroite();
        b.explosion();
        traduit = m.traducteur();
        System.out.println(" ");
        for (i=1;i<=15;i++){
            System.out.println(""+traduit[i][1]+" "+traduit[i][2]+" "+traduit[i][3]+" "+traduit[i][4]+" "+traduit[i][5]+" "+traduit[i][6]+" "+
                    traduit[i][7]+" "+traduit[i][8]+" "+traduit[i][9]+" "+traduit[i][10]+" "+traduit[i][11]+" "+traduit[i][12]+" "+traduit[i][13]);
        }

        /*for (i=1;i<=10;i++){
            int randomNum = 1 + (int)(Math.random() * 3);
            j=randomNum;

        }*/

    }
}