package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractMushroomCollisionHandler extends AbstractItemCollisionHandler {

	public AbstractMushroomCollisionHandler() {		
	}

	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		// When mario bump a Mushroom, the mushroom direction is changed, and a vertical force is applied
		item.getAcceleration().x = -item.getAcceleration().x;
		item.getAcceleration().y = 0.15f;
	}
	
}
