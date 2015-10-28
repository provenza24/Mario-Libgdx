package com.game.mario.background.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.sprite.AbstractSprite;

public class RightScrollingBackground extends ScrollingBackground {

	public RightScrollingBackground(AbstractSprite followedSprite, Batch batch, BackgroundTypeEnum backgroundType, float pvelocity) {
		super(followedSprite, batch, backgroundType);		
		setX(0);
		this.velocity = pvelocity;
	}

}
