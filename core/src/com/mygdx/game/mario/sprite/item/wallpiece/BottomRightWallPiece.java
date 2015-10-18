package com.mygdx.game.mario.sprite.item.wallpiece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BottomRightWallPiece extends AbstractWallPiece {

	public BottomRightWallPiece(float x, float y) {
		super(x, y, new Vector2(X_ACCELERATION_COEFF,Y_ACCELERATION_COEFF));		
	}
	
	@Override
	public void initializeAnimations() {		
		spriteSheet = new Texture(Gdx.files.internal("sprites/brokenWall_0_4.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);				
		TextureRegion[] frame = new TextureRegion[1];
		frame[0] = tmp[0][0];			
		currentAnimation = new Animation(0, frame);		
	}

}
