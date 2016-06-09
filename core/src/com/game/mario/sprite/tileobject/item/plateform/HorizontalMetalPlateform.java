package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.tilemap.TmxMap;

public class HorizontalMetalPlateform extends AbstractMetalPlateform {

	private static final float ACCELERATION = 0.1f;

	private static final float ACCELERATION_MIN = 1f;
	
	private static final float ACCELERATION_MAX = 5f;
	
	private final float DECCELERATION_STEP;
	
	private float stepNumber;		
	
	private float currentStep;
	
	public HorizontalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		direction = DirectionEnum.valueOf(mapObject.getProperties().get("direction").toString().toUpperCase());		
		stepNumber = Float.parseFloat((String)mapObject.getProperties().get("stepNumber"));
		acceleration.x = direction==DirectionEnum.RIGHT ? ACCELERATION_MIN : - ACCELERATION_MIN;
		DECCELERATION_STEP = 100;
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		super.update(tileMap, camera, deltaTime);		
		
		if (isAlive()) {			
			if (currentStep<=stepNumber) {
				float positiveAcceleration =  Math.abs(acceleration.x);
				currentStep = currentStep + positiveAcceleration;
				if (currentStep>=DECCELERATION_STEP) {
					acceleration.x += positiveAcceleration > ACCELERATION_MIN ? direction==DirectionEnum.RIGHT ? -ACCELERATION : ACCELERATION : 0;
				} else {
					acceleration.x += positiveAcceleration < ACCELERATION_MAX ? direction==DirectionEnum.RIGHT ? ACCELERATION : -ACCELERATION : 0;
				}
				 
			} else {
				currentStep = 0;
				direction = direction==DirectionEnum.RIGHT ? DirectionEnum.LEFT : DirectionEnum.RIGHT;
				acceleration.x = direction==DirectionEnum.RIGHT ? ACCELERATION_MIN : -ACCELERATION_MIN;
			}
			updateBounds();
		}			
	}


}
