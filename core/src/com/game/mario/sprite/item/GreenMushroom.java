package com.game.mario.sprite.item;

import com.game.mario.enums.ItemEnum;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class GreenMushroom extends AbstractMushroom {

	public GreenMushroom(float x, float y) {
		super(x, y);
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.GREEN_MUSHROOM, 0, 1, 0);		
	}

	@Override
	public ItemEnum getType() {		
		return ItemEnum.GREEN_MUSHROOM;
	}

}
