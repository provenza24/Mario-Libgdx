package com.mygdx.game.mario.collision.item;

import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public class MushroomCollisionHandler extends AbstractItemCollisionHandler {

	public MushroomCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item) {		
		super.collide(mario, item);
	}

}
