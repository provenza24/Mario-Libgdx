package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.CollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public abstract class AbstractMetalPlateform extends AbstractTileObjectSprite {

	protected boolean isStuck;
	
	public AbstractMetalPlateform(MapObject mapObject) {
		super(mapObject, new Vector2());
		gravitating = false;
		collidableWithTilemap = false;				
		initializeAnimationsUsingSize();
	}	
	
	public void initializeAnimationsUsingSize() {
		spriteSheet = getWidth()==2 ? ResourcesLoader.METAL_PLATEFORM_4 : ResourcesLoader.METAL_PLATEFORM_6;		
		currentAnimation = new Animation(0, TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1)[0][0]);	
		currentFrame = currentAnimation.getKeyFrame(0, false);
	}
	
	@Override
	public void initializeAnimations() {		
	}

	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		
		if (alive) {
			// The sprite is alive, we first update its animation
			move(deltaTime);						
			
			updateBounds();
			
			Mario mario = tileMap.getMario();
											
			if (this.overlaps(mario)) {				
				CollisionHandler.getCollisionHandler().collideMarioWithPlateform(mario, this);				
			} else {
				isStuck = isStuck && direction==DirectionEnum.DOWN && isUnder(mario);					
			}
			
			if (isStuck() && direction==DirectionEnum.DOWN) {				
				tileMap.getMario().setY(getY()+getHeight());
				tileMap.getMario().setOnFloor(true);
				if (tileMap.getMario().getState()==SpriteMoveEnum.FALLING) {
					tileMap.getMario().setState(SpriteMoveEnum.NO_MOVE);					
				}
			}
			
			mario.setStuck(mario.isStuck() || isStuck);
			
			if (getX()<camera.position.x-13) {
				// Sprite is left out of screen, or has felt out of down screen
				deletable = true;				
			} else {
				// Check if sprite is visible
				visible = getX() < camera.position.x + 12;				
			}									
		} else {			
			if (camera.position.x < tileMap.getFlag().getX()) {				
				alive = camera.position.x-8>xAlive;
			}							
		}				
	}

	public void stuckMario(Mario mario) {
		isStuck = true;
		mario.setStuck(true);
	}
	
	public boolean isStuck() {
		return isStuck;
	}
	
	private boolean isUnder(Mario mario) {
		return mario.getState()!=SpriteMoveEnum.JUMPING
				&& mario.getY() >= getY() 
				&& ((mario.getX()+mario.getOffset().x>=getX() && mario.getX()+mario.getOffset().x<=getX()+getWidth())
				||	(mario.getX()+mario.getOffset().x+mario.getWidth()>=getX() && mario.getX()+mario.getOffset().x+mario.getWidth()<=getX()+getWidth()));
	}

}
