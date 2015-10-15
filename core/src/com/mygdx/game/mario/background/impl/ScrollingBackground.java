package com.mygdx.game.mario.background.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.mario.background.IScrollingBackground;
import com.mygdx.game.mario.enums.BackgroundTypeEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;

public abstract class ScrollingBackground extends Sprite implements IScrollingBackground {

	protected static final Map<BackgroundTypeEnum, String> BACKGROUND_IMAGES = new HashMap<BackgroundTypeEnum, String>();
	
	protected float velocity;
	
	protected Batch batch;
	
	protected AbstractSprite followedSprite;
	

	protected int width;
		
	static {
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.OVERWORLD, "overworld.gif");
		BACKGROUND_IMAGES.put(BackgroundTypeEnum.UNDERWORLD, "underworld.png");
	}
	
	public ScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType) {
		super(new Texture(BACKGROUND_IMAGES.get(backgroundType)), 512, 480);
		this.batch = batch;
		this.followedSprite = followedSprite;
		width = getTexture().getWidth();
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
		batch.begin();
		// draw the first background
		batch.draw(getTexture(), getX() - width, 0);
		// draw the second background
		batch.draw(getTexture(), getX(), 0);
		batch.end();
	}
	
}
