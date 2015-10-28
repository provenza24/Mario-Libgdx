package com.game.mario.collision.item;

import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public interface IItemCollisionHandler {

	public void collide(Mario mario, AbstractSprite item, GameCamera camera);
}
