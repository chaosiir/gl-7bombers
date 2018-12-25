package com.bomber.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bomber.game.Bomberball;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=800;
		config.width=1200;
		//config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		new LwjglApplication(new Bomberball(), config);
	}
}