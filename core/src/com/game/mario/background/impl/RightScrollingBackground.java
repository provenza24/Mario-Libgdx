package com.game.mario.background.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.sprite.AbstractSprite;

/**
 * Left to right scrolling background support
 *
 */
public class RightScrollingBackground extends AbstractScrollingBackground {

	public RightScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType, float pvelocity) {
		super(followedSprite, batch, backgroundType);		
		setX(0);
		this.velocity = pvelocity;
	}

}
