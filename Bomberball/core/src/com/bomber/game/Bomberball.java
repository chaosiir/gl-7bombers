package com.bomber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.io.*;
import java.nio.channels.FileChannel;

//classe de l'application
public class Bomberball extends Game {
	public static Stage stg;//creer le stage(la fenetre ) sur lequel tout va s'afficher => voir tuto scene2D
	Jeu jeu;//creation de notre classe jeu
	// la taille defini / augmenté au besoin
	public static int taillecase=Toolkit.getDefaultToolkit().getScreenSize().width/24;//definition de la taille d'une case en fonction
	//de la taille de l'ecran (getScreenSize) . !!! A terme surement definir des coordonées propres au stage => ex le stage fait 100*75 et se trouye en
	//plein ecran donc s'ajuste automatiquement (dans ce cas acces via vecteurs => voir camera,viewport);


	MenuPrincipalBis menuPrincipalBis;
    MenuPause menuPause;
	MenuSolo menuSolo;
	ParametreSolo parametreSolo;
	ChoixEditeurN choixEditeurN;
    ChoixMenuMultijoueur choixMenuMultijoueur;
    EditeurNSolo editeurNSolo;
    Solo jeuSolo;
    Defaite defaite;
    EditeurNMulti editeurNMulti;
    ErreurEditeurS erreurEditeurS;
    ValiderEditeurSolo validerEditeurSolo;
    ErreurEditeurM erreurEditeurM;
    ValiderEditeurMulti validerEditeurMulti;
    ChoixMapSoloE choixMapSoloE;
    ChoixMapMultiE choixMapMultiE;
    ChoixMapSoloJ choixMapSoloJ;
	Multijoueur multijoueur;
	Victoire victoire;
	ChoixMapMultiJ choixMapMultiJ;
	SelectionCheminEp selectionCheminEp;
	SelectionCheminEpa selectionCheminEpa;

    public static TextureAtlas perso ;
	public static TextureAtlas ennemis ;
	public static Texture[] multiTexture = new Texture[25];//tableau comprenant tout les sprites pour pouvoir y acceder rapidement

	@Override
	public void create() {//fonction lancée une seule fois au démarrage de l'application pour créer toutes les variables nécessaires
		perso = new TextureAtlas(Gdx.files.internal("perso.atlas"));
		ennemis = new TextureAtlas(Gdx.files.internal("ennemis.atlas"));


		multiTexture[0] = new Texture("thefloorislava.png");//creation des différentes texture que l'on va chercher dans le fichier assets
		multiTexture[1] = new Texture("murD.png");//=>voir Tuto Texture et Sprite
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
		stg = new Stage(new ScreenViewport());//definition du stage qui prend un point de vu  => voir tuto scene2D
		Gdx.input.setInputProcessor(stg);//on defini comme gestionnaire d'input le stage => le stage recupere les inputs
		jeu = new Jeu();
		jeu.setName("jeu");
		stg.addActor(jeu);// jeu est un group (d'acteur ) donc on l'ajoute à la scene en lui donnant un nom => voir tuto Actor
		stg.setKeyboardFocus(stg.getActors().first());//le stage defini que le premier acteur (le jeu) recupere les inputs


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
		jeu.setEtat(menuPrincipalBis);
		setScreen(menuPrincipalBis);

	}




	@Override
	public void render() {//une fois l'application lancée la fonction render tourne en boucle et va afficher une image sur l'écran à
		// chaque fin d'appel (appellé autant de fois qu'il ya d'image par seconde )

		Gdx.gl.glClearColor(0, 0, 0, 1);//creation de la couleur noir pour le netoyage(pas de RGB)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
		stg.act(Gdx.graphics.getDeltaTime());//tout les acteurs continuent leur actions  => voir tuto Actor
		stg.draw();// on dessine le stage, donc chaque acteur, sur l'écran ce qui revient à dessiner le groupe jeu
	}

	@Override
	public void dispose() {//quand la fenetre est fermé on lance cette fonction
		int i;
		for (i = 0; i < multiTexture.length; i++) {
			multiTexture[i].dispose();//pour chaque texture on la detruit pour eviter les fuites memoire =>voir tuto Texture
		}
	}

	@Override
	public void resize(int width, int height) {//se lance quand la fenetre change de taille donc jamais car le jeu est bloqué en plein ecran
		stg.getViewport().update(width,height);//on change le point de vu (surtout la taille de ce qu'on voit ) !! ne marche pas
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