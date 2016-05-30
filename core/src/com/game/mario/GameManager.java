package com.game.mario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.screen.game.GameScreen;
import com.game.mario.screen.menu.impl.LevelMenuScreen;
import com.game.mario.screen.menu.impl.MainMenuScreen;
import com.game.mario.screen.menu.impl.PauseMenuScreen;
import com.game.mario.screen.menu.impl.SoundMenuScreen;
import com.game.mario.sound.SoundManager;

public class GameManager extends Game {

	private static final Map<ScreenEnum, Screen> SCREENS = new HashMap<ScreenEnum, Screen>();

	private static final GameManager gameManager = new GameManager();

	private static GameScreen gameScreen;

	private static int nbLifes;

	private static int nbCoins;

	private static int currentLevel;
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;

	private List<String> levels = new ArrayList<String>();

	@Override
	public void create() {
		
		
		initState();
				
		//levels.add("test_world.tmx");				
		//levels.add("level_1_1.tmx");
		//levels.add("level_1_2.tmx");
		//levels.add("level_1_3.tmx");
		levels.add("level_1_4.tmx");
		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		SCREENS.put(ScreenEnum.MAIN_MENU, new MainMenuScreen());
		SCREENS.put(ScreenEnum.PAUSE_MENU, new PauseMenuScreen());
		SCREENS.put(ScreenEnum.LEVEL_MENU, new LevelMenuScreen());
		SCREENS.put(ScreenEnum.SOUND_MENU, new SoundMenuScreen());
		
		SoundManager.getSoundManager().setCurrentMusic(SoundManager.SOUND_TITLE_THEME);		
		SoundManager.getSoundManager().playMusic(false);		
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
		SoundManager.getSoundManager().setCurrentMusic(SoundManager.SOUND_TITLE_THEME);
		SoundManager.getSoundManager().playMusic(false);		
		changeScreen(ScreenEnum.MAIN_MENU);
	}
	
	public void restartLevel() {
		nbLifes--;
		gameScreen.dispose();
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		if (nbLifes<0) {
			startNewGame();
		} else {
			changeScreen(ScreenEnum.LEVEL_MENU);
		}		
	}

	public void nextLevel() {
		currentLevel++;
		gameScreen.dispose();		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		changeScreen(ScreenEnum.LEVEL_MENU);
	}

	public void addLife() {
		nbLifes++;
	}
	
	public void addCoin() {
		nbCoins++;
		if (nbCoins==100) {
			nbCoins =0;
			nbLifes++;
		}
	}
	
	public String getCurrentLevelName() {
		return levels.get(currentLevel);
	}

	public int getNbLifes() {
		return nbLifes;
	}
	
	public int getNbCoins() {
		return nbCoins;
	}
	
	public void looseLife() {
		nbLifes--;
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

}
