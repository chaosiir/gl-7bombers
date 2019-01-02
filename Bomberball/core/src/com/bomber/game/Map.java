
package com.bomber.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Map {
	private int mat[][];
	private Case[][] grille;
	private int x;      //dimensions de la map, typiquement 15x13
	private int y;
	private boolean solomulti;

	public Map(){
		super();
		grille=new Case[13][15];
	}
	
	public Map(Case g[][]) {
		super();
		grille=g;
	}
	
	public int[][] mat(int lignes,int colonnes){
		int t[][]=new int[lignes][colonnes];
		int x,y;
		int compteur1=0;
		int compteur2=0;
		while(compteur1!=36) {
			x=(int)(Math.random()*lignes);
			y=(int)(Math.random()*colonnes);
			if(t[x][y]==0) {
				t[x][y]=1;
				compteur1++;
			}
		}
		while(compteur2!=2) {
			x=(int)(Math.random()*lignes);
			y=(int)(Math.random()*colonnes);
			if(t[x][y]==0) {
				t[x][y]=2;
				compteur2++;
			}
		}
		return t;
	}
	
	public static void main(String args[]) {
		Map m=new Map();
		int t[][]=m.mat(13,15);
		for (int i=0;i<13;i++) {
			for(int j=0;j<15;j++) {
				System.out.print(" "+t[i][j]);
			}
			System.out.println();
		}
		boolean b=m.verifSolo(t);
		if(b) {
			System.out.println("La carte est valide");
		}
		else {
			System.out.println("La carte n'est pas valide");
		}
        /*for (i=1;i<=10;i++){
            int randomNum = 1 + (int)(Math.random() * 3);
            j=randomNum;

        }*/
		
	}
	


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
	        int i;			// indice de ligne
	        int j;			// indice de colonne
	        int cpt = 0;		//compteur de cases potentiellment destructibles, spoiler il y en a 93
	        int random;
	        if (nbDestru>93){
	            nbDestru=93;
	        }

	        Case[][] g = new Case[14][16];                          //ce qu'on va renvoyer, le tableau de case
	        Case[] caseDes = new Case[1000];                        //repertorie les cases potentiellment destructibles dans un  tableau

	        for (i = 0;i < 13;i++){                                 //on parcourt toutes les cases de la map
	            for (j = 0;j < 15;j++){
	                g[i][j] = new Case();							//création d'une nouvelle case à la postion i,j
	                g[i][j].setX(i);
	                g[i][j].setY(j);
	                g[i][j].setMap(this);
	                if (j==0 || j==14 || i==0 || i==12 || (j%2==0 && i%2==0)) { //si la case fait partie des case indestructibles
	                    Mur m = new MurI();                                     //on crée un mur indestructible et on le met dans la case
	                    g[i][j].setMur(m);
	                } else if ((i==1 && (j==3||j==11))||(i==3 && (j==1 || j==13))||(i==9 &&(j==1 || j==13))||(i==11 && (j==3||j==11))){
	                    Mur m = new MurD();                  //mise en place des cases destructibles obligatoires autour de zones de départ
	                    g[i][j].setMur(m);
	                } else if(!((i<=2 && j<=2) || (i>=10 && j<=2) || (i<=2 && j>=12) || (i>=10 && j>=12))){
	                    caseDes[cpt]=g[i][j];               //pour toutes les autres cases sauf celles de la zone de départ
	                    cpt++;                              //on ajoute la case de coordonnées i,j à la liste des cases potentiellement destru
	                }
					//if( (j==1 || j==13) && (i==1 || i==11)){g[i][j].setPersonnage(new Personnage(true,g[i][j],3,2,5));}
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
	        /*Map m=new Map();
			this.setX(13);
			this.setY(15);
	        this.grille=g;*/
	        //return m;
	    }

	    // 1 : indestructible
	    // 2 : entree/sortie
	    // 0 : libre

	public void explosion(){ //explose toutes les bombes de la map
		int i;
		int j;
		for (i=0;i<13;i++) {
			for (j = 0; j < 15; j++) {
				if (this.getGrille()[i][j].getBombe()!=null) {
					this.getGrille()[i][j].getBombe().explosion();
				}
			}
		}
	}

	public void clean() {//enlève les personnage morts
		int i;
		int j;
		for (i = 0; i < 13; i++) {
			for (j = 0; j < 15; j++) {
				if (this.getGrille()[i][j].getPersonnage() != null) {
					if (!this.getGrille()[i][j].getPersonnage().isVivant()) {
						this.getGrille()[i][j].setPersonnage(null);
					}
				}
			}
		}
	}

	public boolean verifSolo(int t[][]) { //Vérifie qu'une map solo est valide (convention 1=mur indestructible 2=départ/arrivée 0=libre);
		int lignes=t.length;
		int colonnes=t[0].length;
		int xd=-1,yd=-1,xa=-1,ya=-1;
		int i=0;
		int j=0;
		int compteur=0;
		//On cherche le départ et l'arrivée
		for(i=0;i<lignes;i++) {
			for(j=0;j<colonnes;j++) {
				if (t[i][j]==2) {
					if (xd==-1) {
						xd=i;
						yd=j;
					}
					else {
						xa=i;
						ya=j;
					}
				}

			}


		}

		int tmp[][]=new int[lignes*colonnes][lignes*colonnes];
		int exist[][]=new int[lignes*colonnes][lignes*colonnes]; //On prépare la matrice d'existence de lien (numéroté dans le sens de la gauche vers la droite et on retourne à chaque ligne) Ainsi t[i,j]=j+11*i
		for (i=0;i<lignes;i++) {
			for(j=0;j<colonnes;j++) {
				if (j>0 && j<colonnes-1) {
					if (t[i][j]!=1 && t[i][j-1]!=1) {
						exist[j+colonnes*i][j-1+colonnes*i]=1;
						exist[j-1+colonnes*i][j+colonnes*i]=1;
					}
					if (t[i][j]!=1 && t[i][j+1]!=-1) {
						exist[j+colonnes*i][j+1+colonnes*i]=1;
						exist[j+1+colonnes*i][j+colonnes*i]=1;
					}
				}
				if (i>0 && i<lignes-1) {
					if (t[i][j]!=1 && t[i-1][j]!=1) {
						exist[j+colonnes*i][j+colonnes*(i-1)]=1;
						exist[j+colonnes*(i-1)][j+colonnes*i]=1;
					}
					if (t[i][j]!=1 && t[i+1][j]!=1) {
						exist[j+colonnes*i][j+colonnes*(i+1)]=1;
						exist[j+colonnes*(i+1)][j+colonnes*i]=1;
					}
				}
			}
		}
		//Cas particulier pour la matrice d'existence

		//Ici la matrice d'existence est faite.
		int a=yd+colonnes*xd; //Valeur des sommets dans la matrice d'existence
		int b=ya+colonnes*xa;
		System.out.println("a="+a+" b="+b+" xa="+xa+" ya="+ya+" xd="+xd+" yd="+yd);


		int k;
		for(i=0;i<colonnes*lignes;i++) {
			for(j=0;j<colonnes*lignes;j++) {
				tmp[i][j]=exist[i][j];
			}
		}
		for(k=0;k<colonnes*lignes;k++) {
			for(i=0;i<colonnes*lignes;i++) {
				for(j=0;j<colonnes*lignes;j++) {
					if(tmp[i][k]==1 && tmp[k][j]==1) {
						tmp[i][j]=1;
					}
				}
			}
		}

		if (tmp[a][b]==1 || tmp [b][a]==1) {
			return true;
		}
		else {
			return false;
		}





	}
	public  Map generatePve(int nbDestru,int nbInDestru) {
		Case [][] grille=new Case[13][15];
		int x,y,tmp,tmp1;
		x=(int)(Math.random()*13);
		y=(int)(Math.random()*15);
		if(nbDestru>89) {
			nbDestru=89;
		}
		if(nbInDestru>40) {
			nbInDestru=40;
		}
		tmp=nbDestru;
		tmp1=nbInDestru;

		for(int i=0;i<13;i++) {
			for(int j=0;j<15;j++) {
				grille[i][j]=new Case();
				grille[i][j].setX(i);
				grille[i][j].setY(j);
				if (i==0 || j==0 || i==12 || j==14) {
					grille[i][j].setMur(new MurI());
				}
			}
		}
		while(tmp>0) {
			while(grille[x][y].getMur()!=null && tmp>0) {
				x=(int)(Math.random()*13);
				y=(int)(Math.random()*15);
			}
			if(tmp>0) {
				grille[x][y].setMur(new MurD());
				tmp--;
			}
		}
		while(tmp1>0) {
			while(grille[x][y].getMur()!=null && tmp1>0) {
				x=(int)(Math.random()*13);
				y=(int)(Math.random()*15);
			}
			if(tmp1>0) {
				grille[x][y].setMur(new MurI());
				tmp1--;
			}
		}

		int cpt=2;
		while (cpt>0) {
			x=(int)(Math.random()*13);
			y=(int)(Math.random()*15);
			if(grille[x][y].getMur()==null && cpt==2) {
				grille[x][y].setPorte(new Porte());
				cpt--;
			}
			if(grille[x][y].getMur()==null && cpt==1 && grille[x][y].getPorte()==null) {
				grille[x][y].setPersonnage(new Personnage());
				cpt--;
			}
		}
		Map m=new Map();
		m.setX(13);
		m.setY(15);
		m.setGrille(grille);
		return m;
	}

	int [][] traducteur(){//map.traducteur
		int [][] tableau=new int [13][15];
		int i;
		int j;
		for (i=0;i<13;i++){
			for (j=0;j<15;j++){
				if (this.grille[i][j].getMur() instanceof MurI){
					//(MurI)this.grille[i][j].getMur().fdh();
					tableau[i][j]=1;
				} else if (this.grille[i][j].getPorte()!=null) {
					tableau[i][j]=2;
				}
				else if(this.grille[i][j].getPersonnage()!=null) {
					tableau[i][j]=2;
				}
				else {
					tableau[i][j]=0;
				}
			}
		}
		return tableau;
	}

	int [][] traducteur2(){//map.traducteur
		int [][] tableau=new int [13][15];
		int i;
		int j;
		for (i=0;i<13;i++){
			for (j=0;j<15;j++){
				if (this.grille[i][j].getMur() instanceof MurI){
					//(MurI)this.grille[i][j].getMur().fdh();
					tableau[i][j]=2;
				}else if (this.grille[i][j].getMur() instanceof MurD) {
					tableau[i][j] = 1;
					if (this.grille[i][j].getBonus()!=null){
						tableau[i][j]=6;
					}
				} else if (this.grille[i][j].getPersonnage()!=null ) {
					if (this.grille[i][j].getPersonnage().isVivant()) {
						tableau[i][j] = 3;
					}
				} else if (this.grille[i][j].getBombe()!=null) {
					tableau[i][j] = 4;
				} else {
					tableau[i][j]=0;
					if (this.grille[i][j].getBonus()!=null){
						tableau[i][j]=7;
					}
				}
				/*if (this.grille[i][j].getBonus() instanceof BonusExplo){
					//tableau[i][j]=tableau[i][j]+10;
					tableau[i][j]=9;
				} else if (this.grille[i][j].getBonus() instanceof BonusBombe){
					//tableau[i][j]=tableau[i][j]+20;
					tableau[i][j]=9;
				} else if (this.grille[i][j].getBonus() instanceof BonusMove){
					//tableau[i][j]=tableau[i][j]+30;
					tableau[i][j]=9;
				}*/


			}

		}
		return tableau;

	}

	public void genBonus(int nbBonus){ //pose les bonus sur la map une fois celle ci créée avec les cases destructibles
		int i;                          //cette procédure peut s'adapter au mode pve sans problème
		int j;
		int cpt=0;
		int random;
		Case[] caseDes = new Case[1000];
		for (i=0;i<13;i++) {                                      //on parcourt toutes les cases de la map
			for (j = 0; j < 15; j++) {
				if (this.getGrille()[i][j].getMur() instanceof MurD){
					caseDes[cpt]=this.getGrille()[i][j];
					cpt ++;
				}
			}
		}
		int a;
		int b;
		int choix;
		Bonus bonus = new Bonus(null);
		for(i=0;i<nbBonus;i++){
			random = (int)(Math.random() * cpt);
			a=caseDes[random].getX();
			b=caseDes[random].getY();
			choix= (int)(Math.random() * 3);
			if (choix==0){
				bonus= new BonusExplo(caseDes[random]);
			} else if (choix==1){
				bonus= new BonusBombe(caseDes[random]);
			} else if (choix==2){
				bonus= new BonusMove(caseDes[random]);
			}
			this.getGrille()[a][b].setBonus(bonus);
			caseDes[random]=caseDes[cpt-1];
			cpt --;
		}

	}

	public static Map genererMapSolo(int nbDestru,int nbInDestru) {
		Map m = new Map();
		m = m.generatePve(nbDestru, nbInDestru);
		int t[][] = m.traducteur();
		boolean bool=true;
			while (!m.verifSolo(t) || bool) {
				m = m.generatePve(nbDestru, nbInDestru);
				t = m.traducteur();
				int i;
				int j;
				int x=1;
				int y=1;
				for(i=0;i<m.getX();i++){
					for (j=0;j<m.getY();j++){
						if(m.getGrille()[i][j].getPersonnage()!=null){
							x=i;
							y=j;
							i=2000;//sortie des for
							j=2000;
						}
					}
				}
				bool=true;
				if(((m.getGrille()[x][y-1].getMur()==null)&& ((m.getGrille()[x-1][y].getMur()==null)||(m.getGrille()[x+1][y].getMur()==null))) ||// arc ^-> et v->
						((m.getGrille()[x][y+1].getMur()==null)&& ((m.getGrille()[x-1][y].getMur()==null)||(m.getGrille()[x+1][y].getMur()==null)))) {//arc <-^ et <-v
					bool=false;
				}


		}
		return m;
	}

	public void afficher(Batch b, Texture[] multt){
	    	int i;
	    	int j;
	    	for(i=0;i<x;i++){
	    		for (j=0;j<y;j++){
	    			grille[i][j].afficher(b,multt);
				}
			}

	}


}
