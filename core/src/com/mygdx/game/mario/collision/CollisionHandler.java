package com.mygdx.game.mario.collision;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.mario.collision.upperblock.AbstractUpperBlockCollisionHandler;
import com.mygdx.game.mario.collision.upperblock.IUpperBlockCollisionHandler;
import com.mygdx.game.mario.sprite.AbstractGameSprite;
import com.mygdx.game.mario.sprite.impl.Mario;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class CollisionHandler {

	private static final CollisionHandler collisionHandler = new CollisionHandler();

	public void collideEnemies(AbstractGameSprite enemy1, AbstractGameSprite enemy2) {
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

}
