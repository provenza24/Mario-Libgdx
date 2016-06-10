package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;

public class VerticalMetalPlateform extends AbstractPredifinedMetalPlateform {
	
	public VerticalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;		
	}
	
	public void move(float deltaTime) {
		
		super.move(deltaTime);		
		
		if (isAlive()) {
			if (currentStep<=STEP_NUMBER) {
				float positiveAcceleration =  Math.abs(acceleration.y);
				currentStep = currentStep + positiveAcceleration;
				if (currentStep>=DECCELERATION_STEP) {
					acceleration.y += positiveAcceleration > ACCELERATION_MIN ? direction==DirectionEnum.UP ? -ACCELERATION : ACCELERATION : 0;
				} else {
					acceleration.y += positiveAcceleration < ACCELERATION_MAX ? direction==DirectionEnum.UP ? ACCELERATION : -ACCELERATION : 0;
				}
				 
			} else {
				currentStep = 0;
				direction = direction==DirectionEnum.UP ? DirectionEnum.DOWN : DirectionEnum.UP;
				acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;
			}	
		}
	}

}
