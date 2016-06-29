package com.game.mario.sprite.sfx.wallpiece;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.AbstractSfxSprite;

public abstract class AbstractWallPiece extends AbstractSfxSprite {

	protected static final float X_ACCELERATION_COEFF = 8;
	
	protected static final float Y_ACCELERATION_COEFF = 0.3f;
	
	protected WorldTypeEnum worldTypeEnum;
		
	public AbstractWallPiece(float x, float y, Vector2 acceleration, WorldTypeEnum worldTypeEnum) {
		super(x, y);							
		setRenderingSize(0.5f,0.5f);		
		this.acceleration = acceleration;
		
		this.worldTypeEnum = worldTypeEnum;
		
		moveable = true;		
		gravitating = true;
		
		isAnimationLooping = false;
	}

	@Override
	public void addAppearAction() {				
	}
		
	
	
	@Override
	public void initializeAnimations() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(float deltaTime) {		
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
