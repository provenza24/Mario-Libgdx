package com.game.mario.screen.menu.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.menu.MainMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sprite.menu.MushroomSelector;

public class MainMenuScreen extends AbstractMenuScreen {

	public MainMenuScreen() {				
		super(MainMenuEnum.class, new BitmapFont(Gdx.files.internal("fonts/pressStart2P.fnt")),MushroomSelector.class);
		setOffset(0, -110);				
	}
	
	public void addBackgroundElements() {		
		backgroundGroup.addActor(new Image(new Texture(Gdx.files.internal("titlescreen_background.png"))));		
		
		Image titlescreen = new Image(new Texture(Gdx.files.internal("titlescreen.png")));			
		titlescreen.setPosition(Gdx.graphics.getWidth() / 2 - titlescreen.getWidth() /2, 220);		
		backgroundGroup.addActor(titlescreen);		
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
