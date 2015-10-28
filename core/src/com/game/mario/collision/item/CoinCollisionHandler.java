package com.game.mario.collision.item;

import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class CoinCollisionHandler extends AbstractItemCollisionHandler {

	public CoinCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera) {		
		super.collide(mario, item, camera);
		mario.addCoin();
	}

}
