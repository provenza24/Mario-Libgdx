package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.mario.GameManager;
import com.game.mario.enums.MainMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;

public class MenuScreen extends AbstractMenuScreen {

	public MenuScreen() {				
		super(MainMenuEnum.class, new BitmapFont(Gdx.files.internal("fonts/mario.fnt")),MushroomSelector.class);
		setOffset(0, -80);		
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if (getSelectedItemEnum()==MainMenuEnum.ONE_PLAYER_GAME) {				
				GameManager.getGameManager().setScreen(GameManager.getGameManager().getGameScreen());
			}
		}
	}

}
