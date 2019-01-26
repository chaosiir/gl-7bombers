package com.bomber.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bomber.game.Ecrans.Campagne.Campagne;
import com.bomber.game.Ecrans.Campagne.ChoixCampagne;
import com.bomber.game.Ecrans.Campagne.VictoireCampagne;
import com.bomber.game.Ecrans.Editeur.*;
import com.bomber.game.Ecrans.MenuPause;
import com.bomber.game.Ecrans.MenuPrincipalBis;
import com.bomber.game.Ecrans.Multi.ChoixMapMultiJ;
import com.bomber.game.Ecrans.Multi.ChoixMenuMultijoueur;
import com.bomber.game.Ecrans.Multi.Multijoueur;
import com.bomber.game.Ecrans.Solo.*;
import com.bomber.game.Ecrans.Victoire;

import java.awt.*;
import java.io.*;

/**
 * Classe de l'application globale. Excellent jeu ;)
 */
public class Bomberball extends Game {
	public static Stage stg;
	Jeu jeu;																			//creation de notre classe jeu

	public static int taillecase=Toolkit.getDefaultToolkit().getScreenSize().width/24;	//definition de la taille d'une case en fonction
	//de la taille de l'ecran (getScreenSize)

	public static InputMultiplexer input=new InputMultiplexer();
	public String addresse=new File(".").getPath();
	public MenuPrincipalBis menuPrincipalBis;
    public MenuPause menuPause;
	public MenuSolo menuSolo;
	public ParametreSolo parametreSolo;
	public ChoixEditeurN choixEditeurN;
    public ChoixMenuMultijoueur choixMenuMultijoueur;
    public EditeurNSolo editeurNSolo;
    public Solo jeuSolo;
    public Defaite defaite;
    public EditeurNMulti editeurNMulti;
    public ErreurEditeurS erreurEditeurS;
    public ValiderEditeurSolo validerEditeurSolo;
    public ErreurEditeurM erreurEditeurM;
    public ValiderEditeurMulti validerEditeurMulti;
    public ChoixMapSoloE choixMapSoloE;
    public ChoixMapMultiE choixMapMultiE;
    public ChoixMapSoloJ choixMapSoloJ;
	public Multijoueur multijoueur;
	public Victoire victoire;
	public ChoixMapMultiJ choixMapMultiJ;
	public SelectionCheminEp selectionCheminEp;
	public SelectionCheminEpa selectionCheminEpa;
	public ChoixCampagne choixCampagne;
	public Campagne campagne;
	public VictoireCampagne victoireCampagne;

    public static TextureAtlas perso ;
	public static TextureAtlas ennemis ;
	public static Texture[] multiTexture = new Texture[25];			//tableau comprenant tous les sprites pour pouvoir y acceder rapidement
	@Override
	/**
	 * Initialise les textures et les états du jeu à son lancement
	 */
	public void create() {													//fonction lancée une seule fois au démarrage de l'application pour créer toutes les variables nécessaires
		System.out.println(addresse);
		perso = new TextureAtlas(Gdx.files.internal("perso.atlas"));
		ennemis = new TextureAtlas(Gdx.files.internal("ennemis.atlas"));

		multiTexture[0] = new Texture("thefloorislava.png");		//creation des différentes texture que l'on va chercher dans le fichier assets
		multiTexture[1] = new Texture("murD.png");
		multiTexture[2] = new Texture("murI.png");
		multiTexture[3] = new Texture("door.png");
		multiTexture[4] = new Texture("player.png");
		multiTexture[5] = new Texture("bomb1.png");
		multiTexture[6] = new Texture("item_bomb.png");
		multiTexture[7] = new Texture("item_speedup.png");
		multiTexture[8] = new Texture("item_superflame.png");
		multiTexture[9] = new Texture("flame1.png");
		multiTexture[10] = new Texture("flame1_horizontal.png");
		multiTexture[11] = new Texture("flame1_vertical.png");
		multiTexture[12] = new Texture("flame1_up.png");
		multiTexture[13] = new Texture("flame1_down.png");
		multiTexture[14] = new Texture("flame1_right.png");
		multiTexture[15] = new Texture("flame1_left.png");
		multiTexture[16] = new Texture("bat1.png");
		multiTexture[17] = new Texture("ghost1.png");
		multiTexture[18] = new Texture("rouge.png");
		multiTexture[19]= new Texture("item_throwing.png");
		multiTexture[20]= new Texture("player2.png");
		multiTexture[21]= new Texture("player3.png");
		multiTexture[22]= new Texture("player4.png");
		multiTexture[23]= new Texture("ghost2.png");
		multiTexture[24]= new Texture("bat2.png");
		stg = new Stage(new ScreenViewport());							//definition du stage qui prend un point de vue
		Gdx.input.setInputProcessor(input);								//on definit comme gestionnaire d'input le stage => le stage récupère les inputs
		jeu = new Jeu();
		jeu.setName("jeu");



		menuPrincipalBis = new MenuPrincipalBis(this, jeu);
		menuSolo = new MenuSolo(this, jeu);
        menuPause = new MenuPause(this, jeu);
		parametreSolo = new ParametreSolo(this, jeu);
		choixEditeurN = new ChoixEditeurN(this, jeu);
		choixMenuMultijoueur = new ChoixMenuMultijoueur(this, jeu);
		editeurNSolo = new EditeurNSolo(this, jeu);
		jeuSolo=new Solo(this,jeu);
		editeurNMulti = new EditeurNMulti(this,jeu);
		erreurEditeurS = new ErreurEditeurS(this,jeu);
		validerEditeurSolo=new ValiderEditeurSolo(this,jeu);
		erreurEditeurM= new ErreurEditeurM(this, jeu);
		validerEditeurMulti = new ValiderEditeurMulti(this,jeu);
		choixMapSoloE = new ChoixMapSoloE(this,jeu);
		choixMapMultiE = new ChoixMapMultiE(this,jeu);
		choixMapSoloJ= new ChoixMapSoloJ(this,jeu);
		multijoueur = new Multijoueur(this,jeu);
		choixMapMultiJ = new ChoixMapMultiJ(this,jeu);
		selectionCheminEp = new SelectionCheminEp(this,jeu);
		selectionCheminEpa = new SelectionCheminEpa(this,jeu);
		choixCampagne= new ChoixCampagne(this,jeu);
		campagne = new Campagne(this,jeu);
		victoireCampagne = new VictoireCampagne(this,jeu);



		jeu.setEtat(menuPrincipalBis);
		setScreen(menuPrincipalBis);
		stg.addActor(menuPrincipalBis);						// jeu est un group (d'acteur ) donc on l'ajoute à la scène en lui donnant un nom
		stg.setKeyboardFocus(stg.getActors().first());
		input.addProcessor(stg);

	}




