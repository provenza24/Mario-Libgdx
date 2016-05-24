package com.game.mario.collision;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.collision.upperblock.AbstractUpperBlockCollisionHandler;
import com.game.mario.collision.upperblock.IUpperBlockCollisionHandler;
import com.game.mario.enums.EnemyStateEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.enemy.AbstractEnemy;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class CollisionHandler {

	private static final CollisionHandler collisionHandler = new CollisionHandler();

	public void collideEnemies(AbstractEnemy enemy1, AbstractEnemy enemy2) {
		
		if (enemy1.getBounds().overlaps(enemy2.getBounds()) && !enemy1.isBumped() && !enemy2.isBumped()) {
		
			boolean isKoopaSliding = false;
			if (enemy1.getEnemyType()==EnemyTypeEnum.KOOPA && enemy1.getEnemyState()==EnemyStateEnum.SLIDING) {
				enemy2.bump();	
				isKoopaSliding = true;
			}
			if (enemy2.getEnemyType()==EnemyTypeEnum.KOOPA && enemy2.getEnemyState()==EnemyStateEnum.SLIDING) {
				enemy1.bump();
				isKoopaSliding = true;
			}
			
			if (!isKoopaSliding) {
				enemy1.setOldPosition(enemy2.getOldPosition());
				enemy1.getAcceleration().x = -enemy1.getAcceleration().x;
				enemy2.getAcceleration().x = -enemy2.getAcceleration().x;
			}
		}						
	}

	public static CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	public void collideMarioWithUpperBlock(Mario mario, TmxMap tileMap, Stage stage) {		
		/*if (mario.getMapCollisionEvent().isCollidingTop()) {
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
		}*/
	}
	
	public void collideMarioWithItem(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		IItemCollisionHandler collisionHandler = AbstractItemCollisionHandler.getHandler(item);
		if (collisionHandler!=null) {			
			collisionHandler.collide(mario, item, camera, scrollingBackgrounds);
		}
	}

}
