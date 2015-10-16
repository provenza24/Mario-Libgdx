package com.mygdx.game.mario.collision.item;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.item.Mushroom;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractItemCollisionHandler implements IItemCollisionHandler {

	private static Map<Class<?>, IItemCollisionHandler> handlers = new HashMap<Class<?>, IItemCollisionHandler>();

	static {
		handlers.put(Mushroom.class, new MushroomCollisionHandler());		
	}

	public static IItemCollisionHandler getHandler(AbstractSprite sprite) {		
		return handlers.get(sprite.getClass());
	}

	public AbstractItemCollisionHandler() {

	}

	@Override
	public void collide(Mario mario, AbstractSprite item) {
		item.setDeletable(true);
	}

}
