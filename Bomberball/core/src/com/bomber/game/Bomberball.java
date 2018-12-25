package com.bomber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Bomberball extends ApplicationAdapter {
	Stage stg;
	Jeu jeu;
	Texture[] multiTexture = new Texture[5];

	@Override
	public void create() {//fonction lancée une seule fois au démarrage de l'application pour créer toutes les variables nécessaires
		stg = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stg);
		jeu=new Jeu(multiTexture);
		jeu.setName("jeu");
		stg.addActor(jeu);
		stg.setKeyboardFocus(stg.getActors().first());

		multiTexture[0] = new Texture("thefloorislava.png");
		multiTexture[1] = new Texture("murD.png");
		multiTexture[2] = new Texture("murI.png");
		multiTexture[3] = new Texture("door.png");
		multiTexture[4] = new Texture("player.png");


	}

	@Override
	public void render() {//une fois l'application lancée la fonction render tourne en boucle et va afficher une image sur l'écran à
		// chaque fin d'appel (appellé autant de fois qu'il ya d'image par seconde )

		Gdx.gl.glClearColor(0, 0, 0, 1);//creation de la couleur noir (pas de RGB)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
		stg.act();
		stg.draw();

	}

	@Override
	public void dispose() {//quand la fenetre est fermé on lance cette fonction
		int i;
		for (i = 0; i < 5; i++) {
			multiTexture[i].dispose();
		}
	}

}