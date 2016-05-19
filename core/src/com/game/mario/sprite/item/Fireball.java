package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class Fireball extends AbstractSprite {

	public Fireball(Mario mario) {
		super(mario.getX() + mario.getWidth()/2, mario.getY() + 0.9f);		
		setSize(0.5f, 0.5f);		
		renderingSize = new Vector2(0.5f,0.5f);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		alive = true;		
		acceleration.x = mario.getDirection()==DirectionEnum.RIGHT ? 16 : -16;
		acceleration.y = -0.1f;
		moveable = true;
		collidableWithTilemap = true;
		gravitating = true; 
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		super.update(tileMap, camera, deltaTime);		
		if (isOnFloor()) {			
			acceleration.y = 0.1f;
		}		
		deletable = deletable 
				||this.getMapCollisionEvent().isCollidingLeft() 
				|| this.getMapCollisionEvent().isCollidingRight() 
				|| this.getMapCollisionEvent().isCollidingTop()
				|| camera.position.x+8<getX();
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.FIREBALL;				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);				
		TextureRegion[] frames = new TextureRegion[4];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[0][2];
		frames[3] = tmp[0][3];			
		currentAnimation = new Animation(0.05f, frames);				
	}
	

}
