package com.mygdx.game.mario.background.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.mario.sprite.impl.Mario;

public class RightScrollingBackground extends ScrollingBackground {

	public RightScrollingBackground(Mario mario, Batch batch, String texture, float pvelocity) {
		super(mario, batch, texture);		
		this.x = 0;
		this.velocity = pvelocity;
	}

}
