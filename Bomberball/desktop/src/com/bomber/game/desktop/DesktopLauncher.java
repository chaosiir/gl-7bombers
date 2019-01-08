package com.bomber.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bomber.game.Bomberball;
//class servant à lancer l'application sur ordinateur
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();//en plus de l'application on doit donner une configuration
		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());//on met dans la configuration le mode plein ecran au demarrage
		new LwjglApplication(new Bomberball(), config);//lance l'application
	}
}
