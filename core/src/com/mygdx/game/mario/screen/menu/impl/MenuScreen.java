package com.mygdx.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.GameManager;
import com.mygdx.game.mario.screen.GameScreen;
import com.mygdx.game.mario.screen.menu.AbstractMenuScreen;
import com.mygdx.game.mario.sprite.menu.MushroomSelector;

public class MenuScreen extends AbstractMenuScreen {

	public MenuScreen() {
		super(MushroomSelector.class, new String[]{ "1 PLAYER GAME", "OPTIONS", "CREDITS" });		
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if (currentItem == 0) {
				GameScreen gameScreen = GameManager.getGameManager().getGameScreen();
				if (gameScreen == null) {
					GameManager.getGameManager().setGameScreen(new GameScreen());
				}
				GameManager.getGameManager().setScreen(GameManager.getGameManager().getGameScreen());
			}
		}
	}

}
