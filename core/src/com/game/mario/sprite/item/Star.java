package com.game.mario.sprite.item;

import com.game.mario.action.ActionFacade;
import com.game.mario.collision.tilemap.StarTilemapCollisionHandler;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Star extends AbstractItem {
	
	public Star(float x, float y) {
		super(x, y);
		GRAVITY_COEF = 0.010f;
		offset.x = 0.1f;
		offset.y = 0.4f;
		setSize(1 - 2*offset.x, 1f - offset.y);
		acceleration.x = 2.9f;
		acceleration.y = 0;
		
		tilemapCollisionHandler = new StarTilemapCollisionHandler();
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.STAR, 0, 4, 0.01f);		
	}

	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));		
	}

	@Override
	public ItemEnum getType() {
		return ItemEnum.STAR;
	}
		
}
