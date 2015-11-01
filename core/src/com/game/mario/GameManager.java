package com.game.mario;

import java.util.HashMap;
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
	
	@Override
	public void create() {		
		
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		SCREENS.put(ScreenEnum.MAIN_MENU, new MainMenuScreen());
		SCREENS.put(ScreenEnum.PAUSE_MENU, new PauseMenuScreen());
		SCREENS.put(ScreenEnum.LEVEL_MENU, new LevelMenuScreen());
		
		setScreen(SCREENS.get(ScreenEnum.MAIN_MENU));
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
		gameScreen.dispose();
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
	}
	
	public void nextLevel() {
		gameScreen.dispose();
		GameState.getInstance().nextLevel();
		gameScreen = new GameScreen();
		SCREENS.put(ScreenEnum.GAME, gameScreen);
		changeScreen(ScreenEnum.LEVEL_MENU);
	}
	
}
