package com.game.mario.collision.tilemap;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.CollisionPoint;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class MarioTilemapCollisionHandler extends AbstractTilemapCollisionHandler {

	private static final float COLLISION_X_CORRECTIF = 10e-5F;
	
	public MarioTilemapCollisionHandler() {		
	}
	
	public void collideWithTilemap(TmxMap tileMap, AbstractSprite sprite) {
						
		sprite.setCollidingCells(new ArrayList<TmxCell>());
		
		boolean onFloorCorrection = false;
		sprite.setMove(new Vector2(sprite.getX() - sprite.getOldPosition().x, sprite.getY() - sprite.getOldPosition().y));
				
		checkBottomMapCollision(tileMap, sprite);		
		
		if (sprite.getOldAcceleration().y == 0 && sprite.getMapCollisionEvent().isCollidingBottom()) {
			// Mario is on a plateform and is still on it
			if (sprite.getState()==SpriteMoveEnum.FALLING) {
				sprite.setState(SpriteMoveEnum.NO_MOVE);
			}
			sprite.setOnFloor(true);			
			sprite.setY((int) sprite.getY() + 1 + COLLISION_X_CORRECTIF);
			sprite.getOldPosition().y = sprite.getY();
			sprite.getAcceleration().y = 0;
			onFloorCorrection = true;
		}
		
		sprite.setMove(new Vector2(sprite.getX() - sprite.getOldPosition().x, sprite.getY() - sprite.getOldPosition().y));		
		Vector2 newPosition = new Vector2(sprite.getX(), sprite.getY());
		
		checkMapCollision(tileMap, sprite);				
										
		if (sprite.getMapCollisionEvent().getCollisionPoints().size()>0) {						
							
			int i=0;
			
			while (sprite.getMapCollisionEvent().getCollisionPoints().size()>0) {
			
				for (CollisionPoint collisionPoint : sprite.getMapCollisionEvent().getCollisionPoints()) {
					
					if (sprite.getMove().y==0 && sprite.getMove().x!=0) {
						newPosition.x = sprite.getMove().x>0 ? (int) (sprite.getX() + sprite.getOffset().x) +  sprite.getOffset().x - COLLISION_X_CORRECTIF : (int) (sprite.getX() + sprite.getWidth() +  sprite.getOffset().x) -  sprite.getOffset().x + COLLISION_X_CORRECTIF;						
						sprite.getAcceleration().x = 0;	
						if (sprite.getState()!=SpriteMoveEnum.FALLING 
								&& sprite.getState()!=SpriteMoveEnum.JUMPING) {
							sprite.setState(SpriteMoveEnum.NO_MOVE);
						}
					}
					
					if (sprite.getMove().y<0 && sprite.getMove().x==0) {						
						newPosition.y = (int) sprite.getY() + 1f;
						sprite.getAcceleration().y = 0;
						sprite.setState(SpriteMoveEnum.NO_MOVE);
						sprite.setOnFloor(true);					
					}
					
					if (sprite.getMove().y>0 && sprite.getMove().x==0) {					
						sprite.addCollidingCell(collisionPoint.getCell());						
						newPosition.y = (int) sprite.getY();
						sprite.getAcceleration().y = 10e-5F;						
						sprite.setState(SpriteMoveEnum.FALLING);						
					}
					
					if (sprite.getMove().x>0 && sprite.getMove().y>0) {
											
						if (sprite.getMapCollisionEvent().isBlockedRight()) {
							newPosition.x = (int) (sprite.getX() + sprite.getOffset().x) + sprite.getOffset().x - COLLISION_X_CORRECTIF;						
							sprite.getAcceleration().x = 0;									
						} else {
							float xDelta = collisionPoint.getPoint().x - collisionPoint.getCell().getX();
							float yDelta = collisionPoint.getPoint().y - collisionPoint.getCell().getY();
																		
							if (xDelta>yDelta) {
								sprite.addCollidingCell(collisionPoint.getCell());
								newPosition.y = (int) sprite.getY();
								sprite.getAcceleration().y = 10e-5F;								
								if (sprite.getState()!=SpriteMoveEnum.FALLING && sprite.getState()!=SpriteMoveEnum.JUMPING) {
									sprite.setState(SpriteMoveEnum.NO_MOVE);
									sprite.setOnFloor(true);		
								} else if (sprite.getState()==SpriteMoveEnum.JUMPING) {
									sprite.setState(SpriteMoveEnum.FALLING);
								}						
							} else {								
								newPosition.x = (int) (sprite.getX() + sprite.getOffset().x) + sprite.getOffset().x - COLLISION_X_CORRECTIF;						
								sprite.getAcceleration().x = 0;					
							}
						}						
						
					}
					
					if (sprite.getMove().x>0 && sprite.getMove().y<0) {
					
						if (sprite.getMapCollisionEvent().isBlockedRight()) {
							newPosition.x = (int) (sprite.getX() + sprite.getOffset().x) + sprite.getOffset().x - COLLISION_X_CORRECTIF;						
							sprite.getAcceleration().x = 0;									
						} else {	
							float xDelta = collisionPoint.getPoint().x - collisionPoint.getCell().getX();
							float yDelta = (collisionPoint.getCell().getY() + 1) - collisionPoint.getPoint().y;
							if (xDelta>yDelta) {				
								newPosition.y = (int) sprite.getY() + 1f;						
								sprite.getAcceleration().y = 0;
								sprite.setOnFloor(true);		
								sprite.setState(SpriteMoveEnum.NO_MOVE);
							} else {
								newPosition.x = (int) (sprite.getX() + sprite.getOffset().x) + sprite.getOffset().x - COLLISION_X_CORRECTIF;						
								sprite.getAcceleration().x = 0;										
							}
						}
												
					}
					
					if (sprite.getMove().x<0 && sprite.getMove().y<0) {							
												
						if (sprite.getMapCollisionEvent().isBlockedLeft()) {						
							newPosition.x = (int) (sprite.getX() + sprite.getWidth() + sprite.getOffset().x) - sprite.getOffset().x + COLLISION_X_CORRECTIF;					
							sprite.getAcceleration().x = 0;				
							
						} else {
							float xDelta = (collisionPoint.getCell().getX()+1) - collisionPoint.getPoint().x;
							float yDelta = (collisionPoint.getCell().getY()+1) - collisionPoint.getPoint().y;
							if (xDelta>yDelta) {
								newPosition.y = (int) sprite.getY() + 1f;
								sprite.getAcceleration().y = 0;
								sprite.setOnFloor(true);
								sprite.setState(SpriteMoveEnum.NO_MOVE);
							} else {
								newPosition.x = (int) (sprite.getX() + sprite.getWidth() + sprite.getOffset().x) - sprite.getOffset().x + COLLISION_X_CORRECTIF;					
								sprite.getAcceleration().x = 0;					
							}
						}												
					}
					
					if (sprite.getMove().x<0 && sprite.getMove().y>0) {
												
						if (sprite.getMapCollisionEvent().isBlockedLeft()) {
							newPosition.x = (int) (sprite.getX() + sprite.getWidth() + sprite.getOffset().x) - sprite.getOffset().x + COLLISION_X_CORRECTIF;						
							sprite.getAcceleration().x = 0;			
						} else {
							float xDelta = (collisionPoint.getCell().getX()+1) - collisionPoint.getPoint().x;
							float yDelta = collisionPoint.getPoint().y - (collisionPoint.getCell().getY());
							if (xDelta>yDelta) {
																
								sprite.addCollidingCell(collisionPoint.getCell());
								newPosition.y = (int) sprite.getY();
								sprite.getAcceleration().y = 10e-5F;
								
								if (sprite.getState()!=SpriteMoveEnum.FALLING && sprite.getState()!=SpriteMoveEnum.JUMPING) {
									sprite.setState(SpriteMoveEnum.NO_MOVE);
									sprite.setOnFloor(true);
								} else if (sprite.getState()==SpriteMoveEnum.JUMPING) {
									sprite.setState(SpriteMoveEnum.FALLING);
								}
																
							} else {								
								newPosition.x = (int) (sprite.getX() + sprite.getWidth() + sprite.getOffset().x) - sprite.getOffset().x + COLLISION_X_CORRECTIF;						
								sprite.getAcceleration().x = 0;					
							}
						}
						
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
			// The collision has been handled, now fix player acceleration
			if (sprite.getMove().x<0 || (sprite.getMove().x==0 && sprite.getDirection()==DirectionEnum.LEFT)) {
				Vector2 leftBottomCorner = new Vector2(sprite.getX() + sprite.getOffset().x - 2*COLLISION_X_CORRECTIF, sprite.getY());
				Vector2 leftTopCorner = new Vector2(sprite.getX() + sprite.getOffset().x - 2*COLLISION_X_CORRECTIF, sprite.getY() + sprite.getHeight());				
				int x = (int) leftBottomCorner.x;
				int y = (int) leftBottomCorner.y;
				boolean isCollision = tileMap.isCollisioningTileAt(x, y);
				if (!isCollision) {
					x = (int) leftTopCorner.x;
					y = (int) leftTopCorner.y;
					isCollision = tileMap.isCollisioningTileAt(x, y);
					if (!isCollision && sprite.getSizeState()>0) {
						Vector2 leftMiddle = new Vector2(sprite.getX() + sprite.getOffset().x - 2*COLLISION_X_CORRECTIF, sprite.getY() + sprite.getHeight() / 2);
						x = (int) leftMiddle.x;
						y = (int) leftMiddle.y;
						isCollision = tileMap.isCollisioningTileAt(x, y);
					}
				}				
				sprite.getAcceleration().x = isCollision ? 0 : sprite.getOldAcceleration().x;
			} else {
				Vector2 rightBottomCorner = new Vector2(sprite.getX() + sprite.getWidth() + sprite.getOffset().x + 2*COLLISION_X_CORRECTIF, sprite.getY());
				Vector2 rightTopCorner = new Vector2(sprite.getX() + sprite.getWidth() + sprite.getOffset().x + 2*COLLISION_X_CORRECTIF, sprite.getY() + sprite.getHeight());			
				int x = (int) rightBottomCorner.x;
				int y = (int) rightBottomCorner.y;
				boolean isCollision = tileMap.isCollisioningTileAt(x, y);
				if (!isCollision) {
					x = (int) rightTopCorner.x;
					y = (int) rightTopCorner.y;
					isCollision = tileMap.isCollisioningTileAt(x, y);
					if (!isCollision && sprite.getSizeState()>0) {
						Vector2 rightMiddle = new Vector2(sprite.getX() + sprite.getWidth() + sprite.getOffset().x + 2*COLLISION_X_CORRECTIF, sprite.getY() + sprite.getHeight() / 2);
						x = (int) rightMiddle.x;
						y = (int) rightMiddle.y;
						isCollision = tileMap.isCollisioningTileAt(x, y);
					}
				}				
				sprite.getAcceleration().x = isCollision ? 0 : sprite.getOldAcceleration().x;
			}										
		}  else {
			
			if (sprite.getMove().y < 0 && !onFloorCorrection) {						
				sprite.setState(SpriteMoveEnum.FALLING);
				sprite.setOnFloor(false);
			} else {
				checkUpperMapCollision(tileMap, sprite);
				if (sprite.getState()==SpriteMoveEnum.JUMPING && sprite.getMapCollisionEvent().isCollidingUpperBlock()) {					
					TmxCell collidingCell = sprite.getMapCollisionEvent().getCollisionPoints().get(0).getCell();
					if (sprite.getY()-collidingCell.getY()<0.2f) {
						sprite.addCollidingCell(sprite.getMapCollisionEvent().getCollisionPoints().get(0).getCell());
						newPosition.y = (int) sprite.getY();
						sprite.setY(newPosition.y);
						sprite.getAcceleration().y = 10e-5F;
						sprite.setState(SpriteMoveEnum.FALLING);
					}														
				}
			}
		}		
				

	}
	
	@Override
	protected void checkMapCollision(TmxMap tilemap, AbstractSprite sprite) {
		
		super.checkMapCollision(tilemap, sprite);
		
		if (sprite.getSizeState() > 0) {
			Vector2 rightMiddle = new Vector2(sprite.getX() + sprite.getWidth() + sprite.getOffset().x, sprite.getY() + sprite.getHeight() / 2);
			int x =(int) rightMiddle.x;
			int y = (int) rightMiddle.y;
			boolean isCollision = tilemap.isCollisioningTileAt(x, y);
			sprite.getMapCollisionEvent().setCollidingMiddleRight(isCollision);
			if (isCollision) {
				sprite.getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(rightMiddle, new TmxCell(tilemap.getTileAt(x, y), x, y)));
			}

			Vector2 leftMiddle = new Vector2(sprite.getX() + sprite.getOffset().x, sprite.getY() + sprite.getHeight() / 2);
			x = (int) leftMiddle.x;
			y = (int) leftMiddle.y;
			isCollision = tilemap.isCollisioningTileAt(x, y);
			sprite.getMapCollisionEvent().setCollidingMiddleLeft(isCollision);
			if (isCollision) {
				sprite.getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(leftMiddle, new TmxCell(tilemap.getTileAt(x, y), x, y)));
			}
		}
	}
	
	protected void checkUpperMapCollision(TmxMap tilemap, AbstractSprite sprite) {
		
		sprite.reinitMapCollisionEvent();
		sprite.getMapCollisionEvent().reinitCollisionPoints();
		
		Vector2 leftTopCorner = new Vector2(sprite.getX() + sprite.getOffset().x, sprite.getY() + sprite.getHeight());
		Vector2 rightTopCorner = new Vector2(sprite.getX() + sprite.getWidth() + sprite.getOffset().x, sprite.getY() + sprite.getHeight());
		Vector2 middleTopCorner = new Vector2(sprite.getX() + sprite.getWidth()/2 + sprite.getOffset().x, sprite.getY() + sprite.getHeight());
		
		int x = (int) leftTopCorner.x;
		int y = (int) leftTopCorner.y;
		boolean isCollision = tilemap.isCollisioningInvisibleTileAt(x, y);
		sprite.getMapCollisionEvent().setCollidingTopLeft(isCollision);
		if (isCollision) {
			sprite.getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(leftTopCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}
		
		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;
		isCollision = tilemap.isCollisioningInvisibleTileAt(x, y);
		sprite.getMapCollisionEvent().setCollidingTopRight(isCollision);
		if (isCollision) {
			sprite.getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(rightTopCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}
		
		x = (int) middleTopCorner.x;
		y = (int) middleTopCorner.y;
		isCollision = tilemap.isCollisioningInvisibleTileAt(x, y);
		sprite.getMapCollisionEvent().setCollidingMiddleTop(isCollision);
		if (isCollision) {
			sprite.getMapCollisionEvent().getCollisionPoints().add(new CollisionPoint(middleTopCorner, new TmxCell(tilemap.getTileAt(x, y), x, y)));
		}
	
	}


}
