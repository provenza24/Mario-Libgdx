package com.mygdx.game.mario.collision;

import com.mygdx.game.mario.sprite.AbstractGameSprite;

public class CollisionHandler {

	private static final CollisionHandler collisionHandler = new CollisionHandler();
	
	public void collideEnemies(AbstractGameSprite enemy1, AbstractGameSprite enemy2) {
		if (enemy1.getBounds().overlaps(enemy2.getBounds())) {							
			enemy1.setOldPosition(enemy2.getOldPosition());
			enemy1.getAcceleration().x = - enemy1.getAcceleration().x;
			enemy2.getAcceleration().x = - enemy2.getAcceleration().x;
		}
	}
	
	public static CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}
	
}
