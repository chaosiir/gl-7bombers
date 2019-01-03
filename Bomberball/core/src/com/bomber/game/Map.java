
package com.bomber.game;


import com.badlogic.gdx.scenes.scene2d.Group;

public class Map extends Group {//meme chose map est un group d'acteur (les cases)
	private int mat[][];

	public Map(){
		super();

		this.setPosition(Bomberball.taillecase*2.5f, Bomberball.taillecase/2);//on definit sa position dans la fenetre tout les acteurs
		// appartenant à ce groupe auront une position relative à celle-ci => voir tuto Acteur/group
		//la taillecase est defini dans bomberball par rapport à la taille de l'ecran
		grille=new Case[13][11];
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
	
	    private Case[][] grille;
	    private int x;      //dimensions de la map, typiquement 15x13
	    private int y;
	    private boolean solomulti;

	    public Case[][] getGrille() {return grille; }
	    public void setGrille(Case[][] grille) {this.grille = grille;}
	    public int tailleX() {return x; }//!!!!!!!!!!!!!!!!!!!!!!!  le get/set X ou Y sont interdit car sont des fonctions pour l'affichage !!!!!!!!!!!
	    public void settailleX(int x) {this.x = x;}
	    public int tailleY() {return y;}
	    public void settailleY(int y) { this.y = y;}
	    public boolean isSolomulti() {return solomulti;}
	    public void setSolomulti(boolean solomulti) {this.solomulti = solomulti;}


	    //génération de la map PvP de base
	    //Renvoie un tableau de case de taille 13x15 avec le pourtour
        public static Map generatePvp(int nbDestru){
            int i;			// indice de ligne
            int j;			// indice de colonne
            int cpt = 0;		//compteur de cases potentiellment destructibles, spoiler il y en a 93
            int random;
            if (nbDestru>93){
                nbDestru=93;
            }

            Case[][] g = new Case[15][13];                          //ce qu'on va renvoyer, le tableau de case
            Case[] caseDes = new Case[1000];                        //repertorie les cases potentiellment destructibles dans un  tableau

            for (i = 0;i < 15;i++){                                 //on parcourt toutes les cases de la map
                for (j = 0;j < 13;j++){
                    g[i][j] = new Case();							//création d'une nouvelle case à la postion i,j
                    g[i][j].setposX(i);
                    g[i][j].setposY(j);
                    if (i==0 || i==14 || j==0 || j==12 || (i%2==0 && j%2==0)) { //si la case fait partie des case indestructibles
                        Mur m = new MurI();                                     //on crée un mur indestructible et on le met dans la case
                        g[i][j].setMur(m);
                    } else if ((j==1 && (i==3||i==11))||(j==3 && (i==1 || i==13))||(j==9 &&(i==1 || i==13))||(j==11 && (i==3||i==11))){
                        Mur m = new MurD();                  //mise en place des cases destructibles obligatoires autour de zones de départ
                        g[i][j].setMur(m);
                    } else if(!((j<=2 && i<=2) || (j>=10 && i<=2) || (j<=2 && i>=12) || (j>=10 && i>=12))){
                        caseDes[cpt]=g[i][j];               //pour toutes les autres cases sauf celles de la zone de départ
                        cpt++;                              //on ajoute la case de coordonnées i,j à la liste des cases potentiellement destru
                    }
                    if( (i==1 || i==13) && (j==1 || j==11)){g[i][j].setPersonnage(new Personnage(true,g[i][j],2));}
                }
            }
            int a;
            int b;
            System.out.println(cpt);
            for(i=0;i<nbDestru;i++){
                random = (int)(Math.random() * cpt);
                a=caseDes[random].posX();
                b=caseDes[random].posY();
                Mur m = new MurD();
                g[a][b].setMur(m);
                caseDes[random]=caseDes[cpt-1];
                cpt --;
            }

            Map m=new Map();
            m.settailleX(15);
            m.settailleY(13);
            m.grille=g;
            return m;
        }


	    // 1 : indestructible
	    // 2 : entree/sortie
	    // 0 : libre


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
    public  Map generatePve(int nbDestru,int nbInDestru) { //C'est la fonction à appeller pour avoir une map
        Case [][] grille=new Case[15][13];
        int x,y,tmp,tmp1;
        x=(int)(Math.random()*15);
        y=(int)(Math.random()*13);
        if(nbDestru>89) {
            nbDestru=89;
        }
        if(nbInDestru>40) {
            nbInDestru=40;
        }
        tmp=nbDestru;
        tmp1=nbInDestru;

        for(int i=0;i<15;i++) {
            for(int j=0;j<13;j++) {
                grille[i][j]=new Case();
                grille[i][j].setposX(i);
                grille[i][j].setposY(j);
                if (j==0 || i==0 || j==12 || i==14) {
                    grille[i][j].setMur(new MurI());
                }
            }
        }
        while(tmp>0) {
            while(grille[x][y].getMur()!=null && tmp>0) {
                x=(int)(Math.random()*15);
                y=(int)(Math.random()*13);
            }
            if(tmp>0) {
                grille[x][y].setMur(new MurD());
                tmp--;
            }
        }
        while(tmp1>0) {
            while(grille[x][y].getMur()!=null && tmp1>0) {
                x=(int)(Math.random()*15);
                y=(int)(Math.random()*13);
            }
            if(tmp1>0) {
                grille[x][y].setMur(new MurI());
                tmp1--;
            }
        }

        int cpt=2;
        while (cpt>0) {
            x=(int)(Math.random()*15);
            y=(int)(Math.random()*13);
            if(grille[x][y].getMur()==null && cpt==2) {
                grille[x][y].setPorte(new Porte());
                cpt--;
            }
            if(grille[x][y].getMur()==null && cpt==1 && grille[x][y].getPorte()==null) {
                grille[x][y].setPersonnage(new Personnage(true,grille[x][y],2));
                cpt--;
            }
        }
        Map m=new Map();
        m.settailleX(15);
        m.settailleY(13);
        m.setGrille(grille);
        return m;
    }


	int [][] traducteur(){//map.traducteur
		int [][] tableau=new int [15][13];
		int i;
		int j;
		for (i=0;i<15;i++){
			for (j=0;j<13;j++){
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
				for(i=0;i<m.tailleX();i++){
					for (j=0;j<m.tailleY();j++){
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



}
