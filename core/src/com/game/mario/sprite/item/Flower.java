package com.game.mario.sprite.item;

import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Flower extends AbstractItem {
	
	public Flower(float x, float y) {
		super(x, y);				
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FLOWER, 0, 4, 0.01f);		
	}	

	@Override
	public ItemEnum getType() {		
		return ItemEnum.FLOWER;
	}
		
}
