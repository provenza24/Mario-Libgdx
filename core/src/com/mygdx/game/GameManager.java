package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.mario.screen.GameScreen;
import com.mygdx.game.mario.screen.MenuScreen;

public class GameManager extends Game {

	private MenuScreen menuScreen;
	
	private GameScreen gameScreen;

	@Override
	public void create() {
		menuScreen = new MenuScreen();		
		setScreen(menuScreen);
	}

	public static GameManager getGameManager() {
		return gameManager;
	}
	
	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public void setMenuScreen(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	private static final GameManager gameManager = new GameManager();
	
	

}
