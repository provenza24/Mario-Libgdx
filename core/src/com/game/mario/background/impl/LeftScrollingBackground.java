package com.game.mario.background.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.sprite.AbstractSprite;

/**
 * Right to left scrolling image support
 */
public class LeftScrollingBackground extends AbstractScrollingBackground {

	public LeftScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType, float pvelocity) {
		super(followedSprite, batch, backgroundType);		
		setX(width);
		this.velocity = -pvelocity;
	}

}
