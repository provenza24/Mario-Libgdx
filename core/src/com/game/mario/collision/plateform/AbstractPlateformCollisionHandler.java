package com.game.mario.collision.plateform;

import java.util.HashMap;
import java.util.Map;

import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.AscendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.DescendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.HorizontalMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.VerticalMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractPlateformCollisionHandler implements IPlateformCollisionHandler {

	private static Map<Class<?>, IPlateformCollisionHandler> handlers = new HashMap<Class<?>, IPlateformCollisionHandler>();

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
				
		if (mario.getAcceleration().y>0) {			
			mario.getAcceleration().y = 0;
			mario.setY(plateform.getY() - (mario.getHeight() + mario.getOffset().y) - 0.0001f);
			mario.setState(SpriteStateEnum.FALLING);
			mario.setOnFloor(false);
		} else {
			mario.getAcceleration().y = 0;
			
			if (mario.getY()+mario.getHeight()>plateform.getY()) {
			
				mario.setY(plateform.getY()+plateform.getHeight());
			
				plateform.stuckMario(mario);
				mario.setOnFloor(true);										
			
				if (mario.getState()==SpriteStateEnum.JUMPING || mario.getState()==SpriteStateEnum.FALLING) {				
					mario.setState(SpriteStateEnum.NO_MOVE);
				}
			}
		}		
							
	}
		

}
