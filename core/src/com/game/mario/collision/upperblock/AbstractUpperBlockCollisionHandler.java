package com.game.mario.collision.upperblock;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUpperBlockCollisionHandler implements IUpperBlockCollisionHandler  {

	private static Map<Integer, IUpperBlockCollisionHandler> handlers = new HashMap<Integer, IUpperBlockCollisionHandler>();
	
	static {
		handlers.put(4, new WallCollisionHandler());
		handlers.put(64, new WallCollisionHandler());
		handlers.put(7, new MysteryBlockCollisionHandler());
		handlers.put(8, new MysteryBlockCollisionHandler());
	}
	
	public static IUpperBlockCollisionHandler getHandler(Integer tileId) {
		return handlers.get(tileId);
	}
	
}
