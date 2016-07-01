package com.game.mario.sprite.sfx.wallpiece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.WorldTypeEnum;

public class TopRightWallPiece extends AbstractWallPiece {

	public TopRightWallPiece(float x, float y, WorldTypeEnum worldTypeEnum) {
		super(x, y, new Vector2(X_ACCELERATION_COEFF, Y_ACCELERATION_COEFF*1.5f), worldTypeEnum);		
	}
	
	@Override
	public void initializeAnimationsWithBackgrounds() {		
		spriteSheet = new Texture(Gdx.files.internal("sprites/wall/brokenWall_"+(worldTypeEnum==WorldTypeEnum.OVERGROUND ? "0" : "1")+"_2.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);				
		TextureRegion[] frame = new TextureRegion[1];
		frame[0] = tmp[0][0];			
		currentAnimation = new Animation(0, frame);		
	}

}
