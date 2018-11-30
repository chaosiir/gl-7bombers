package com.bomber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.*;


public class Bomberball extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Map map;
	Texture fb;
	Sprite fbs;
	BitmapFont f;
	Texture [] multiTexture=new Texture[5];
	
	@Override
	public void create () {//fonction lancée une seule fois au démarrage de l'application pour créer toutes les variables nécessaires
		batch = new SpriteBatch();//fenetre d'affichage
		fbs=new Sprite(fb=new Texture("fondbouton.png"));
		fbs.setScale(1.5f,1);
		f=new BitmapFont(Gdx.files.internal("alph_bl.fnt"));
		Gdx.input.setInputProcessor(this);//comme on impplemente InputProcessor on peut definir l'interface comme son propre InputProcessor
		//pour recuperer les inputs
		map=Map.genererMapSolo(60,30);
		multiTexture[0]=new Texture("thefloorislava.png");
		multiTexture[1]= new Texture("murD.png");
		multiTexture[2]=new Texture("murI.png");
		multiTexture[3]=new Texture("door.png");
		multiTexture[4]=new Texture("player.png");


	}

	@Override
	public void render () {//une fois l'application lancée la fonction render tourne en boucle et va afficher une image sur l'écran à
		// chaque fin d'appel (appellé autant de fois qu'il ya d'image par seconde )

		Gdx.gl.glClearColor(0, 0, 0, 1);//creation de la couleur noir (pas de RGB)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//nettoyage de l'ecran => tout l'ecran prend la couleur donné (ici noir)
		batch.begin();//puis on redessine tout dans la fenetre  pour ça on lance le dessin avec un begin()
		batch.draw(fbs,200,300,0,0,fbs.getWidth(),fbs.getHeight(),fbs.getScaleX(),fbs.getScaleY(),0);
		f.draw(batch,"generer une map multi",210,370);
		batch.draw(fbs,200,600,0,0,fbs.getWidth(),fbs.getHeight(),fbs.getScaleX(),fbs.getScaleY(),0);
		f.draw(batch,"generer une map solo",220,670);
		map.afficher(batch,multiTexture);
		batch.end();// fin du dessin on envoit l'image à l'écran

	}

	@Override
	public void dispose () {//quand la fenetre est fermé on lance cette fonction
		batch.dispose();//bien detruire les objet sinon fuite mémoire
		int i;
		for(i=0;i<5;i++){
			multiTexture[i].dispose();
		}
	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(button== Buttons.LEFT){// on test sur quel bouton  on a cliqué en fonction des coordonées
			if((screenX>=200 && screenX<=500) && (( Gdx.graphics.getHeight()-screenY)>=300 && ( Gdx.graphics.getHeight()-screenY)<= 400)){
				System.out.println("mult");
				map=Map.generatePvp(70);
			}
			if((screenX>=200 && screenX<=500) && (( Gdx.graphics.getHeight()-screenY)>=600 && ( Gdx.graphics.getHeight()-screenY)<= 700)){
				map=Map.genererMapSolo(80,20);
				System.out.println("solo");
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
