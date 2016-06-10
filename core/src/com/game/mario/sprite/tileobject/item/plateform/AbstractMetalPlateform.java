package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.CollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public abstract class AbstractMetalPlateform extends AbstractTileObjectSprite {

	protected boolean isStuck;
	
	public AbstractMetalPlateform(MapObject mapObject) {
		super(mapObject, new Vector2());
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
		currentFrame = currentAnimation.getKeyFrame(0, false);
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
				if (tileMap.getMario().getState()==SpriteStateEnum.FALLING) {
					tileMap.getMario().setState(SpriteStateEnum.NO_MOVE);					
				}
			}
			
			mario.setStuck(mario.isStuck() || isStuck);
			
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

	public void stuckMario(Mario mario) {
		isStuck = true;
		mario.setStuck(true);
	}
	
	public boolean isStuck() {
		return isStuck;
	}
	
	private boolean isUnder(Mario mario) {
		return mario.getState()!=SpriteStateEnum.JUMPING
				&& mario.getY() >= getY() 
				&& ((mario.getX()+mario.getOffset().x>=getX() && mario.getX()+mario.getOffset().x<=getX()+getWidth())
				||	(mario.getX()+mario.getOffset().x+mario.getWidth()>=getX() && mario.getX()+mario.getOffset().x+mario.getWidth()<=getX()+getWidth()));
	}

}
