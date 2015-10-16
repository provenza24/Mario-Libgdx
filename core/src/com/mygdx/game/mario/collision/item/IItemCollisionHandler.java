package com.mygdx.game.mario.collision.item;

import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public interface IItemCollisionHandler {

	public void collide(Mario mario, AbstractSprite item);
}
