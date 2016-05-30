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
import com.game.mario.collision.CollisionPoint;
import com.game.mario.collision.tilemap.BasicCollisionHandler;
import com.game.mario.collision.tilemap.ITilemapCollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.enemy.Goomba;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractSprite extends Actor implements IMoveable, IDrawable {

	protected SpriteStateEnum state;
	
	private ITilemapCollisionHandler tilemapCollisionHandler;
	
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
	
	protected Vector2 oldAcceleration;
	
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
		oldAcceleration = new Vector2(0,0);
		
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
		
		tilemapCollisionHandler = new BasicCollisionHandler();
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
					tilemapCollisionHandler.collideWithTilemap(tileMap, this);
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
		bounds.setY(getY());
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
		oldAcceleration.x = acceleration.x;
		oldAcceleration.y = acceleration.y;
	}
	
	public void move(float deltaTime) {
		
		storeOldPosition();
		
		float xVelocity = deltaTime * acceleration.x;
		xVelocity = direction == DirectionEnum.LEFT ? -xVelocity : xVelocity;
		setX(getX() + xVelocity);

		applyGravity();
		setY(getY() + acceleration.y);		
	}
	
	public void render(Batch batch) {
		batch.begin();
		//@TODO replace this by computing value at initialization		
		batch.draw(currentFrame, getX(), getY(), renderingSize.x, renderingSize.y);
		batch.end();
	}

	public void reinitMapCollisionEvent() {				
		mapCollisionEvent.setCollidingTopLeft(false);
		mapCollisionEvent.setCollidingTopRight(false);		
		mapCollisionEvent.setCollidingBottomLeft(false);
		mapCollisionEvent.setCollidingBottomRight(false);
		mapCollisionEvent.setCollidingMiddleLeft(false);
		mapCollisionEvent.setCollidingMiddleRight(false);		
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
		boolean isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteStateEnum.FALLING;
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
	
	public void addCollidingCell(TmxCell tmxCell) {
		collidingCells.add(tmxCell);
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

	public Vector2 getOldAcceleration() {
		return oldAcceleration;
	}

	public void setOldAcceleration(Vector2 oldAcceleration) {
		this.oldAcceleration = oldAcceleration;
	}

	public SpriteStateEnum getState() {
		return state;
	}

	public void setState(SpriteStateEnum state) {
		this.state = state;
	}
	
}
