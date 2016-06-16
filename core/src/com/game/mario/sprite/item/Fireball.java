package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.tilemap.FireballTilemapCollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

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
		tilemapCollisionHandler = new FireballTilemapCollisionHandler();
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		super.update(tileMap, camera, deltaTime);		
		deletable = deletable || camera.position.x+9<getX();
	}	

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FIREBALL, 0, 4, 0.05f);						
	}
	

}
