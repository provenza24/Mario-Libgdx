package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.MainMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;

public class MainMenuScreen extends AbstractMenuScreen {

	public MainMenuScreen() {				
		super(MainMenuEnum.class, new BitmapFont(Gdx.files.internal("fonts/mario.fnt")),MushroomSelector.class);
		setOffset(0, -80);		
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (getSelectedItemEnum()==MainMenuEnum.ONE_PLAYER_GAME) {				
				GameManager.getGameManager().changeScreen(ScreenEnum.GAME);				
			}
		}
	}

}
