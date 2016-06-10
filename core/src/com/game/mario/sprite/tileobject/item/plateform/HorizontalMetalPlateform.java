package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;

public class HorizontalMetalPlateform extends AbstractMetalPlateform {

	private static final float ACCELERATION = 0.00055f;

	private static final float ACCELERATION_MIN = 0.00025f;
	
	private static final float ACCELERATION_MAX = 0.08f;
	
	private final float DECCELERATION_STEP;
	
	private float stepNumber;		
	
	private float currentStep;
	
	public HorizontalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		direction = DirectionEnum.valueOf(mapObject.getProperties().get("direction").toString().toUpperCase());		
		stepNumber = Float.parseFloat(mapObject.getProperties().get("stepNumber").toString());
		acceleration.x = ACCELERATION_MIN;
		DECCELERATION_STEP = stepNumber - 1.48f;		
	}
	
	@Override
	public void move(float deltaTime) {		
		
		if (isAlive()) {
			if (currentStep<=stepNumber) {
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
