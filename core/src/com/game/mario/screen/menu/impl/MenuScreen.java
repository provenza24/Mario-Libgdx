package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.mario.GameManager;
import com.game.mario.screen.GameScreen;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;

public class MenuScreen extends AbstractMenuScreen {

	public MenuScreen() {
		
		super(MushroomSelector.class, new String[]{ "1 PLAYER GAME", "OPTIONS", "CREDITS" });							
		
		//super(new String[]{ "1 PLAYER GAME", "OPTIONS", "CREDITS" });
		//setOffset(100,50);		
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if (getSelectedItemName().equals("1 PLAYER GAME")) {
				GameScreen gameScreen = GameManager.getGameManager().getGameScreen();
				if (gameScreen == null) {
					GameManager.getGameManager().setGameScreen(new GameScreen());
				}
				GameManager.getGameManager().setScreen(GameManager.getGameManager().getGameScreen());
			}
		}
	}

}
