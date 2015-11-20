package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.PauseMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;
import com.game.mario.util.ResourcesLoader;

public class PauseMenuScreen extends AbstractMenuScreen {

	public PauseMenuScreen() {				
		super(PauseMenuEnum.class, ResourcesLoader.MENU_FONT,MushroomSelector.class);		
		setOffset(0, -80);			
		setFontColor(1,1,1);
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (getSelectedItemEnum()==PauseMenuEnum.CONTINUE) {				
				GameManager.getGameManager().changeScreen(ScreenEnum.GAME);
			} if (getSelectedItemEnum()==PauseMenuEnum.QUIT) {
				GameManager.getGameManager().startNewGame();							
			}
		}
	}

	@Override
	public void addBackgroundElements() {
		// TODO Auto-generated method stub
		
	}

}
