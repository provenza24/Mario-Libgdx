package com.mygdx.game.mario.sprite.item.wallpiece;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.sprite.AbstractItem;
import com.mygdx.game.mario.tilemap.TmxMap;

public abstract class AbstractWallPiece extends AbstractItem {

	protected static final float X_ACCELERATION_COEFF = 8;
	
	protected static final float Y_ACCELERATION_COEFF = 0.3f;
	
	//@TODO Mettre l'image du sprite dans la classe abstraite parente
	public AbstractWallPiece(float x, float y, Vector2 acceleration) {
		super(x, y);							
		setRenderingSize(0.5f,0.5f);
		collidableWithTilemap = false;
		this.acceleration = acceleration;		
	}
	
	@Override
	protected void updateAnimation(float delta) {
		// Ovverided because the coin animation must be played only one time
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, false);		
	}

	@Override
	public void addAppearAction() {				
	}
		
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		super.update(tileMap, camera, deltaTime);
		if (acceleration.y>0) {
			acceleration.y -=0.05f;
		} else {
			acceleration.y -=0.005f;
		}
	}
}
