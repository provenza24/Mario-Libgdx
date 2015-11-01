package com.game.mario.collision.item;

import java.util.HashMap;
import java.util.Map;

import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.item.Mushroom;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.item.TransferItemDown;
import com.game.mario.sprite.tileobject.item.TransferItemRight;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractItemCollisionHandler implements IItemCollisionHandler {

	private static Map<Class<?>, IItemCollisionHandler> handlers = new HashMap<Class<?>, IItemCollisionHandler>();

	static {
		handlers.put(Mushroom.class, new MushroomCollisionHandler());		
		handlers.put(TransferItemDown.class, new TransferCollisionHandler());
		handlers.put(TransferItemRight.class, new TransferCollisionHandler());
		handlers.put(Coin.class, new CoinCollisionHandler());
	}

	public static IItemCollisionHandler getHandler(AbstractSprite sprite) {		
		return handlers.get(sprite.getClass());
	}

	public AbstractItemCollisionHandler() {

	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera) {
		item.setDeletable(true);
	}

}