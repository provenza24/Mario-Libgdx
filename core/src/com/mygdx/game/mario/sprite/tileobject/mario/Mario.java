package com.mygdx.game.mario.sprite.tileobject.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.enums.MarioStateEnum;
import com.mygdx.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class Mario extends AbstractTileObjectSprite {

	private static final float ACCELERATION_MAX = 5f; // 7.5f;

	private static final float DECELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF = 0.2f;

	Animation animations[][];   
	
	private Animation marioRunRightAnimation;

	private Animation marioRunLeftAnimation;

	private Animation marioSlideRightAnimation;

	private Animation marioSlideLeftAnimation;

	private Animation marioJumpLeftAnimation;

	private Animation marioJumpRightAnimation;

	private MarioStateEnum state;

	private MarioStateEnum previousState;

	private int jumpTimer;

	private boolean canInitiateJump;

	private boolean canJumpHigher;
	
	private int nbCoins;
	
	/** 0=small, 1=big, 2=flowered */
	private int sizeState;
		
	public Mario(MapObject mapObject) {
		super(mapObject);		
		setSize(1, 1);		
		renderingSize = new Vector2(1,1);
		stateTime = 0f;
		jumpTimer = 0;
		onFloor = true;
		canInitiateJump = true;
		canJumpHigher = true;
		gravitating = true;
		direction = DirectionEnum.RIGHT;
		state = MarioStateEnum.NO_MOVE;
		previousState = MarioStateEnum.NO_MOVE;				
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		sizeState = 0;
		changeSizeState(sizeState);
		nbCoins = 0;
	}

	public void changeSizeState(int i) {
		sizeState = i;
		if (i==0) {
			setSize(1, 1);			
			setRenderingSize(1,1);
		} else {
			setSize(1, 2);
			setRenderingSize(1,2);
		}
		//setBounds(getX(), getY(), getWidth(), getHeight());
		marioRunRightAnimation = animations[i][0]; 
		marioRunLeftAnimation = animations[i][1];
		marioSlideRightAnimation =  animations[i][2];
		marioSlideLeftAnimation=  animations[i][3];				
		marioJumpRightAnimation = animations[i][4];
		marioJumpLeftAnimation = animations[i][5];
	}
	
	@Override
	public void initializeAnimations() {		
		initializeAnimation("sprites/mario.gif", 0);		
		initializeAnimation("sprites/mario-big.png", 1);
	}

	private void initializeAnimation(String image, int i) {
		Texture spriteSheet = new Texture(Gdx.files.internal(image));

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 14,
				spriteSheet.getHeight() / 1);

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
				  
		if (animations == null) {
			 animations = new Animation[3][6] ;
		}
		animations[i] = new Animation[6];
		animations[i][0] = marioRunRightAnimation; 
		animations[i][1] = marioRunLeftAnimation;
		animations[i][2] = marioSlideRightAnimation;
		animations[i][3] = marioSlideLeftAnimation;
		animations[i][4] = marioJumpRightAnimation;
		animations[i][5] = marioJumpLeftAnimation;
	}
	
	public void accelerate() {
		if (this.acceleration.x < ACCELERATION_MAX) {
			this.acceleration.x = this.acceleration.x + ACCELERATION_COEF;
		}
	}

	public void decelerate(float rate) {
		if (this.acceleration.x > 0) {
			this.acceleration.x = this.acceleration.x - DECELERATION_COEF * rate;
		}
		if (this.acceleration.x < 0) {
			this.acceleration.x = 0;
		}
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

	public void storeOldPosition() {
		super.storeOldPosition();
		previousState = state;
	}

	public void setStateIfNotJumping(MarioStateEnum pstate) {
		if (state != MarioStateEnum.FALLING && state != MarioStateEnum.JUMPING) {
			this.state = pstate;
		}
	}
	
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		move(deltaTime);
		collideWithTilemap(tileMap);
		updateAnimation(deltaTime);
	}
	
	public void checkHorizontalMapCollision(TmxMap tilemap) {
		super.checkHorizontalMapCollision(tilemap);
		if (sizeState>0) {
			Vector2 rightMiddle = new Vector2(getX() + 0.95f - getOffset().x, getY() + getHeight()/2 - 0.1f);
			boolean isCollision = tilemap.isCollisioningTileAt((int) rightMiddle.x, (int) rightMiddle.y);
			getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);
			Vector2 leftMiddle = new Vector2(getX() + getOffset().x, getY() + getHeight()/2 - 0.1f);
			isCollision = tilemap.isCollisioningTileAt((int) leftMiddle.x, (int) leftMiddle.y);
			getMapCollisionEvent().setCollidingLeft(getMapCollisionEvent().isCollidingLeft() || isCollision);
		}		
	}
	
	public void collideWithTilemap(TmxMap tileMap) {

		float xActualAcceleration = acceleration.x;
		float xActual = getX();
		
		checkVerticalBottomMapCollision(tileMap);
		float yMove = getY() - getOldPosition().y;
		if (yMove < 0) {
			if (getMapCollisionEvent().isCollidingBottom()) {
				setY((int) getY() + 1);
				getAcceleration().y = 0;
				if (state == MarioStateEnum.FALLING) {
					state = MarioStateEnum.NO_MOVE;
				}
			} else {
				setState(MarioStateEnum.FALLING);
			}
		} else if (yMove == 0) {
			if (previousState == MarioStateEnum.JUMPING) {
				setState(MarioStateEnum.FALLING);
			}
		}
		
		checkHorizontalMapCollision(tileMap);
		float xMove = getX() - getOldPosition().x;
		if (xMove > 0 && getMapCollisionEvent().isCollidingRight()
				|| xMove < 0 && getMapCollisionEvent().isCollidingLeft()) {
			// Mario is colliding on his right or left			
			setX(getOldPosition().x);
			acceleration.x = 0;			
		}
		
		checkVerticalUpperMapCollision(tileMap);
		if (yMove > 0) {
			if (getMapCollisionEvent().isCollidingTop()) {
				setX(xActual);
				setY(getOldPosition().y);		
				acceleration.x = xActualAcceleration;
				acceleration.y = 0;
				setState(MarioStateEnum.FALLING);
			}
		}

		onFloor = getMapCollisionEvent().isCollidingBottom();
		bounds.setX(getX());
	    bounds.setY(getY());
	}
	
	public void checkVerticalBottomMapCollision(TmxMap tilemap) {

		reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + 0.1f + getOffset().x, getY());
		Vector2 rightBottomCorner = new Vector2(getX() + 0.9f - getOffset().x, getY());
		
		boolean isCollision = tilemap.isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		getMapCollisionEvent().setCollidingBottom(isCollision);
		
		isCollision = tilemap.isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		getMapCollisionEvent().setCollidingBottom(getMapCollisionEvent().isCollidingBottom() || isCollision);				
	}
	
	public void checkVerticalUpperMapCollision(TmxMap tilemap) {
	
		Vector2 leftTopCorner = new Vector2(getX() + 0.1f + getOffset().x, getY() + getHeight() - 0.1f);
		Vector2 rightTopCorner = new Vector2(getX() + 0.9f - getOffset().x, getY() + getHeight() - 0.1f);
		
		int x = (int) leftTopCorner.x;
		int y = (int) leftTopCorner.y;
		boolean isCollision = tilemap.isCollisioningTileAt(x ,y);		
		getMapCollisionEvent().setCollidingTop(getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			getCollidingCells().add(new TmxCell(tilemap.getTileAt(x, y), x ,y));
		}

		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;
		isCollision = tilemap.isCollisioningTileAt(x, y);
		getMapCollisionEvent().setCollidingTop(getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			getCollidingCells().add(new TmxCell(tilemap.getTileAt(x, y), x ,y));
		}
		
	}

	public void updateAnimation(float delta) {

		stateTime = stateTime + delta;

		float xMove = getX() - getOldPosition().x;

		if (getState() != MarioStateEnum.JUMPING && getState() != MarioStateEnum.FALLING) {
			if (xMove == 0) {
				if (getState() == MarioStateEnum.SLIDING_LEFT) {
					setDirection(DirectionEnum.RIGHT);
				} else if (getState() == MarioStateEnum.SLIDING_RIGHT) {
					setDirection(DirectionEnum.LEFT);
				}
				setState(MarioStateEnum.NO_MOVE);
				currentAnimation = direction == DirectionEnum.RIGHT ? marioRunRightAnimation : marioRunLeftAnimation;
				currentFrame = currentAnimation.getKeyFrame(0, false);
			} else {
				currentAnimation = state == MarioStateEnum.RUNNING_LEFT ? marioRunLeftAnimation
						: state == MarioStateEnum.RUNNING_RIGHT ? marioRunRightAnimation
								: state == MarioStateEnum.SLIDING_LEFT ? marioSlideLeftAnimation
										: state == MarioStateEnum.SLIDING_RIGHT ? marioSlideRightAnimation
												: direction == DirectionEnum.RIGHT ? marioRunRightAnimation
														: marioRunLeftAnimation;
				currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			}
		}

		if (!onFloor) {
			currentAnimation = direction == DirectionEnum.RIGHT ? marioJumpRightAnimation : marioJumpLeftAnimation;
			currentFrame = currentAnimation.getKeyFrame(0, false);
		}
	}

	public boolean canInitiateJump() {
		return canInitiateJump;
	}

	public void setCanInitiateJump(boolean canInitiateJump) {
		this.canInitiateJump = canInitiateJump;
	}

	public boolean canJumpHigher() {
		return canJumpHigher;
	}

	public void setCanJumpHigher(boolean canJumpHigher) {
		this.canJumpHigher = canJumpHigher;
	}

	public MarioStateEnum getState() {
		return state;
	}

	public void setState(MarioStateEnum pstate) {
		this.state = pstate;
	}

	public int getJumpTimer() {
		return jumpTimer;
	}

	public void incJumpTimer() {
		this.jumpTimer++;
	}

	public void setJumpTimer(int jumpTimer) {
		this.jumpTimer = jumpTimer;
	}

	public MarioStateEnum getPreviousState() {
		return previousState;
	}

	public void setPreviousState(MarioStateEnum previousState) {
		this.previousState = previousState;
	}

	public int getSizeState() {
		return sizeState;
	}

	public void setSizeState(int sizeState) {
		this.sizeState = sizeState;
	}

	public int getNbCoins() {
		return nbCoins;
	}

	public void setNbCoins(int nbCoins) {
		this.nbCoins = nbCoins;
	}
	
	public void addCoin() {
		this.nbCoins++;
	}

}