	@Override
	/**
	 * Assure l'affichage en continu du jeu
	 */
	public void render() {												//une fois l'application lancée la fonction render tourne en boucle et va afficher une image sur l'écran à
																		// chaque fin d'appel (appelée autant de fois qu'il ya d'image par seconde )

		Gdx.gl.glClearColor(0, 0, 0, 1);			//creation de la couleur noir pour le netoyage(pas de RGB)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);						//nettoyage de l'ecran => tout l'ecran prend la couleur donnée (ici noir)
		stg.act(Gdx.graphics.getDeltaTime());							//tout les acteurs continuent leur actions
		stg.draw();														// on dessine le stage, donc chaque acteur, sur l'écran ce qui revient à dessiner le groupe jeu
	}

	@Override
/**
 * Supprime les données du jeu lorsque celui-ci est fermé
 */
	public void dispose() {												//quand la fenêtre est fermée on lance cette fonction
		int i;
		for (i = 0; i < multiTexture.length; i++) {
			multiTexture[i].dispose();									//pour chaque texture on la detruit pour éviter les fuites memoire
		}
	}

	@Override
	/**
	 * Gère le changement de taille de la fenêtre d'affichage
	 */
	public void resize(int width, int height) {							//se lance quand la fenetre change de taille donc jamais car le jeu est bloqué en plein ecran
		stg.getViewport().update(width,height);
	}

/*Permet de charger le contenu d'un fichier*/

	/**
	 * Loads the specified file into a String representation
	 * @author Stephane Nicoll - Infonet FUNDP
	 * @version 0.1
	 */
	public static String loadFile(File f) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			StringWriter out = new StringWriter();
			int b;
			while ((b=in.read()) != -1)
				out.write(b);
			out.flush();
			out.close();
			in.close();
			return out.toString();
		}
		catch (IOException ie)
		{
			ie.printStackTrace();
		}
		return null;
	}

	/**
	 * Permet de copier un fichier source vers un fichier destinataire
	 *
	 * @param source : fichier à copier
	 * @param dest   : fichier destinataire
	 * @return : Si true: pas d'erreur; false sinon
	 */
	public static boolean copier(File source, File dest) {
		try {InputStream sourceFile = new java.io.FileInputStream(source);
			 OutputStream destinationFile = new FileOutputStream(dest);
			 // Lecture par segment de 0.5Mo
			byte buffer[] = new byte[512 * 1024];
			int nbLecture;
			while ((nbLecture = sourceFile.read(buffer)) != -1){
				destinationFile.write(buffer, 0, nbLecture);
			}
		} catch (IOException e){
			e.printStackTrace();
			return false; // Erreur
		}
		return true; // Résultat OK
	}

}