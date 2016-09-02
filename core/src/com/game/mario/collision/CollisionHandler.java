package com.game.mario.collision;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.collision.plateform.AbstractPlateformCollisionHandler;
import com.game.mario.collision.plateform.IPlateformCollisionHandler;
import com.game.mario.collision.upperblock.AbstractUpperBlockCollisionHandler;
import com.game.mario.collision.upperblock.IUpperBlockCollisionHandler;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sprite.AbstractEnemy;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class CollisionHandler {

	private static final CollisionHandler collisionHandler = new CollisionHandler();

	public void collideEnemies(AbstractEnemy enemy1, AbstractEnemy enemy2) {
		
		if (enemy1.isCollidableWithEnnemies() && enemy2.isCollidableWithEnnemies() 
				&& enemy1.getBounds().overlaps(enemy2.getBounds()) && !enemy1.isBumped() && !enemy2.isBumped()) {
		
			boolean isKoopaSliding = false;
			if (enemy1.getEnemyType()==EnemyTypeEnum.KOOPA && enemy1.getState()==SpriteMoveEnum.SLIDING) {
				enemy2.bump();	
				isKoopaSliding = true;
			}
			if (enemy2.getEnemyType()==EnemyTypeEnum.KOOPA && enemy2.getState()==SpriteMoveEnum.SLIDING) {
				enemy1.bump();
				isKoopaSliding = true;
			}
			
			if (!isKoopaSliding) {
				enemy1.setOldPosition(enemy1.getOldPosition());				
				enemy1.getAcceleration().x = -enemy1.getAcceleration().x;
				enemy2.getAcceleration().x = -enemy2.getAcceleration().x;
			}
		}						
	}

	public static CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	public void collideMarioWithUpperBlock(Mario mario, TmxMap tileMap, Stage stage) {		
			
		TmxCell collidingCell = null;
		List<TmxCell> collidingCells = mario.getCollidingCells();
		if (collidingCells.size()>0) {
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
	
	public void collideMarioWithItem(Mario mario, AbstractItem item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		IItemCollisionHandler collisionHandler = AbstractItemCollisionHandler.getHandler(item);
		if (collisionHandler!=null) {			
			collisionHandler.collide(mario, item, camera, scrollingBackgrounds);
		}
	}
	
	public void collideMarioWithPlateform(Mario mario, AbstractMetalPlateform plateform) {
		IPlateformCollisionHandler collisionHandler = AbstractPlateformCollisionHandler.getHandler(plateform);
		if (collisionHandler!=null) {			
			collisionHandler.collide(mario, plateform);
		}
	}

}
