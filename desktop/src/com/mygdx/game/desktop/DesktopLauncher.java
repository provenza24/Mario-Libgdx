package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		/*config.width = 512;
		config.height = 480;*/
		
		config.width = 800;
		config.height = 750;
		
		//config.vSyncEnabled = false; // Setting to false disables vertical sync
		//config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
		//config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
