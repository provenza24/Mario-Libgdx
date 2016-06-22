package com.game.mario.sprite.sfx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;

public class Toad extends AbstractSfxSprite {
	
	private TextureRegion jumpingTexture;
	
	private TextureRegion onFloorTexture;
	
	private float timer;
	
	private int jumpNumber;
	
	public Toad(float x, float y) {		
		super(x ,y);						
		setSize(1, 1.47f);
		renderingSize = new Vector2(1, 1.47f);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());		
		gravitating = true;
		collidableWithTilemap = true;
		moveable = true;		
		onFloor=true;	
		GRAVITY_COEF = 0.012f;
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.CASTLE_TOAD;			
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 2, spriteSheet.getHeight() / 1);						
		onFloorTexture = tmp[0][0];
		jumpingTexture = tmp[0][1];							
	}

	@Override
	public void addAppearAction() {			
	}

	@Override
	protected void updateAnimation(float delta) {	
		currentFrame = onFloor ? onFloorTexture : jumpingTexture;		
	}

	@Override
	public void move(float deltaTime) {
		timer += deltaTime;
		if (onFloor && timer>1 && jumpNumber<6) {			
			onFloor = false;
			acceleration.y = 0.4f;	
			jumpNumber++;
		} else {
			if (getY()>3) {				
				acceleration.y = 0;
				timer = 0;
			}
		}
		super.move(deltaTime);
	}			
	
}
