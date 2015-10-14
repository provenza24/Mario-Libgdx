package com.mygdx.game.mario.sprite.tileobject.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.enums.MarioStateEnum;
import com.mygdx.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.mygdx.game.mario.tilemap.TmxMap;

public class Mario extends AbstractTileObjectSprite {

	private static final float ACCELERATION_MAX = 5; // 7.5f;

	private static final float DECELERATION_COEF = 0.4f;

	private static final float ACCELERATION_COEF = 0.2f;

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
		
	public Mario(MapObject mapObject) {
		super(mapObject);		
		setSize(1, 1);		
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
	}

	@Override
	public void initializeAnimations() {
		
		spriteSheet = new Texture(Gdx.files.internal("sprites/mario.gif"));

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
		
	}
	
	public void accelerate() {
		if (this.acceleration.x < ACCELERATION_MAX) {
			this.acceleration.x = this.acceleration.x + ACCELERATION_COEF;
		}
	}

	public void decelerate() {
		if (this.acceleration.x > 0) {
			this.acceleration.x = this.acceleration.x - DECELERATION_COEF;
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
	
	public void collideWithTilemap(TmxMap tileMap) {

		tileMap.checkVerticalMapCollision(this);
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
		} else if (yMove > 0) {
			if (getMapCollisionEvent().isCollidingTop()) {
				setY(getOldPosition().y);
				getAcceleration().y = 0;
				setState(MarioStateEnum.FALLING);
			}
		}

		tileMap.checkHorizontalMapCollision(this);
		float xMove = getX() - getOldPosition().x;
		if (xMove > 0 && getMapCollisionEvent().isCollidingRight()
				|| xMove < 0 && getMapCollisionEvent().isCollidingLeft()) {
			// Mario is colliding on his right or left
			setX(getOldPosition().x);
			getAcceleration().x = 0;
		}

		onFloor = getMapCollisionEvent().isCollidingBottom();
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

}
