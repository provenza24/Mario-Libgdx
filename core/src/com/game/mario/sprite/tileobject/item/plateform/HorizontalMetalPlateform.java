package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.DirectionEnum;

public class HorizontalMetalPlateform extends AbstractMetalPlateform {

	private static final float ACCELERATION = 0.00055f;

	private static final float ACCELERATION_MIN = 0.00025f;
	
	private static final float ACCELERATION_MAX = 0.08f;
	
	private final float DECCELERATION_STEP;
	
	private float stepNumber;		
	
	private float currentStep;
	
	BitmapFont debugFont;
	
	SpriteBatch spriteBatch;
	
	public HorizontalMetalPlateform(MapObject mapObject) {
		super(mapObject);		
		direction = DirectionEnum.valueOf(mapObject.getProperties().get("direction").toString().toUpperCase());		
		stepNumber = 3;
		acceleration.x = ACCELERATION_MIN;
		DECCELERATION_STEP = 1.52f;
		spriteBatch = new SpriteBatch();
		debugFont = new BitmapFont();		
		debugFont.setColor(0, 0, 1, 1);
	}
	
	@Override
	public void move(float deltaTime) {		
		
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
	
	public void render(Batch batch) {
		batch.begin();
		//@TODO replace this by computing value at initialization		
		batch.draw(currentFrame, getX(), getY(), renderingSize.x, renderingSize.y);		
		batch.end();
		
		spriteBatch.begin();
		debugFont.draw(spriteBatch, "acceleration.x=" + String.format("%.3f", acceleration.x), 20, 200);
		debugFont.draw(spriteBatch, "direction="+direction, 20, 220);
		spriteBatch.end();
	}


}
