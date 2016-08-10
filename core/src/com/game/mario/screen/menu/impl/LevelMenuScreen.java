package com.game.mario.screen.menu.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.mario.GameManager;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.enums.menu.LevelMenuEnum;
import com.game.mario.screen.menu.AbstractMenuScreen;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.menu.MushroomSelector;
import com.game.mario.sprite.statusbar.MarioLifes;
import com.game.mario.util.ResourcesLoader;

public class LevelMenuScreen extends AbstractMenuScreen {
	
	private final static Map<WorldTypeEnum, Texture> LEVEL_IMAGES = new HashMap<>();
	
	private Image image;
	
	static {
		LEVEL_IMAGES.put(WorldTypeEnum.HILLS, ResourcesLoader.HILLS_LEVEL_MENU);
		LEVEL_IMAGES.put(WorldTypeEnum.UNDERGROUND, ResourcesLoader.UNDERGROUND_LEVEL_MENU);
		LEVEL_IMAGES.put(WorldTypeEnum.WATERFALL, ResourcesLoader.WATERFALL_LEVEL_MENU);
		LEVEL_IMAGES.put(WorldTypeEnum.CASTLE, ResourcesLoader.CASTLE_LEVEL_MENU);
	}
	
	public LevelMenuScreen() {				
		super(LevelMenuEnum.class, ResourcesLoader.MENU_FONT,MushroomSelector.class);		
		setOffset(20, -160);
		setFontColor(1,1,1);			
	}

	@Override
	public void handleInput() {
		
		super.handleInput();
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			
			SoundManager.getSoundManager().playMusic(true);			
			GameManager.getGameManager().changeScreen(ScreenEnum.GAME);							
		}
	}

	@Override
	public void addBackgroundElements() {
		
		image = new Image(LEVEL_IMAGES.get(GameManager.getGameManager().getCurrentLevel().getWorldTypeEnum()));		
		image.setPosition(Gdx.graphics.getWidth() / 2 - image.getWidth() /2, Gdx.graphics.getHeight() /2 - 130);
		backgroundGroup.addActor(image);
		
		MarioLifes marioLifes = new MarioLifes();
		marioLifes.scaleBy(1.01f);
		marioLifes.setPosition(Gdx.graphics.getWidth() / 2 - 70, Gdx.graphics.getHeight() / 2 + image.getHeight() /2 + 30 - marioLifes.getHeight());
		backgroundGroup.addActor(marioLifes);
		changeImage();
	}
	
	public void changeImage() {				
		image = new Image(LEVEL_IMAGES.get(GameManager.getGameManager().getCurrentLevel().getWorldTypeEnum()));		
		image.setPosition(Gdx.graphics.getWidth() / 2 - image.getWidth() /2, Gdx.graphics.getHeight() /2 - 130);
		backgroundGroup.addActor(image);
	}
	
	@Override
	public void draw() {
		super.draw();
		
		Batch batch = stage.getBatch();
		batch.begin();
		
		font.draw(batch, "WORLD   "+GameManager.getGameManager().getCurrentLevel().getWorldNumber()+"-"+GameManager.getGameManager().getCurrentLevel().getLevelNumber(), 
				Gdx.graphics.getWidth() / 2 - 90, Gdx.graphics.getHeight() / 2 + image.getHeight() /2 + 100);
		
		font.draw(batch, "X", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + image.getHeight() /2 + 30);
		font.draw(batch, ""+GameManager.getGameManager().getNbLifes(), Gdx.graphics.getWidth() / 2 + 50, Gdx.graphics.getHeight() / 2 + image.getHeight() /2 + 30);
		batch.end();		
	}

}
