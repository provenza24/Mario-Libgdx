package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.CollisionEvent;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.enums.MarioStateEnum;
import com.mygdx.game.mario.tilemap.MarioTileMap;

public class Mario extends Sprite {

	private static final int ACCELERATION_MAX = 5;

	private static final float DECELERATION_COEF = 0.4f;

	private static final float ACCELERATION_COEF = 0.2f;
	
	private static final float GRAVITY_COEF = 0.01f;

	private DirectionEnum direction;
	
	private Texture marioSpriteSheet;
		
	private Animation marioRunRightAnimation;
	
	private Animation marioRunLeftAnimation;
	
	private Animation marioSlideRightAnimation;
	
	private Animation marioSlideLeftAnimation;
	
	private Animation marioJumpLeftAnimation;
	
	private Animation marioJumpRightAnimation;
		
	private float marioStateTime;
	
	private Vector2 acceleration;
			
	private Vector2 oldPosition;
		
	private MarioStateEnum state;
	
	private MarioStateEnum previousState;
	
	private Animation currentAnimation; 
	
	private TextureRegion currentFrame;	
	
	private CollisionEvent mapCollisionEvent;
	
	private boolean onFloor;
				
	public Mario() {
							
		setSize(32, 32);
		setPosition(0, 6);
		acceleration = new Vector2();
		oldPosition = new Vector2(0,6);
		onFloor = true;
		
		mapCollisionEvent = new CollisionEvent();
		
		marioSpriteSheet = new Texture(Gdx.files.internal("mario.gif"));

		TextureRegion[][] tmp = TextureRegion.split(marioSpriteSheet, marioSpriteSheet.getWidth() / 14, marioSpriteSheet.getHeight() / 1);
		
		TextureRegion[] marioRunRightFrames = new TextureRegion[3];
		marioRunRightFrames[0] = tmp[0][0];
		marioRunRightFrames[1] = tmp[0][1];
		marioRunRightFrames[2] = tmp[0][2];		
		marioRunRightAnimation = new Animation(0.05f, marioRunRightFrames);
		
		TextureRegion[] marioRunLeftFrames = new TextureRegion[3];
		marioRunLeftFrames[0] = tmp[0][5];
		marioRunLeftFrames[1] = tmp[0][6];
		marioRunLeftFrames[2] = tmp[0][7];		
		marioRunLeftAnimation = new Animation(0.05f, marioRunLeftFrames);
		
		TextureRegion[] marioSlideRightFrames = new TextureRegion[1];
		marioSlideRightFrames[0] = tmp[0][9];
		marioSlideRightAnimation = new Animation(1, marioSlideRightFrames);
		
		TextureRegion[] marioSlideLeftFrames = new TextureRegion[1];
		marioSlideLeftFrames[0] = tmp[0][4];
		marioSlideLeftAnimation = new Animation(1, marioSlideLeftFrames);
		
		TextureRegion[] marioJumpRightFrames = new TextureRegion[1];
		marioJumpRightFrames[0] = tmp[0][3];
		marioJumpRightAnimation = new Animation(1, marioJumpRightFrames);
		
		TextureRegion[] marioJumpLeftFrames = new TextureRegion[1];
		marioJumpLeftFrames[0] = tmp[0][8];
		marioJumpLeftAnimation = new Animation(1, marioJumpLeftFrames);
		
		marioStateTime = 0f;
		
		direction = DirectionEnum.RIGHT;
		state = MarioStateEnum.NO_MOVE;
		previousState = MarioStateEnum.NO_MOVE;
	}

	public Animation getMarioSlideRightAnimation() {
		return marioSlideRightAnimation;
	}

	public void setMarioSlideRightAnimation(Animation marioSlideRightAnimation) {
		this.marioSlideRightAnimation = marioSlideRightAnimation;
	}

	public Texture getMarioSpriteSheet() {
		return marioSpriteSheet;
	}

	public void setMarioSpriteSheet(Texture marioSpriteSheet) {
		this.marioSpriteSheet = marioSpriteSheet;
	}
	
	public Animation getMarioRunRightAnimation() {
		return marioRunRightAnimation;
	}

	public void setMarioRunRightAnimation(Animation marioRunRightAnimation) {
		this.marioRunRightAnimation = marioRunRightAnimation;
	}

	public float getMarioStateTime() {
		return marioStateTime;
	}

	public void setMarioStateTime(float marioStateTime) {
		this.marioStateTime = marioStateTime;
	}

	public Animation getMarioRunLeftAnimation() {
		return marioRunLeftAnimation;
	}

	public void setMarioRunLeftAnimation(Animation marioRunLeftAnimation) {
		this.marioRunLeftAnimation = marioRunLeftAnimation;
	}
	
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
		
	public Vector2 getOldPosition() {
		return oldPosition;
	}

	public void setOldPosition(Vector2 oldPosition) {
		this.oldPosition = oldPosition;
	}

	public void accelerate() {
		if (this.acceleration.x<ACCELERATION_MAX) {
			this.acceleration.x = this.acceleration.x + ACCELERATION_COEF;
		}
	}
	
	public void decelerate() {
		if (this.acceleration.x>0) {
			this.acceleration.x = this.acceleration.x - DECELERATION_COEF; 
		}
		if (this.acceleration.x<0) {
			this.acceleration.x = 0;
		}
	}
	
	public void applyGravity() {
		this.acceleration.y = this.acceleration.y - GRAVITY_COEF; 
	}
	
	public Animation getMarioJumpLeftAnimation() {
		return marioJumpLeftAnimation;
	}

	public void setMarioJumpLeftAnimation(Animation marioJumpLeftAnimation) {
		this.marioJumpLeftAnimation = marioJumpLeftAnimation;
	}

