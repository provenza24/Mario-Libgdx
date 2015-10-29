package com.game.mario;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.screen.GameScreen;
import com.game.mario.screen.menu.impl.MainMenuScreen;
import com.game.mario.screen.menu.impl.PauseMenuScreen;

public class GameManager extends Game {

	private static final Map<ScreenEnum, Screen> SCREENS = new HashMap<ScreenEnum, Screen>();
		
	private static final GameManager gameManager = new GameManager();
	
	@Override
	public void create() {		
		
		SCREENS.put(ScreenEnum.GAME, new GameScreen());
		SCREENS.put(ScreenEnum.MAIN_MENU, new MainMenuScreen());
		SCREENS.put(ScreenEnum.PAUSE_MENU, new PauseMenuScreen());
		
		setScreen(SCREENS.get(ScreenEnum.MAIN_MENU));
	}

	public static GameManager getGameManager() {
		return gameManager;
	}

	public void changeScreen(ScreenEnum screenEnum) {
		setScreen(SCREENS.get(screenEnum));
	}
	
}
