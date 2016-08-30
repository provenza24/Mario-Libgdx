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

/**
 * Abstract class which provides scrolling support
 * Provided images width must be equals to two times the screen width
 * A velocity is given to backgrounds to temporize the automatic scrolling
 */
public abstract class AbstractScrollingBackground extends Sprite implements IScrollingBackground {

	/** Map containing backgrounds images */
	protected static final Map<BackgroundTypeEnum, Texture> BACKGROUND_IMAGES = new HashMap<BackgroundTypeEnum, Texture>();
	
	/** Horizontal velocity */
	protected float velocity;
	
	/** Batch used to draw this background */
	protected Batch batch;
	
	/** Followed sprite */
	protected AbstractSprite followedSprite;
	
	/** Background width */
	protected int width;
	
	/** Indicator to determine if background is enabled / disabled */
	protected boolean enabled;
		
	static {		
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.HILLS, ResourcesLoader.OVERGROUND_HILLS);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.WATERFALL, ResourcesLoader.WATERFALL);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.UNDERGROUND, ResourcesLoader.UNDERGROUND);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.BONUS, ResourcesLoader.BONUS);
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.CASTLE, ResourcesLoader.CASTLE);	
	}
		
	public AbstractScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType) {
		super(BACKGROUND_IMAGES.get(backgroundType));
		this.batch = batch;
		this.followedSprite = followedSprite;
		this.enabled = true;
		this.width = getTexture().getWidth();
	}
	
	@Override
	public void changeImage(BackgroundTypeEnum backgroundTypeEnum) {
	
		if (backgroundTypeEnum!=null) {
			 setRegion(BACKGROUND_IMAGES.get(backgroundTypeEnum));	
		}
	}
	
	@Override
	public void update() {
						
		float xMarioMove = (followedSprite.getX() - followedSprite.getOldPosition().x);
		if (xMarioMove>0) {
			// Scroll only if player is going to the right of the screen
			setX(getX() + xMarioMove * velocity);			
		}				
		// Reset image position when needed
		if (getX() <= 0){			
			setX(width);
		} else if (getX()>=width) {
			setX(0);
		}
	}
	
	@Override
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

	@Override
	public void toggleEnabled() {
		enabled = !enabled;
	}
	
}
