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
	
	public Toad(float x, float y) {		
		super(x ,y);						
		renderingSize = new Vector2(2,2);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());		
		alive = true;
		gravitating = true;
		collidableWithTilemap = true;
		moveable = true;
		visible = true;
		onFloor=true;	
		GRAVITY_COEF = 0.012f;
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.CASTLE_BAG;			
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 8, spriteSheet.getHeight() / 1);						
		onFloorTexture = tmp[0][7];
		jumpingTexture = tmp[0][6];							
	}

	@Override
	public void addAppearAction() {			
	}

	@Override
	protected void updateAnimation(float delta) {		
		if (onFloor) {
			currentFrame = onFloorTexture;
		} else {
			currentFrame = jumpingTexture;
		}
	}

	@Override
	public void move(float deltaTime) {
		timer += deltaTime;
		if (onFloor && timer>1) {			
			onFloor = false;
			acceleration.y = 0.4f;			
		} else {
			if (getY()>3) {				
				acceleration.y = 0;
				timer = 0;
			}
		}
		super.move(deltaTime);
	}
	
		
	
}
