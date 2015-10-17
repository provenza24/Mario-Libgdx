package com.mygdx.game.mario.sprite.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.sprite.AbstractItem;
import com.mygdx.game.mario.tilemap.TmxMap;

public class WallPiece extends AbstractItem {

	//@TODO Mettre l'image du sprite dans la classe abstraite parente
	public WallPiece(float x, float y, Vector2 acceleration) {
		super(x, y);							
		setRenderingSize(0.5f,0.5f);
		collidableWithTilemap = false;
		this.acceleration = acceleration;		
	}

	@Override
	public void initializeAnimations() {
		
		spriteSheet = new Texture(Gdx.files.internal("sprites/brokenWall_0_1.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);				
		TextureRegion[] frame = new TextureRegion[1];
		frame[0] = tmp[0][0];			
		currentAnimation = new Animation(0, frame);		
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
