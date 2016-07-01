package com.game.mario.sprite.sfx.wallpiece;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.AbstractSfxSprite;

public abstract class AbstractWallPiece extends AbstractSfxSprite {

	private static final float WIDTH = 0.5f;
	
	private static final float HEIGHT = 0.5f;	

	protected static final float X_ACCELERATION_COEFF = 8;
	
	protected static final float Y_ACCELERATION_COEFF = 0.3f;
	
	protected WorldTypeEnum worldTypeEnum;
		
	public AbstractWallPiece(float x, float y, Vector2 acceleration, WorldTypeEnum worldTypeEnum) {
		super(x, y, new Vector2(WIDTH, HEIGHT), new Vector2());									
		this.acceleration = acceleration;		
		this.worldTypeEnum = worldTypeEnum;		
		this.moveable = true;		
		this.gravitating = true;		
		this.isAnimationLooping = false;
		initializeAnimationsWithBackgrounds();		
	}

	@Override
	public void addAppearAction() {				
	}
	
	@Override
	public void initializeAnimations() {
		// TODO Auto-generated method stub	
	}

	protected abstract void initializeAnimationsWithBackgrounds();		
	
	@Override
	public void move(float deltaTime) {	
		// TODO why this ?
		super.move(deltaTime);
		if (acceleration.y>0) {
			acceleration.y -=0.05f;
		} else {
			acceleration.y -=0.005f;
		}
	}


	public WorldTypeEnum getWorldTypeEnum() {
		return worldTypeEnum;
	}

	public void setWorldTypeEnum(WorldTypeEnum worldTypeEnum) {
		this.worldTypeEnum = worldTypeEnum;
	}
}
