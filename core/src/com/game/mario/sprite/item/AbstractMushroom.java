package com.game.mario.sprite.item;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractItem;

public abstract class AbstractMushroom extends AbstractItem {

	public AbstractMushroom(float x, float y) {
		super(x, y, new Vector2(1, 1), new Vector2(0.1f, 0.1f));
		alive = true;				
		acceleration.x = 4f;				
	}

}
