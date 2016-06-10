package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;

public class HorizontalMetalPlateform extends AbstractPredifinedMetalPlateform {
	
	public HorizontalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		acceleration.x = ACCELERATION_MIN;		
		
	}
	
	@Override
	public void move(float deltaTime) {		
		
		if (isAlive()) {
			if (currentStep<=STEP_NUMBER) {
				currentStep = currentStep + acceleration.x;
				if (currentStep>=DECCELERATION_STEP) {
					acceleration.x += acceleration.x > ACCELERATION_MIN ? -ACCELERATION : 0;
				} else {					
					acceleration.x += acceleration.x < ACCELERATION_MAX ? ACCELERATION : 0;
				}						
			} else {				
				currentStep = 0;
				direction = direction==DirectionEnum.RIGHT ? DirectionEnum.LEFT : DirectionEnum.RIGHT;
				acceleration.x = ACCELERATION_MIN;
			}
			
			storeOldPosition();
					
			float xVelocity = direction == DirectionEnum.LEFT ? -acceleration.x : acceleration.x;
			setX(getX() + xVelocity);
		}
					
	}

}
