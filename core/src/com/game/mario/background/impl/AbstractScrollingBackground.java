package com.game.mario.background.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.util.ResourcesLoader;

public abstract class AbstractScrollingBackground extends Sprite implements IScrollingBackground {

	protected static final Map<BackgroundTypeEnum, Texture> BACKGROUND_IMAGES = new HashMap<BackgroundTypeEnum, Texture>();
	
	protected float velocity;
	
	protected Batch batch;
	
	protected AbstractSprite followedSprite;
	
	protected int width;
	
	protected boolean enabled;
		
	static {
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.CLOUDS, ResourcesLoader.OVERGROUND_CLOUDS);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.HILLS, ResourcesLoader.OVERGROUND_HILLS);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.WATERFALL, ResourcesLoader.WATERFALL);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.UNDERGROUND, ResourcesLoader.UNDERGROUND);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.BONUS, ResourcesLoader.BONUS);				
	}
	
	public void changeImage(BackgroundTypeEnum backgroundTypeEnum) {
	
		if (backgroundTypeEnum!=null) {
			 setRegion(BACKGROUND_IMAGES.get(backgroundTypeEnum));	
		}
	}
		
	
	public AbstractScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType) {
		super(BACKGROUND_IMAGES.get(backgroundType));
		this.batch = batch;
		this.followedSprite = followedSprite;
		this.enabled = true;
		this.width = getTexture().getWidth();
	}
	
	public void update() {
						
		float xMarioMove = (followedSprite.getX() - followedSprite.getOldPosition().x);
		if (xMarioMove>0) {			
			setX(getX() + xMarioMove * velocity);			
		}							
		if (velocity<0 && getX() <= 0){
			setX(width);
		} else if (velocity>0 && getX()>=width) {
			setX(0);
		}
	}
	
	public void render() {
		if (enabled) {
			batch.begin();
			// draw the first background
			batch.draw(getTexture(), getX() - width, 0);
			// draw the second background
			batch.draw(getTexture(), getX(), 0);
			batch.end();
		}		
	}

	public void toggleEnabled() {
		enabled = !enabled;
	}
	
}
