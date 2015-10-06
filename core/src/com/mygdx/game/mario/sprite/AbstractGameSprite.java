package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.CollisionEvent;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.tilemap.TmxMap;

public abstract class AbstractGameSprite extends Sprite implements IMoveable, ICollisionable, IDrawable {

	private static final float GRAVITY_COEF = 0.01f;

	protected DirectionEnum direction;

	protected Vector2 acceleration;

	protected float stateTime;

	protected CollisionEvent mapCollisionEvent;

	protected boolean onFloor;

	protected Animation currentAnimation;

	protected TextureRegion currentFrame;

	protected Texture spriteSheet;
	
	protected boolean gravitating;
	
	protected Vector2 oldPosition;
	
	protected boolean visible;
	
	protected boolean alive;
	
	protected boolean deletable;
	
	protected float xAlive;

	public AbstractGameSprite(MapObject mapObject) {
		float xPosition = (Float) mapObject.getProperties().get("x")/32;
		float yPosition = (Float) mapObject.getProperties().get("y")/32 + 1;
		setPosition(xPosition, yPosition);
		oldPosition = new Vector2(xPosition, yPosition);
		acceleration = new Vector2(0,0);
		String xAliveString = (String) mapObject.getProperties().get("xAlive");
		xAlive = xAliveString!=null ? Float.parseFloat(xAliveString) / 32 : xPosition - 16 ;		
		visible = false;
		alive = false;
		mapCollisionEvent = new CollisionEvent();
	}

	/** Methods */
	public void applyGravity() {
		if (isGravitating()) {
			this.acceleration.y = this.acceleration.y - GRAVITY_COEF;
		}		
	}

	public void updateAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);		
	}
	
	public void storeOldPosition() {
		oldPosition.x = getX();
		oldPosition.y = getY();		
	}

	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		if (alive) {			
			move(deltaTime);
			collideWithTilemap(tileMap);
			updateAnimation(deltaTime);			
			if (getX()<camera.position.x-9) {
				deletable = true;				
			} else {
				visible = getX() < camera.position.x+8;				
			}							
		} else {
			alive = camera.position.x-8>xAlive;			
		}
	}
	
	public void move(float deltaTime) {

		storeOldPosition();
		
		float xVelocity = deltaTime * acceleration.x;
		xVelocity = direction == DirectionEnum.LEFT ? -xVelocity : xVelocity;
		setX(getX() + xVelocity);

		applyGravity();
		setY(getY() + acceleration.y);
	}
			
	public void collideWithTilemap(TmxMap tileMap) {
		
		tileMap.checkVerticalMapCollision(this);
		float yMove = getY() - getOldPosition().y;
		if (yMove < 0) {
			if (getMapCollisionEvent().isCollidingBottom()) {
				setY((int) getY() + 1);
				getAcceleration().y = 0;
				
			}
		} else if (yMove > 0) {
			if (getMapCollisionEvent().isCollidingTop()) {
				setY(getOldPosition().y);
				getAcceleration().y = 0;			
			}
		}		
		
		tileMap.checkHorizontalMapCollision(this);
		float xMove = getX() - getOldPosition().x;
		if (xMove > 0 && getMapCollisionEvent().isCollidingRight()
				|| xMove < 0 && getMapCollisionEvent().isCollidingLeft()) {			
			setX(getOldPosition().x);
			getAcceleration().x = -getAcceleration().x;
		}

		onFloor = getMapCollisionEvent().isCollidingBottom();
		
	}	
	
	public void render(Batch batch) {
		batch.begin();
		batch.draw(currentFrame, getX(), getY(), 1, 1);
		batch.end();
	}

	public void reinitHorizontalMapCollisionEvent() {
		mapCollisionEvent.setCollidingLeft(false);
		mapCollisionEvent.setCollidingRight(false);
	}

	public void reinitVerticalMapCollisionEvent() {
		mapCollisionEvent.setCollidingBottom(false);
		mapCollisionEvent.setCollidingTop(false);
	}

	/** Getters / Setters */
	public DirectionEnum getDirection() {
		return direction;
	}

	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean isOnFloor() {
		return onFloor;
	}

	public void setOnFloor(boolean onFloor) {
		this.onFloor = onFloor;
	}

	public CollisionEvent getMapCollisionEvent() {
		return mapCollisionEvent;
	}

	public void setMapCollisionEvent(CollisionEvent mapCollisionEvent) {
		this.mapCollisionEvent = mapCollisionEvent;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Texture spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public boolean isGravitating() {
		return gravitating;
	}

	public void setGravitating(boolean gravitating) {
		this.gravitating = gravitating;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getxAlive() {
		return xAlive;
	}

	public void setxAlive(float xAlive) {
		this.xAlive = xAlive;
	}
	
	
	public Vector2 getOldPosition() {
		return oldPosition;
	}

	public void setOldPosition(Vector2 oldPosition) {
		this.oldPosition = oldPosition;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}	


}
