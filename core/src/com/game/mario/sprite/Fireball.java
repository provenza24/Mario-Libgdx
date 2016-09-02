package com.game.mario.sprite;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.collision.tilemap.FireballTilemapCollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Fireball extends AbstractSprite {

	private static final float HEIGHT = 0.5f;
	private static final float WIDTH = 0.5f;

	public Fireball(Mario mario) {
		super(mario.getX() + mario.getWidth()/2, mario.getY() + 0.9f, new Vector2(WIDTH, HEIGHT), new Vector2());		
		alive = true;		
		acceleration.x = mario.getDirection()==DirectionEnum.RIGHT ? 16 : -16;
		acceleration.y = -0.1f;
		moveable = true;
		collidableWithTilemap = true;
		gravitating = true;
		tilemapCollisionHandler = new FireballTilemapCollisionHandler();
		deletableOutScreenRight = true;
	}
	
	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FIREBALL, 0, 4, 0.05f);						
	}
	

}
