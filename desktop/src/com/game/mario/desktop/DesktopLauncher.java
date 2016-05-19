package com.game.mario.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.mario.GameManager;

public class DesktopLauncher {
		
	public static int WIDTH = 512;	
	public static int HEIGHT = 480;
	
	//public static int WIDTH = 800;
	//public static int HEIGHT = 750;
	
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = WIDTH;
		config.height = HEIGHT;
		
		//config.vSyncEnabled = false; // Setting to false disables vertical sync
		//config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
		//config.backgroundFPS = 0; // Setting to 0 disables background fps throttling
		
		new LwjglApplication(GameManager.getGameManager(), config);
	}
}
