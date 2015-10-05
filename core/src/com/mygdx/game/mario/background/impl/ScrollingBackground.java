package com.mygdx.game.mario.background.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.mario.background.IScrollingBackground;
import com.mygdx.game.mario.sprite.impl.Mario;

public abstract class ScrollingBackground implements IScrollingBackground {

	protected float velocity;
	
	protected Batch batch;
	
	protected Mario mario;
	
	protected Texture background;
	
	protected float x;
	
	protected int width;
		
	public ScrollingBackground(Mario mario, Batch batch, String texture) {
		this.batch = batch;
		this.mario = mario;
		background = new Texture(texture);
		width = background.getWidth();
	}
	
	public void update() {
		
		float xMarioMove = (mario.getX() - mario.getOldPosition().x);
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
