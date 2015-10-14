package com.mygdx.game.mario.background.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.mario.background.IScrollingBackground;
import com.mygdx.game.mario.enums.BackgroundTypeEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;

public abstract class ScrollingBackground implements IScrollingBackground {

	protected static final Map<BackgroundTypeEnum, String> BACKGROUND_IMAGES = new HashMap<BackgroundTypeEnum, String>();
	
	protected float velocity;
	
	protected Batch batch;
	
	protected AbstractSprite followedSprite;
	
	protected Texture background;
	
	protected float x;
	
	protected int width;
		
	static {
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.OVERWORLD, "overworld-800.gif");
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.UNDERWORLD, "underworld.png");
	}
	
	public ScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType) {
		this.batch = batch;
		this.followedSprite = followedSprite;
		background = new Texture(BACKGROUND_IMAGES.get(backgroundType));
		width = background.getWidth();
	}
	
	public void update() {
		
		float xMarioMove = (followedSprite.getX() - followedSprite.getOldPosition().x);
		if (xMarioMove>0) {			
			x += xMarioMove * velocity;			
		}							
		if (velocity<0 && x <= 0){
			x = width;
		} else if (velocity>0 && x>=width) {
			x = 0;
		}
	}
	
	public void render() {
		batch.begin();
		// draw the first background
		batch.draw(background, x - width, 0);
		// draw the second background
		batch.draw(background, x, 0);
		batch.end();
	}
	
}
