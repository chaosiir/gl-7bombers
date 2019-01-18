package com.bomber.game;

import com.bomber.game.Graphe;

import java.util.LinkedList;
import java.util.Vector;


public class Dijkstra {
	
	 
	// creation du graphe
	public static final int INFINITE = 1000;//Integer.MAX_VALUE;
	public final static int ALPHA_NOTDEF = -999 ;// on met final psk c'est une constante
	private int x0;
	private int [] S;//ensemble de sommets dont les distances les plus courtes � la source sont connues au d�part seulement Source
	private int [] R;//ensemble des pr�d�cesseur des sommets de 0 � N-1;
	private Graphe g0;
	private int [] D;//tableau des valeurs du meilleur raccourci pour se rendre � chaque sommet
	// rajout
	private boolean [] noeudsMarques;
	private static int dimensionDeLaMatrice;//je rajoute �a pour simplifier le code.
	
	public Dijkstra( int x, Graphe g){	
		x0 = x;
		g0 = g;
		dimensionDeLaMatrice = g0.nbSommet();
		S = new int [dimensionDeLaMatrice];//sommets atteints
		D = new int [dimensionDeLaMatrice];//distances
		noeudsMarques = new boolean[dimensionDeLaMatrice];
		R = new int [dimensionDeLaMatrice];
		calculePlusCourtChemin();
	}
	
	private void calculePlusCourtChemin(){ 
	    int n =0;
		for (int a = 0; a < dimensionDeLaMatrice; a++){
			noeudsMarques[a] =false;
			S[a]=-1; //en supposant qu'on num�rote les sommets de 0 ou de 1 � n.
			R[a]=-1; // pour les noeuds qui n'ont pas de pr�dec�sseur
		}
		
		S[n]=x0;
		D[x0]=0;
		R[x0]=x0;
		initDistMin();
		for (int i = 0; i< dimensionDeLaMatrice ;i++){	
			if (!contains(S,i)){// � revoir
				int t = choixSommet();
				noeudsMarques[t] = true;
				n++;
				S[n]=t;
				majDistMin(t);
			} //end if
		}//end for
//		for (int i=0; i<dimensionDeLaMatrice;i++){
//			System.out.print(" S[i]"+S[i]);
//		}
//		for (int i=0; i<dimensionDeLaMatrice;i++){
//			System.out.print(" R["+i+"]"+R[i]);
//		}
//		System.out.println();
	}
	
	//impl�mentation de initDistMin
	private void initDistMin(){
		noeudsMarques[x0]=true;
		for (int i=0; i<dimensionDeLaMatrice;i++){
			if(g0.existeArc(x0,i)){
				D[i] = g0.getU()[x0][i];
				R[i] = x0;
			}
			else {
				if (x0 != i)
				D[i] =- g0.ALPHA_NOTDEF+1;
			}
		}
	}
	
	private void majDistMin(int n){
		for (int i = 0; i < dimensionDeLaMatrice; i++){			
				if (!contains(S,i)){
					//D[i] = min(D[i], D[n] + distanceDsGraphe(n,i));
					if (D[n] + distanceDsGraphe(n,i)<D[i]){
						D[i]=D[n] + distanceDsGraphe(n,i);
						R[i]=n;
					}
				}
		}
	}
	private int distanceDsGraphe (int t, int s){
		if (g0.existeArc(t, s)){		
			return g0.getU()[t][s];
		}
		else {
			return INFINITE;
		}
	}

	public int choixSommet(){
		int valeurMin = INFINITE;
		int min = x0;
		for (int i=0; i<dimensionDeLaMatrice ;i++){
			if (!noeudsMarques[i])
				if (D[i]<=valeurMin){
					min = i;
					valeurMin = D[i];
				}
		}
		return min;
	}
	
	
    //fonction � d�finir :S.contains(i)
	private boolean contains(int[] S, int i){
		return noeudsMarques[i];
	}
	
	public int longueurChemin (int i){
		return D[i];
	}
	//fonction � d�finir min
	private int min (int i, int j){
		if (i<=j)
			return i;
		else return j;
	}
	public LinkedList<Integer> afficheChemin(int i){
		int source = x0;
		int antecedent = i;
		LinkedList<Integer> lesNoeudsIntermediaires = new LinkedList<Integer>();
		while (antecedent!=source){
			lesNoeudsIntermediaires.add(antecedent);
			antecedent = R[antecedent];
			
		}
		lesNoeudsIntermediaires.add(source);

		return lesNoeudsIntermediaires;
	}


}