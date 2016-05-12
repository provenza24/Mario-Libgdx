package com.game.mario.sprite;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.mario.collision.CollisionEvent;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractSprite extends Actor implements IMoveable, IDrawable {

	private static final float GRAVITY_COEF = 0.01f;

	protected DirectionEnum direction;

	protected Vector2 acceleration;

	protected float stateTime;

	protected CollisionEvent mapCollisionEvent;

	protected boolean onFloor;

	protected Animation currentAnimation;

	protected TextureRegion currentFrame;
	
	protected Animation killedAnimation;

	protected Texture spriteSheet;
	
	protected boolean gravitating;
	
	protected Vector2 oldPosition;
	
	protected boolean visible;
	
	protected boolean alive;
	
	protected boolean deletable;
	
	protected float xAlive;
	
	protected Vector2 offset;
	
	protected Rectangle bounds;
	
	protected List<TmxCell> collidingCells;
	
	protected boolean moveable;
	
	protected boolean collidableWithTilemap;
	
	protected boolean killed;
	
	protected boolean bumped;
	
	protected Vector2 renderingSize;
	
	protected String image;

	public AbstractSprite(float x, float y) {
		
		setPosition(x, y);		
		setSize(1, 1);		
		renderingSize = new Vector2(1,1);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		oldPosition = new Vector2(x, y);
		acceleration = new Vector2(0,0);			
		
		mapCollisionEvent = new CollisionEvent();
		offset = new Vector2(0,0);			
		collidingCells = new ArrayList<TmxCell>();				
		
		visible = false;
		alive = false;
		killed = false;
		moveable = false;
		collidableWithTilemap = false;
		gravitating = false;
		bumped = false;
		
		stateTime = 0f;				
		
		xAlive = getX() - 16 ;
		
		initializeAnimations();
	}
	
	public Rectangle getBounds() {
        return bounds;
    }
	
	public abstract void initializeAnimations();

	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
						
		if (alive) {
			// The sprite is alive, we first update its animation
			updateAnimation(deltaTime);
			if (getActions().size>0) {
				// If sprite is acting (ex: sprite is an enemy, and has just been killed, an animation is playing to simulate its death) 
				act(deltaTime);
			} else {
				// else sprite is not acting
				if (isMoveable()) {
					// if sprite is moveable, we move it
					move(deltaTime);
				}
				if (isCollidableWithTilemap()) {
					// if sprite collides with tilemap, collide it
					collideWithTilemap(tileMap);
				}				
			}
			// Update sprite bounds (for future collisions)
			updateBounds();
			if (getX()<camera.position.x-9 || getY() < -1) {
				// Sprite is left out of screen, or has felt out of down screen
				deletable = true;				
			} else {
				// Check if sprite is visible
				visible = getX() < camera.position.x+8;				
			}						
		} else {			
			if (camera.position.x < tileMap.getFlag().getX()) {				
				alive = camera.position.x-8>xAlive;
			} else {
				// Sprite of bonus stage are alive
				alive = getX() >  tileMap.getFlag().getX();
			}
							
		}				
	}

	protected void updateBounds() {
		bounds.setX(getX()+offset.x);
		bounds.setY(getY()+offset.y);
	}
	
	protected void applyGravity() {
		if (isGravitating()) {
			this.acceleration.y = this.acceleration.y - GRAVITY_COEF;
		}		
	}

	protected void updateAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);		
	}
	
	protected void storeOldPosition() {
		oldPosition.x = getX();
		oldPosition.y = getY();		
	}

	public void move(float deltaTime) {
		
		storeOldPosition();
		
		float xVelocity = deltaTime * acceleration.x;
		xVelocity = direction == DirectionEnum.LEFT ? -xVelocity : xVelocity;
		setX(getX() + xVelocity);

		applyGravity();
		setY(getY() + acceleration.y);		
	}
			
	protected void collideWithTilemap(TmxMap tileMap) {
		
		checkVerticalMapCollision(tileMap);
		float yMove = getY() - getOldPosition().y;
		if (yMove < 0) {
			if (getMapCollisionEvent().isCollidingBottom()) {
				setY((int) getY() + 1);
				getAcceleration().y = 0;
				
			}
		} else if (yMove > 0) {
			if (getMapCollisionEvent().isCollidingTop()) {
				setY((int) getY());
				getAcceleration().y = 0;			
			}
		}		
		
		checkHorizontalMapCollision(tileMap);
		float xMove = getX() - getOldPosition().x;
		if (xMove > 0 && getMapCollisionEvent().isCollidingRight()
				|| xMove < 0 && getMapCollisionEvent().isCollidingLeft()) {			
			setX(getOldPosition().x);			
			getAcceleration().x = -getAcceleration().x;
		}

		onFloor = getMapCollisionEvent().isCollidingBottom();					
	}	
	
	public void checkHorizontalMapCollision(TmxMap tilemap) {

		reinitHorizontalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x, getY() + offset.y);
		Vector2 leftTopCorner = new Vector2(getX() + getOffset().x, getY() + getHeight() + offset.y);
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + offset.y);
		Vector2 rightTopCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + getHeight() + offset.y);

		boolean isCollision = tilemap.isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		getMapCollisionEvent().setCollidingLeft(isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		getMapCollisionEvent().setCollidingLeft(getMapCollisionEvent().isCollidingLeft() || isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);

	}

	public void checkVerticalMapCollision(TmxMap tilemap) {

		reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x, getY() + offset.y);
		Vector2 leftTopCorner = new Vector2(getX() + getOffset().x, getY() + getHeight() + offset.y);
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + offset.y);
		Vector2 rightTopCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + getHeight() + offset.y);

		boolean isCollision = tilemap.isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		getMapCollisionEvent().setCollidingBottom(isCollision);

		int x = (int) leftTopCorner.x;
		int y = (int) leftTopCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x ,y);		
		getMapCollisionEvent().setCollidingTop(getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			getCollidingCells().add(new TmxCell(tilemap.getTileAt(x, y), x ,y));
		}

		isCollision = tilemap.isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		getMapCollisionEvent()
				.setCollidingBottom(getMapCollisionEvent().isCollidingBottom() || isCollision);
		
		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingTop(getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			getCollidingCells().add(new TmxCell(tilemap.getTileAt(x, y), x ,y));
		}

	}
	
	public void render(Batch batch) {
		batch.begin();
		//@TODO replace this by computing value at initialization		
		batch.draw(currentFrame, getX(), getY(), renderingSize.x, renderingSize.y);
		batch.end();
	}

	public void reinitHorizontalMapCollisionEvent() {
		mapCollisionEvent.setCollidingLeft(false);
		mapCollisionEvent.setCollidingRight(false);
	}

	public void reinitVerticalMapCollisionEvent() {
		mapCollisionEvent.setCollidingBottom(false);
		mapCollisionEvent.setCollidingTop(false);
		collidingCells = new ArrayList<TmxCell>();
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

	public Animation getKilledAnimation() {
		return killedAnimation;
	}

	public void setKilledAnimation(Animation killedAnimation) {
		this.killedAnimation = killedAnimation;
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

	public Vector2 getRenderingSize() {
		return renderingSize;
	}

	public void setRenderingSize(Vector2 renderingSize) {
		this.renderingSize = renderingSize;
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Texture spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public boolean isKilled() {
		return killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}
	
	public boolean collideMario(Mario mario) {
		boolean isEnemyHit = mario.getY() > getY() && mario.getState() == MarioStateEnum.FALLING;
		if (isEnemyHit) {
			kill();
			mario.getAcceleration().y = 0.15f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);			
		}
		 return isEnemyHit;
	}
	
	public void kill() {
		this.killed = true;			
	}

	public boolean isCollidableWithTilemap() {
		return collidableWithTilemap;
	}

	public void setCollidableWithTilemap(boolean collidableWithTilemap) {
		this.collidableWithTilemap = collidableWithTilemap;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public boolean isGravitating() {
		return gravitating;
	}

	public List<TmxCell> getCollidingCells() {
		return collidingCells;
	}

	public void setCollidingCells(List<TmxCell> collidingCells) {
		this.collidingCells = collidingCells;
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

	public Vector2 getOffset() {
		return offset;
	}

	public void setOffset(Vector2 offset) {
		this.offset = offset;
	}	

	public void setRenderingSize(float px, float py) {
		renderingSize.x = px;
		renderingSize.y = py;
	}
	
	public void dispose() {
		spriteSheet.dispose();		
	}

	public void bump() {
		killed = true;
		bumped = true;
	}

	public boolean isBumped() {
		return bumped;
	}

	public void setBumped(boolean bumped) {
		this.bumped = bumped;
	}
	
}
