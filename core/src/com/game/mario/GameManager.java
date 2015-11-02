package com.game.mario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.screen.GameScreen;
import com.game.mario.screen.menu.impl.LevelMenuScreen;
import com.game.mario.screen.menu.impl.MainMenuScreen;
import com.game.mario.screen.menu.impl.PauseMenuScreen;

public class GameManager extends Game {

	private static final Map<ScreenEnum, Screen> SCREENS = new HashMap<ScreenEnum, Screen>();

	private static final GameManager gameManager = new GameManager();

	private GameScreen gameScreen;

	private int nbLifes;

	private int nbCoins;

	private int currentLevel;

	/** 0=small, 1=big, 2=flowered */
	private int sizeState;

	private List<String> levels = new ArrayList<String>();

	@Override
	public void create() {
		
		levels.add(0, "level_1_1.tmx");		
		levels.add(1, "level_1_2.tmx");
		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		SCREENS.put(ScreenEnum.MAIN_MENU, new MainMenuScreen());
		SCREENS.put(ScreenEnum.PAUSE_MENU, new PauseMenuScreen());
		SCREENS.put(ScreenEnum.LEVEL_MENU, new LevelMenuScreen());
		
		setScreen(SCREENS.get(ScreenEnum.MAIN_MENU));
	}
	
	private void initState() {
		
		nbLifes = 3;
		nbCoins = 0;
		sizeState = 0;
		currentLevel = 0;
	}

	public static GameManager getGameManager() {
		return gameManager;
	}

	public void changeScreen(ScreenEnum screenEnum) {
		setScreen(SCREENS.get(screenEnum));
	}

	public Screen getScreen(ScreenEnum screenEnum) {
		return SCREENS.get(screenEnum);
	}

	public void startNewGame() {
		initState();
		gameScreen.dispose();
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
	}

	public void nextLevel() {
		currentLevel++;
		gameScreen.dispose();		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		changeScreen(ScreenEnum.LEVEL_MENU);
	}

	public void addCoin() {
		nbCoins++;
	}
	
	public String getCurrentLevelName() {
		return levels.get(currentLevel);
	}

	public int getNbLifes() {
		return nbLifes;
	}

	public void setNbLifes(int nbLifes) {
		this.nbLifes = nbLifes;
	}

	public int getNbCoins() {
		return nbCoins;
	}

	public void setNbCoins(int nbCoins) {
		this.nbCoins = nbCoins;
	}

	public int getSizeState() {
		return sizeState;
	}

	public void setSizeState(int sizeState) {
		this.sizeState = sizeState;
	}

	public List<String> getLevels() {
		return levels;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

}
