package com.game.mario.sprite.item;

import com.game.mario.enums.ItemEnum;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class RedMushroom extends AbstractMushroom {

	public RedMushroom(float x, float y) {
		super(x, y);			
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.RED_MUSHROOM, 0, 1, 0);
	}

	@Override
	public ItemEnum getType() {		
		return ItemEnum.RED_MUSHROOM;
	}
		
}
