package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.PauseMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;

public class PauseMenuScreen extends AbstractMenuScreen {

	public PauseMenuScreen() {				
		super(PauseMenuEnum.class, new BitmapFont(Gdx.files.internal("fonts/mario.fnt")),MushroomSelector.class);
		setOffset(0, -80);		
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			if (getSelectedItemEnum()==PauseMenuEnum.CONTINUE) {				
				GameManager.getGameManager().changeScreen(ScreenEnum.GAME);
			} if (getSelectedItemEnum()==PauseMenuEnum.QUIT) {
				GameManager.getGameManager().changeScreen(ScreenEnum.MAIN_MENU);				
			}
		}
	}

}
