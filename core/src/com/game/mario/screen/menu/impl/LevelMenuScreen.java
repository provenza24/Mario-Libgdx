package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.LevelMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.menu.MushroomSelector;
import com.game.mario.util.ResourcesLoader;

public class LevelMenuScreen extends AbstractMenuScreen {

	public LevelMenuScreen() {				
		super(LevelMenuEnum.class, ResourcesLoader.MENU_FONT,MushroomSelector.class);		
		setOffset(0, -80);
		setFontColor(1,1,1);
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			SoundManager.getSoundManager().playMusic(SoundManager.SOUND_MAIN_THEME);			
			GameManager.getGameManager().changeScreen(ScreenEnum.GAME);							
		}
	}

	@Override
	public void addBackgroundElements() {
		// TODO Auto-generated method stub
		
	}

}
