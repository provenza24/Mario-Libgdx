package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.SoundMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.screen.menu.MenuItem;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.menu.MushroomSelector;
import com.game.mario.util.ResourcesLoader;

public class SoundMenuScreen extends AbstractMenuScreen {

	public SoundMenuScreen() {				
		super(SoundMenuEnum.class, ResourcesLoader.MENU_FONT,MushroomSelector.class);		
		setOffset(0, -80);
		setFontColor(1,1,1);
	}

	@Override
	public void handleInput() {
		
		super.handleInput();		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {			
			if (getSelectedItemEnum()==SoundMenuEnum.BACK) {	
				GameManager.getGameManager().changeScreen(ScreenEnum.MAIN_MENU);
			}									
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (getSelectedItemEnum()==SoundMenuEnum.MUSIC_VOL) {
				for (MenuItem menuItem : menuItems) {
					if (menuItem.getMenuEnum().equals(SoundMenuEnum.MUSIC_VOL)) {
						SoundManager.getSoundManager().decreaseMusicVolume();
						System.out.println(SoundManager.getSoundManager().getMusicVolume());
					}
				}
			}
		}
	}

	@Override
	public void addBackgroundElements() {
		// TODO Auto-generated method stub
		
	}

}
