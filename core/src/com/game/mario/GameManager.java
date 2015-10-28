package com.game.mario;

import com.badlogic.gdx.Game;
import com.game.mario.screen.GameScreen;
import com.game.mario.screen.menu.impl.MenuScreen;

public class GameManager extends Game {

	private MenuScreen menuScreen;
	
	private GameScreen gameScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen();
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
