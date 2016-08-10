package com.game.mario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.screen.game.GameScreen;
import com.game.mario.screen.menu.impl.LevelMenuScreen;
import com.game.mario.screen.menu.impl.MainMenuScreen;
import com.game.mario.screen.menu.impl.PauseMenuScreen;
import com.game.mario.screen.menu.impl.SoundMenuScreen;
import com.game.mario.sound.SoundManager;
import com.game.mario.util.Level;

public class GameManager extends Game {

	private static final Map<ScreenEnum, Screen> SCREENS = new HashMap<ScreenEnum, Screen>();

	private static final GameManager gameManager = new GameManager();

	private static GameScreen gameScreen;

	private static int nbLifes;

	private static int nbCoins;

	private static int currentLevelIndex;
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;

	private List<Level> levels = new ArrayList<Level>();
	
	private LevelMenuScreen levelMenuScreen;

	@Override
	public void create() {
				
		initState();
						
		//levels.add("test_world.tmx");				
		
		levels.add(new Level(1,1,"level_1_1.tmx", WorldTypeEnum.HILLS));
		levels.add(new Level(1,2,"level_1_2.tmx", WorldTypeEnum.UNDERGROUND));
		levels.add(new Level(1,3,"level_1_3.tmx", WorldTypeEnum.WATERFALL));
		levels.add(new Level(1,4,"level_1_4.tmx", WorldTypeEnum.CASTLE));		
		
		gameScreen = new GameScreen();
		levelMenuScreen = new LevelMenuScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		SCREENS.put(ScreenEnum.MAIN_MENU, new MainMenuScreen());
		SCREENS.put(ScreenEnum.PAUSE_MENU, new PauseMenuScreen());		 
		SCREENS.put(ScreenEnum.LEVEL_MENU, levelMenuScreen);
		SCREENS.put(ScreenEnum.SOUND_MENU, new SoundMenuScreen());
		
		SoundManager.getSoundManager().setCurrentMusic(SoundManager.SOUND_TITLE_THEME);		
		SoundManager.getSoundManager().playMusic(false);		
		setScreen(SCREENS.get(ScreenEnum.MAIN_MENU));
	}
	
	private void initState() {
		
		nbLifes = 3;
		nbCoins = 0;
		sizeState = 0;
		currentLevelIndex = 0;
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
		levelMenuScreen.changeImage();
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
		currentLevelIndex++;
		gameScreen.dispose();		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);	
		levelMenuScreen.changeImage();
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
		return levels.get(currentLevelIndex).getTmxName();
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

	public List<Level> getLevels() {
		return levels;
	}

	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}

	public Level getCurrentLevel() {
		return levels.get(currentLevelIndex);
	}	
}
