package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;

public class AbstractPredifinedMetalPlateform extends AbstractMetalPlateform {

	protected static final float ACCELERATION = 0.00055f;

	protected static final float ACCELERATION_MIN = 0.00025f;
	
	protected static final float ACCELERATION_MAX = 0.08f;
	
	protected final float DECCELERATION_STEP;
	
	protected float STEP_NUMBER;		
	
	protected float currentStep;
	
	public AbstractPredifinedMetalPlateform(MapObject mapObject) {
		super(mapObject);
		direction = DirectionEnum.valueOf(mapObject.getProperties().get("direction").toString().toUpperCase());		
		STEP_NUMBER = Float.parseFloat(mapObject.getProperties().get("stepNumber").toString());
		DECCELERATION_STEP = STEP_NUMBER/2 + 0.01f;
	}

}
