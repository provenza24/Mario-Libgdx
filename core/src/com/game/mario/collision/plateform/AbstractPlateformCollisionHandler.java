package com.game.mario.collision.plateform;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.AscendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.DescendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.HorizontalMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.VerticalMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractPlateformCollisionHandler implements IPlateformCollisionHandler {

	private static Map<Class<?>, IPlateformCollisionHandler> handlers = new HashMap<Class<?>, IPlateformCollisionHandler>();

	private static final float COLLISION_X_CORRECTIF = 10e-5F;
	
	static {
		handlers.put(AscendingMetalPlateform.class, new MetalPlateformCollisionHandler());
		handlers.put(DescendingMetalPlateform.class, new MetalPlateformCollisionHandler());
		handlers.put(VerticalMetalPlateform.class, new MetalPlateformCollisionHandler());
		handlers.put(HorizontalMetalPlateform.class, new HorizontalPlateformCollisionHandler());		
	}

	public static IPlateformCollisionHandler getHandler(AbstractSprite sprite) {		
		return handlers.get(sprite.getClass());
	}

	public AbstractPlateformCollisionHandler() {

	}
	
	public void collide(Mario mario, AbstractMetalPlateform plateform) {
						
		
		if (plateform.isStuck()) {		
			mario.getAcceleration().y = 0;
			mario.setY(plateform.getY()+plateform.getHeight());
		
		} else {							
			
			if (mario.getMove().x==0 && mario.getMove().y<0) {				
				if (mario.getOldPosition().y> plateform.getY()) {
					stuckMario(mario, plateform);
				}
			} else if (mario.getMove().y==0 && mario.getMove().x==0 && mario.getState()==SpriteMoveEnum.NO_MOVE) {				
				stuckMario(mario, plateform);			
			} else if (mario.getMove().x>0 && mario.getMove().y<0) {								
				handleFallingRight(mario, plateform);
			} else if (mario.getMove().x<0 && mario.getMove().y<0) {				
				handleFallingLeft(mario, plateform);				
			} else if (mario.getMove().y>0 && mario.getMove().x==0) {				
				handleFallingStraight(mario, plateform);					
			}  else if (mario.getMove().y==0 && mario.getMove().x!=0) {				
				handleRunning(mario, plateform);							
			}  else if (mario.getMove().x>0 && mario.getMove().y>0) {					
				handleJumpingRight(mario, plateform);				
			} else if (mario.getMove().x<0 && mario.getMove().y>0) {											
				handleJumpingLeft(mario, plateform);
				
			}
		}											
							
	}

	private void handleJumpingLeft(Mario mario, AbstractMetalPlateform plateform) {
		
		float deltaX = plateform.getX() + plateform.getWidth() - mario.getX()+mario.getOffset().x;
		float deltaY = mario.getY()+mario.getHeight()-plateform.getY();		
		
		if (deltaY>deltaX) {
			mario.setX(plateform.getX() + plateform.getWidth() - mario.getOffset().x + COLLISION_X_CORRECTIF);
			mario.getAcceleration().x = 0;				
		} else {
			handleFallingStraight(mario, plateform);				
		}
	}

	private void handleJumpingRight(Mario mario, AbstractMetalPlateform plateform) {
								
		float deltaX = mario.getX()+mario.getWidth()+mario.getOffset().x - plateform.getX();
		float deltaY = mario.getY()+mario.getHeight()-plateform.getY();
		
		if (deltaY>deltaX) {
			mario.setX(plateform.getX() - (mario.getWidth() + mario.getOffset().x) - COLLISION_X_CORRECTIF);
			mario.getAcceleration().x = 0;			
		} else {			
			handleFallingStraight(mario, plateform);				
		}
	}

	private void handleFallingStraight(Mario mario, AbstractMetalPlateform plateform) {
		mario.setY(plateform.getY()-(mario.getHeight()+mario.getOffset().y));				
		mario.getAcceleration().y = 10e-5F;						
		mario.setState(SpriteMoveEnum.FALLING);
	}

	private void handleRunning(Mario mario, AbstractMetalPlateform plateform) {
		if (mario.getMove().x >0) {
			handleFallingRight(mario, plateform);
		} else {
			Vector2 plateformRightTop = new Vector2(plateform.getX() + plateform.getWidth(), plateform.getY() + plateform.getHeight());
			Vector2 plateformRightBottom = new Vector2(plateform.getX() + plateform.getWidth(), plateform.getY());
			
			if ((mario.getBounds().contains(plateformRightTop) || mario.getBounds().contains(plateformRightBottom)) 
					&& mario.getOldPosition().x+mario.getOffset().x>=plateform.getX()+plateform.getWidth()) {
				mario.setX(plateform.getX() + plateform.getWidth() - mario.getOffset().x + COLLISION_X_CORRECTIF);
				mario.getAcceleration().x = 0;				
			} else {
				if (mario.getOldPosition().y> plateform.getY()) {
					stuckMario(mario, plateform);
				}
			}
		}					
		if (mario.getState()!=SpriteMoveEnum.FALLING && mario.getState()!=SpriteMoveEnum.JUMPING) {
			mario.setState(SpriteMoveEnum.NO_MOVE);
		}
	}

	private void handleFallingLeft(Mario mario, AbstractMetalPlateform plateform) {
		Vector2 plateformRightTop = new Vector2(plateform.getX() + plateform.getWidth(), plateform.getY() + plateform.getHeight());
		Vector2 plateformRightBottom = new Vector2(plateform.getX() + plateform.getWidth(), plateform.getY());				
		if ((mario.getBounds().contains(plateformRightTop) || mario.getBounds().contains(plateformRightBottom))
				&& mario.getOldPosition().x>=plateform.getX()+plateform.getWidth()) {
			mario.setX(plateform.getX() + plateform.getWidth() - mario.getOffset().x + COLLISION_X_CORRECTIF);
			mario.getAcceleration().x = 0;				
		} else {
			if (mario.getOldPosition().y> plateform.getY()) {
				stuckMario(mario, plateform);
			}
		}
	}

	private void handleFallingRight(Mario mario, AbstractMetalPlateform plateform) {
		Vector2 plateformLeftTop = new Vector2(plateform.getX(), plateform.getY() + plateform.getHeight());
		Vector2 plateformLeftBottom = new Vector2(plateform.getX(), plateform.getY());
							
		if ((mario.getBounds().contains(plateformLeftBottom) || mario.getBounds().contains(plateformLeftTop)) 
				&& mario.getOldPosition().x+mario.getWidth()+mario.getOffset().x<=plateform.getX()) {
			mario.setX(plateform.getX() - (mario.getWidth() + mario.getOffset().x) - COLLISION_X_CORRECTIF);
			mario.getAcceleration().x = 0;				
		} else {
			if (mario.getOldPosition().y> plateform.getY()) {
				stuckMario(mario, plateform);
			}			
		}
	}

	private void stuckMario(Mario mario, AbstractMetalPlateform plateform) {
						
		mario.getAcceleration().y = 0;
		mario.setY(plateform.getY()+plateform.getHeight());			
		plateform.stuckMario(mario);
		mario.setOnFloor(true);										

		if (mario.getState()==SpriteMoveEnum.JUMPING || mario.getState()==SpriteMoveEnum.FALLING) {				
			mario.setState(SpriteMoveEnum.NO_MOVE);
		}
	}
		

}
