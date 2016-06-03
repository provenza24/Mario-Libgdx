package com.game.mario.collision.tilemap;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.CollisionPoint;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class BowserTilemapCollisionHandler extends AbstractTilemapCollisionHandler {

	private static final float COLLISION_X_CORRECTIF = 10e-5F;
	
	public BowserTilemapCollisionHandler() {						
	}
			
	public void collideWithTilemap(TmxMap tileMap, AbstractSprite sprite) {
		
		sprite.setCollidingCells(new ArrayList<TmxCell>());
		
		boolean onFloorCorrection = false;
		Vector2 move = new Vector2(sprite.getAcceleration().y - sprite.getOldPosition().x, sprite.getY() - sprite.getOldPosition().y);
				
		checkBottomMapCollision(tileMap, sprite);		
		
		if (sprite.getOldAcceleration().y == 0 && sprite.getMapCollisionEvent().isCollidingBottom()) {			
			sprite.setOnFloor(true);
			sprite.setY((int) sprite.getY() + 1);
			sprite.getOldPosition().y = sprite.getY();
			sprite.getAcceleration().y = 0;
			onFloorCorrection = true;
		}
		
		move = new Vector2(sprite.getX() - sprite.getOldPosition().x, sprite.getY() - sprite.getOldPosition().y);		
		Vector2 newPosition = new Vector2(sprite.getX(), sprite.getY());
		
		checkMapCollision(tileMap, sprite);				
											
		if (sprite.getMapCollisionEvent().getCollisionPoints().size()>0) {
												
			int i=0;
			
			while (sprite.getMapCollisionEvent().getCollisionPoints().size()>0) {			
				
				for (CollisionPoint collisionPoint : sprite.getMapCollisionEvent().getCollisionPoints()) {
					
					if (move.y==0 && move.x!=0) {						
						newPosition.x = move.x>0 ? (int) (sprite.getX() + sprite.getOffset().x) + sprite.getOffset().x - COLLISION_X_CORRECTIF : (int) (sprite.getX() + sprite.getWidth() + sprite.getOffset().x) - sprite.getOffset().x + COLLISION_X_CORRECTIF;																	
					}
					
					if (move.y<0 && move.x==0) {						
						newPosition.y = (int) sprite.getY() + 1f;
						sprite.getAcceleration().y = 0;												
						sprite.setOnFloor(true);						
						sprite.setState(SpriteStateEnum.WALKING);						
					}					
										
					if (move.x>0 && move.y<0) {											 
						newPosition.y = (int) sprite.getY() + 1f;						
						sprite.getAcceleration().y = 0;
						sprite.setOnFloor(true);		
						sprite.setState(SpriteStateEnum.WALKING);			
					}
					
					if (move.x<0 && move.y<0) {	
						
						newPosition.y = (int) sprite.getY() + 1f;
						sprite.getAcceleration().y = 0;
						sprite.setOnFloor(true);
						sprite.setState(SpriteStateEnum.WALKING);																							
					}														
														
				}
				sprite.setX(newPosition.x);
				sprite.setY(newPosition.y);
				checkMapCollision(tileMap, sprite);
				i++;
				if (i>10) {
					System.out.println("Erreur de collision ?"+i);
				}
				
			}	
											
		}  else {
			if (move.y < 0 && !onFloorCorrection) {				
				sprite.setOnFloor(false);
				sprite.setState(SpriteStateEnum.FALLING);				
			}
		}			
		
	}		

}
