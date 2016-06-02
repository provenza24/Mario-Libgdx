package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public abstract class AbstractMetalPlateform extends AbstractTileObjectSprite {

	public AbstractMetalPlateform(MapObject mapObject) {
		super(mapObject);
		setSize(3, 0.5f);
		setY(getY()-1+getHeight());
		setRenderingSize(3, 0.5f);
		gravitating = false;
		collidableWithTilemap = false;		
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}	
	
	@Override
	public void initializeAnimations() {
		
		spriteSheet = ResourcesLoader.METAL_PLATEFORM;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);						
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		
		if (alive) {
			// The sprite is alive, we first update its animation
			updateAnimation(deltaTime);
			move(deltaTime);						
			updateBounds();
			if (getX()<camera.position.x-9) {
				// Sprite is left out of screen, or has felt out of down screen
				deletable = true;				
			} else {
				// Check if sprite is visible
				visible = getX() < camera.position.x+8;				
			}						
		} else {			
			if (camera.position.x < tileMap.getFlag().getX()) {				
				alive = camera.position.x-8>xAlive;
			}							
		}				
	}

}
