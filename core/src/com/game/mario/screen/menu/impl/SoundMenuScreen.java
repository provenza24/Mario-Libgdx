package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.SoundMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.menu.MushroomSelector;
import com.game.mario.util.ResourcesLoader;

public class SoundMenuScreen extends AbstractMenuScreen {

	public SoundMenuScreen() {				
		super(SoundMenuEnum.class, ResourcesLoader.MENU_FONT,MushroomSelector.class);		
		setOffset(-30, -80);
		setFontColor(1,1,1);
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (getSelectedItemEnum()==SoundMenuEnum.QUIT) {		
				resetCursorPosition();
				GameManager.getGameManager().changeScreen(ScreenEnum.MAIN_MENU);				
			}			
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (getSelectedItemEnum()==SoundMenuEnum.MUSIC_VOLUME) {
				SoundManager.getSoundManager().decreaseMusicVolume();				
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (getSelectedItemEnum()==SoundMenuEnum.MUSIC_VOLUME) {
				SoundManager.getSoundManager().increaseMusicVolume();
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (getSelectedItemEnum()==SoundMenuEnum.SOUND_VOLUME) {
				SoundManager.getSoundManager().decreaseSoundVolume();				
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (getSelectedItemEnum()==SoundMenuEnum.SOUND_VOLUME) {
				SoundManager.getSoundManager().increaseSoundVolume();
			}
		}
		
		menuItems.get(0).setName(SoundMenuEnum.MUSIC_VOLUME.toString()+ " <" + String.format("%.0f", 100*SoundManager.getSoundManager().getMusicVolume())+">");
		menuItems.get(1).setName(SoundMenuEnum.SOUND_VOLUME.toString()+ " <" + String.format("%.0f", 100*SoundManager.getSoundManager().getSoundFxVolume())+">");
	}

	@Override
	public void addBackgroundElements() {
		// TODO Auto-generated method stub
		
	}

}