	public Animation getMarioJumpRightAnimation() {
		return marioJumpRightAnimation;
	}

	public void setMarioJumpRightAnimation(Animation marioJumpRightAnimation) {
		this.marioJumpRightAnimation = marioJumpRightAnimation;
	}

	public void reinitHorizontalMapCollisionEvent() {
		mapCollisionEvent.setCollidingLeft(false);
		mapCollisionEvent.setCollidingRight(false);		
	}
	
	public void reinitVerticalMapCollisionEvent() {
		mapCollisionEvent.setCollidingBottom(false);
		mapCollisionEvent.setCollidingTop(false);		
	}

	public void storeOldPosition() {
		oldPosition.x = getX();
		oldPosition.y = getY();
		previousState = state;
	}
	
	public MarioStateEnum getState() {
		return state;
	}

	public void setState(MarioStateEnum pstate) {		
		this.state = pstate;			
	}
	
	public void setStateIfNotJumping(MarioStateEnum pstate) {		
		if (state!=MarioStateEnum.FALLING && state!=MarioStateEnum.JUMPING) {
			this.state = pstate;			
		}
	}
	
	public Animation getMarioSlideLeftAnimation() {
		return marioSlideLeftAnimation;
	}

	public void setMarioSlideLeftAnimation(Animation marioSlideLeftAnimation) {
		this.marioSlideLeftAnimation = marioSlideLeftAnimation;
	}

	public MarioStateEnum getPreviousState() {
		return previousState;
	}

	public void setPreviousState(MarioStateEnum previousState) {
		this.previousState = previousState;
	}

	public boolean isOnFloor() {
		return onFloor;
	}

	public void setOnFloor(boolean onFloor) {
		this.onFloor = onFloor;
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
	
	public CollisionEvent getMapCollisionEvent() {
		return mapCollisionEvent;
	}

	public void setMapCollisionEvent(CollisionEvent mapCollisionEvent) {
		this.mapCollisionEvent = mapCollisionEvent;
	}
	
	public void move() {
		
		float xVelocity = Gdx.graphics.getDeltaTime() * acceleration.x;
		xVelocity = direction==DirectionEnum.LEFT ? -xVelocity : xVelocity;
		setX(getX() + xVelocity);
		
		applyGravity();
		setY(getY()+acceleration.y);
	}
	
	public void collideMarioWithTilemap(MarioTileMap tileMap) {
				
		tileMap.checkVerticalMapCollision(this);
		float yMove = getY() - getOldPosition().y;	
		if (yMove<0) {
			if (getMapCollisionEvent().isCollidingBottom()) {				
				setY((int)getY()+1);
				getAcceleration().y = 0;
				if (state==MarioStateEnum.FALLING) {
					state = MarioStateEnum.NO_MOVE;
				}
			} else {
				setState(MarioStateEnum.FALLING);				
			}
		} else if (yMove==0) {		
			if (previousState==MarioStateEnum.JUMPING) {				
				setState(MarioStateEnum.FALLING);				
			}
		} else if (yMove>0) {
			if (getMapCollisionEvent().isCollidingTop()) {				
				setY(getOldPosition().y);
				getAcceleration().y = 0;
				setState(MarioStateEnum.FALLING);		
			}		
		}
		
		tileMap.checkHorizontalMapCollision(this);		
		float xMove = getX() - getOldPosition().x;		
		if (xMove>0 && getMapCollisionEvent().isCollidingRight() 
				|| xMove<0 && getMapCollisionEvent().isCollidingLeft()) {			
			// Mario is colliding on his right or left
			setX(getOldPosition().x);
			getAcceleration().x = 0;
		}			
		
		onFloor = getMapCollisionEvent().isCollidingBottom();						
	}
	
	public void updateAnimation() {
		
		float xMove = getX() - getOldPosition().x;
		
		if (getState()!=MarioStateEnum.JUMPING && getState()!=MarioStateEnum.FALLING) {
			if (xMove==0) {			
				if (getState()==MarioStateEnum.SLIDING_LEFT) {
					setDirection(DirectionEnum.RIGHT);
				} else if (getState()==MarioStateEnum.SLIDING_RIGHT) {
					setDirection(DirectionEnum.LEFT);
				} 
				setState(MarioStateEnum.NO_MOVE);			 					
				setCurrentAnimation(getDirection()==DirectionEnum.RIGHT ? getMarioRunRightAnimation() : getMarioRunLeftAnimation());
				setCurrentFrame(getCurrentAnimation().getKeyFrame(0, false));
			} else {			
				setCurrentAnimation(getState()==MarioStateEnum.RUNNING_LEFT ? getMarioRunLeftAnimation() :
					 getState()==MarioStateEnum.RUNNING_RIGHT ? getMarioRunRightAnimation() :
						 getState()==MarioStateEnum.SLIDING_LEFT ? getMarioSlideLeftAnimation() :
							 getState()==MarioStateEnum.SLIDING_RIGHT ? getMarioSlideRightAnimation() :
								 getDirection()==DirectionEnum.RIGHT ? getMarioRunRightAnimation() : getMarioRunLeftAnimation());			
				setCurrentFrame(getCurrentAnimation().getKeyFrame(getMarioStateTime(), true));
			}										
		} else {
			setCurrentAnimation(getDirection()==DirectionEnum.RIGHT ? getMarioJumpRightAnimation() : getMarioJumpLeftAnimation());
			setCurrentFrame(getCurrentAnimation().getKeyFrame(0, false));
		}
		
		if (!onFloor) {
			setCurrentAnimation(getDirection()==DirectionEnum.RIGHT ? getMarioJumpRightAnimation() : getMarioJumpLeftAnimation());
			setCurrentFrame(getCurrentAnimation().getKeyFrame(0, false));
		}
	}
	
}
