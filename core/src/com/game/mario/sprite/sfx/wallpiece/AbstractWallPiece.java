package com.game.mario.sprite.sfx.wallpiece;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractWallPiece extends AbstractSfxSprite {

	protected static final float X_ACCELERATION_COEFF = 8;
	
	protected static final float Y_ACCELERATION_COEFF = 0.3f;
	
	//@TODO Mettre l'image du sprite dans la classe abstraite parente
	public AbstractWallPiece(float x, float y, Vector2 acceleration) {
		super(x, y);							
		setRenderingSize(0.5f,0.5f);		
		this.acceleration = acceleration;
				
		moveable = true;		
		gravitating = true;
		
		isAnimationLooping = false;
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
