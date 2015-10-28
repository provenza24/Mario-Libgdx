package com.game.mario.collision;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.collision.upperblock.AbstractUpperBlockCollisionHandler;
import com.game.mario.collision.upperblock.IUpperBlockCollisionHandler;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class CollisionHandler {

	private static final CollisionHandler collisionHandler = new CollisionHandler();

	public void collideEnemies(AbstractSprite enemy1, AbstractSprite enemy2) {
		if (enemy1.getBounds().overlaps(enemy2.getBounds())) {
			enemy1.setOldPosition(enemy2.getOldPosition());
			enemy1.getAcceleration().x = -enemy1.getAcceleration().x;
			enemy2.getAcceleration().x = -enemy2.getAcceleration().x;
		}
	}

	public static CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	public void collideMarioWithUpperBlock(Mario mario, TmxMap tileMap, Stage stage) {		
		if (mario.getMapCollisionEvent().isCollidingTop()) {
			TmxCell collidingCell = null;
			List<TmxCell> collidingCells = mario.getCollidingCells();
			collidingCell = collidingCells.get(0);
			if (collidingCells.size() > 1) {
				float xMarioHalf = mario.getX() + mario.getWidth() / 2;
				if (!(xMarioHalf <= collidingCell.getX() + 1)) {
					collidingCell = collidingCells.get(1);
				}
			}			
			IUpperBlockCollisionHandler collisionHandler = AbstractUpperBlockCollisionHandler.getHandler(collidingCell.getCell().getTile().getId());
			if (collisionHandler!=null) {			
				collisionHandler.handle(tileMap, collidingCell, stage);
			}
		}
	}
	
	public void collideMarioWithItem(Mario mario, AbstractSprite item, GameCamera camera) {
		IItemCollisionHandler collisionHandler = AbstractItemCollisionHandler.getHandler(item);
		if (collisionHandler!=null) {			
			collisionHandler.collide(mario, item, camera);
		}
	}

}
