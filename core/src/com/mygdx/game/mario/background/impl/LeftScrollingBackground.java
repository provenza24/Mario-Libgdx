package com.mygdx.game.mario.background.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.mario.sprite.impl.Mario;

public class LeftScrollingBackground extends ScrollingBackground {

	public LeftScrollingBackground(Mario mario, Batch batch, String texture, float pvelocity) {
		super(mario, batch, texture);		
		this.x = width;
		this.velocity = -pvelocity;
	}

}
