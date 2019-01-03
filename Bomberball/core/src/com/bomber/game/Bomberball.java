package com.bomber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

//classe de l'application
public class Bomberball extends ApplicationAdapter {
	public  Stage stg;//creer le stage(la fentre ) sur lequel tout va s'afficher => voir tuto scene2D
	Jeu jeu;//creation de notre classe jeu
	public static Texture[] multiTexture = new Texture[16];//tableau comprenant tout les sprites pour pouvoir y acceder rapidement
	// la taille defini / augmenté au besoin
    public static TextureAtlas perso ;
	public static int taillecase=Toolkit.getDefaultToolkit().getScreenSize().width/24;//definition de la taille d'une case en fonction
	//de la taille de l'ecran (getScreenSize) . !!! A terme surement definir des coordonées propres au stage => ex le stage fait 100*75 et se trouye en
	//plein ecran donc s'ajuste automatiquement (dans ce cas acces via vecteurs => voir camera,viewport);

	@Override
	public void create() {//fonction lancée une seule fois au démarrage de l'application pour créer toutes les variables nécessaires
        perso=new TextureAtlas(Gdx.files.internal("perso.atlas"));
		multiTexture[0] = new Texture("thefloorislava.png");//creation des différentes texture que l'on va chercher dans le fichier assets
		multiTexture[1] = new Texture("murD.png");//=>voir Tuto Texture et Sprite
		multiTexture[2] = new Texture("murI.png");
		multiTexture[3] = new Texture("door.png");
		multiTexture[4] = new Texture("player.png");
		multiTexture[5]=new Texture("bomb1.png");
		multiTexture[6]=new Texture("item_bomb.png");
		multiTexture[7]=new Texture("item_speedup.png");
		multiTexture[8]=new Texture("item_superflame.png");
		multiTexture[9]=new Texture("flame1.png");
		multiTexture[10]=new Texture("flame1_horizontal.png");
		multiTexture[11]=new Texture("flame1_vertical.png");
		multiTexture[12]=new Texture("flame1_up.png");
		multiTexture[13]=new Texture("flame1_down.png");
		multiTexture[14]=new Texture("flame1_right.png");
		multiTexture[15]=new Texture("flame1_left.png");
		stg = new Stage(new ScreenViewport());//definition du stage qui prend un point de vu  => voir tuto scene2D
		Gdx.input.setInputProcessor(stg);//on defini comme gestionnaire d'input le stage => le stage recupere les inputs
		jeu=new Jeu();
		jeu.setName("jeu");
		stg.addActor(jeu);// jeu est un group (d'acteur ) donc on l'ajoute à la scene en lui donnant un nom => voir tuto Actor
		stg.setKeyboardFocus(stg.getActors().first());//le stage defini que le premier acteur (le jeu) recupere les inputs




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
}