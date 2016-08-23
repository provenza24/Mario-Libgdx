package com.game.mario.sprite.item;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.tilemap.StarTilemapCollisionHandler;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Star extends AbstractItem {
	
	public Star(float x, float y) {
		super(x, y, new Vector2(1, 1), new Vector2(0.1f, 0.4f));
		GRAVITY_COEF = 0.010f;		
		acceleration.x = 2.9f;
		acceleration.y = 0;
		
		tilemapCollisionHandler = new StarTilemapCollisionHandler();
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.STAR, 0, 3, 0.03f);		
	}

	@Override
	public ItemEnum getType() {
		return ItemEnum.STAR;
	}
		
}
