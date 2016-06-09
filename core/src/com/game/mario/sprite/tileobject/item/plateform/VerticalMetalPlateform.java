package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.tilemap.TmxMap;

public class VerticalMetalPlateform extends AbstractMetalPlateform {

	private static final float ACCELERATION = 0.00055f;

	private static final float ACCELERATION_MIN = 0.00025f;
	
	private static final float ACCELERATION_MAX = 0.08f;
	
	private final float DECCELERATION_STEP;
	
	private float stepNumber;		
	
	private float currentStep;
	
	public VerticalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		direction = DirectionEnum.valueOf(mapObject.getProperties().get("direction").toString().toUpperCase());		
		stepNumber = Float.parseFloat((String)mapObject.getProperties().get("stepNumber"));
		acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;
		DECCELERATION_STEP = stepNumber - 4.2f;
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		super.update(tileMap, camera, deltaTime);		
		
		if (isAlive()) {
			if (currentStep<=stepNumber) {
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
			updateBounds();
		}			
	}


}
