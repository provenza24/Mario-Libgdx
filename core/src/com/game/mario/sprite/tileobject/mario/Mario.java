package com.game.mario.sprite.tileobject.mario;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.tilemap.MarioTilemapCollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.item.TransferItem;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

/**
 * sizeState values : 0=small, 1=big, 2=flowered
 * 
 * 
 *
 */
public class Mario extends AbstractTileObjectSprite {
			
	/** Offset contants */
	private static final float X_OFFSET = 0.05f;
	
	private static final float Y_OFFSET = 0.1f;
	
	private static final float ACCELERATION_MAX = 5f; // 7.5f;

	private static final float ACCELERATION_MAX_SPEEDUP = 8.5f;

	private static final float DECELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF_SPEEDUP = 0.2f;

	/** Animations tables : each state of Mario */
	private Animation animations[][];

	private Animation marioRunRightAnimation;

	private Animation marioRunLeftAnimation;

	private Animation marioSlideRightAnimation;

	private Animation marioSlideLeftAnimation;

	private Animation marioJumpLeftAnimation;

	private Animation marioJumpRightAnimation;

	private Animation marioDeathAnimation;

	private Animation marioGrowDownRightAnimation;

	private Animation marioGrowDownLeftAnimation;

	private Animation marioGrowUpRightAnimation;

	private Animation marioGrowUpLeftAnimation;

	private Animation marioFlagRightAnimation;

	private Animation marioFlagLeftAnimation;

	private Animation marioVictoryAnimation;
	
	private Animation marioStandRightAnimation;
	
	private Animation marioStandLeftAnimation;
	
	private Animation marioCrouchRightAnimation;
	
	private Animation marioCrouchLeftAnimation;

	private SpriteMoveEnum previousState;

	private int jumpTimer;

	private boolean canInitiateJump;

	private boolean canJumpHigher;

	private boolean invincible;

	private float invincibleDuration;
	
	private float invincibleDurationTarget;
	
	private boolean owningStar;

	private float deathNoMoveDuration;

	private boolean growingUp;

	private boolean growingDown;

	private boolean inTransfer;

	private TransferItem transferItem;

	private List<AbstractSprite> fireballs;	
	
	private boolean isStuck;
	
	private boolean isCrouch;

	public Mario(MapObject mapObject) {
		super(mapObject, new Vector2(X_OFFSET, Y_OFFSET));
		onFloor = true;
		canInitiateJump = true;
		canJumpHigher = true;
		gravitating = true;
		direction = DirectionEnum.RIGHT;
		state = SpriteMoveEnum.NO_MOVE;
		previousState = SpriteMoveEnum.NO_MOVE;
		sizeState = GameManager.getGameManager().getSizeState();
		changeSizeState(sizeState);
		alive = true;
		fireballs = new ArrayList<AbstractSprite>();	
		tilemapCollisionHandler = new MarioTilemapCollisionHandler();
	}

	public void changeSizeState(int i) {
		sizeState = i;
		if (i <= 1) {
			setSize(1 - 2*offset.x, 1 - offset.y);
			setRenderingSize(1, 1);
			bounds.setWidth(1 - 2*offset.x);
			bounds.setHeight(1 - offset.y);
		} else {
			setSize(1 - 2*offset.x, 2 - offset.y);
			setRenderingSize(1, 2);
			bounds.setWidth(1 - 2*offset.x);
			bounds.setHeight(2 - offset.y);
		}		
		updateBounds();		
		refreshAnimations();
	}
	
	

	@Override
	public void initializeAnimations() {
		
		animations = new Animation[5][14];
		initializeAnimation(ResourcesLoader.MARIO_SMALL, 0);
		initializeAnimation(ResourcesLoader.MARIO_BIG, 2);
		initializeAnimation(ResourcesLoader.MARIO_FLOWER, 3);		
		initGrowingAnimations();		
		initStarAnimations();
	}

	private void initializeAnimation(Texture texture, int i) {

		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / (i<=1 ? 14 :16), texture.getHeight());
			
		animations[i] = new Animation[14];
		animations[i][0] = AnimationBuilder.getInstance().build(tmp, 0, 3, 0.05f);
		animations[i][1] = AnimationBuilder.getInstance().build(tmp, 5, 3, 0.05f);
		animations[i][2] = AnimationBuilder.getInstance().build(tmp, 9, 1, 1);
		animations[i][3] = AnimationBuilder.getInstance().build(tmp, 4, 1, 1);
		animations[i][4] = AnimationBuilder.getInstance().build(tmp, 3, 1, 1);
		animations[i][5] = AnimationBuilder.getInstance().build(tmp, 8, 1, 1);
		animations[i][6] = AnimationBuilder.getInstance().build(tmp, 10, 1, 1);
		animations[i][7] = AnimationBuilder.getInstance().build(tmp, 11, 1, 1);
		animations[i][8] = AnimationBuilder.getInstance().build(tmp, 12, 1, 1);
		animations[i][9] = AnimationBuilder.getInstance().build(tmp, 13, 1, 1);
		if (i>0) {
			// Crouch animation if Mario is big
			animations[i][10] = AnimationBuilder.getInstance().build(tmp, 14, 1, 1);
			animations[i][11] = AnimationBuilder.getInstance().build(tmp, 15, 1, 1);
		}
		animations[i][12] = null;
		animations[i][13] = null;

	}
	
	private void initGrowingAnimations() {
		Texture growDownRightTexture = ResourcesLoader.MARIO_GROW_UP_RIGHT;
		TextureRegion[][] tmp = TextureRegion.split(growDownRightTexture, growDownRightTexture.getWidth() / 3, growDownRightTexture.getHeight());		
		marioGrowUpRightAnimation = AnimationBuilder.getInstance().build(tmp, 0, 2, 0.15f);
		marioGrowDownRightAnimation = AnimationBuilder.getInstance().buildReversed(tmp, 1, 2, 0.15f);
		
		Texture growDownLeftTexture = ResourcesLoader.MARIO_GROW_UP_LEFT;
		tmp = TextureRegion.split(growDownLeftTexture, growDownLeftTexture.getWidth() / 3, growDownLeftTexture.getHeight());
		marioGrowUpLeftAnimation = AnimationBuilder.getInstance().build(tmp, 0, 2, 0.15f);
		marioGrowDownLeftAnimation = AnimationBuilder.getInstance().buildReversed(tmp, 1, 2, 0.15f);
	}
	
	private void initStarAnimations() {
				
		animations[1] = new Animation[14];
		animations[1][0] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_RUN_RIGHT, 0, 24, 0.025f);
		animations[1][1] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_RUN_LEFT, 0, 24, 0.025f);
		animations[1][2] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_SLIDE_RIGHT, 0, 4, 0.025f);
		animations[1][3] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_SLIDE_LEFT, 0, 4, 0.025f);
		animations[1][4] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_JUMP_RIGHT, 0, 4, 0.025f);
		animations[1][5] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_JUMP_LEFT, 0, 4, 0.025f);		
		animations[1][7] = animations[0][7];
		animations[1][8] = animations[0][8];
		animations[1][9] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_VICTORY, 0, 4, 0.025f);;
		animations[1][10] = null;
		animations[1][11] = null;
		animations[1][12] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_STAND_RIGHT, 0, 4, 0.025f);
		animations[1][13] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_SMALL_STAR_STAND_LEFT, 0, 4, 0.025f);
		
		animations[4] = new Animation[14];
		animations[4][0] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_RUN_RIGHT, 0, 24, 0.025f);
		animations[4][1] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_RUN_LEFT, 0, 24, 0.025f);
		animations[4][2] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_SLIDE_RIGHT, 0, 4, 0.025f);
		animations[4][3] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_SLIDE_LEFT, 0, 4, 0.025f);
		animations[4][4] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_JUMP_RIGHT, 0, 4, 0.025f);
		animations[4][5] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_JUMP_LEFT, 0, 4, 0.025f);		
		animations[4][7] = animations[0][7];
		animations[4][8] = animations[0][8];
		animations[4][9] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_VICTORY, 0, 4, 0.025f);;
		animations[4][10] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_CROUCH_RIGHT, 0, 4, 0.025f);
		animations[4][11] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_CROUCH_LEFT, 0, 4, 0.025f);
		animations[4][12] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_STAND_RIGHT, 0, 4, 0.025f);
		animations[4][13] = AnimationBuilder.getInstance().build(ResourcesLoader.MARIO_BIG_STAR_STAND_LEFT, 0, 4, 0.025f);
	}
	
	public void refreshAnimations() {		
		int animationIdx = owningStar ? sizeState == 0 ? 1 : 4 : sizeState;
		marioRunRightAnimation = animations[animationIdx][0];
		marioRunLeftAnimation = animations[animationIdx][1];
		marioSlideRightAnimation = animations[animationIdx][2];
		marioSlideLeftAnimation = animations[animationIdx][3];
		marioJumpRightAnimation = animations[animationIdx][4];
		marioJumpLeftAnimation = animations[animationIdx][5];
		marioDeathAnimation = animations[animationIdx][6];
		marioFlagRightAnimation = animations[animationIdx][7];
		marioFlagLeftAnimation = animations[animationIdx][8];
		marioVictoryAnimation = animations[animationIdx][9];
		marioCrouchRightAnimation = animations[animationIdx][10];
		marioCrouchLeftAnimation = animations[animationIdx][11];		
		marioStandRightAnimation = animations[animationIdx][12];
		marioStandLeftAnimation = animations[animationIdx][13];
	}

	public void accelerate(boolean accelerationKeyHold) {
		if (this.acceleration.x < (accelerationKeyHold ? ACCELERATION_MAX_SPEEDUP : ACCELERATION_MAX)) {
			this.acceleration.x = this.acceleration.x
					+ (accelerationKeyHold ? ACCELERATION_COEF_SPEEDUP : ACCELERATION_COEF);
		}

		if (accelerationKeyHold == false && this.acceleration.x > ACCELERATION_MAX) {
			this.acceleration.x = ACCELERATION_MAX;
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

	public void setStateIfNotJumping(SpriteMoveEnum pstate) {
		if (state != SpriteMoveEnum.FALLING && state != SpriteMoveEnum.JUMPING && !isCrouch) {
			this.state = pstate;
		}
	}
	
	public void move(float deltaTime) {
		
		storeOldPosition();
		
		float xVelocity = deltaTime * acceleration.x;
		xVelocity = direction == DirectionEnum.LEFT ? -xVelocity : xVelocity;
		setX(getX() + xVelocity);
		
		if (!isStuck) {
			applyGravity();			
		}
		setY(getY() + acceleration.y);
	}
	
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		move(deltaTime);		
		tilemapCollisionHandler.collideWithTilemap(tileMap, this);
		updateBounds();
		updateAnimation(deltaTime);
		updateInvincibleStatus(deltaTime);
		updateAliveStatus();
	}

	private void updateAliveStatus() {
		setAlive(getY() >= -getHeight());
	}

	private void updateInvincibleStatus(float deltaTime) {
		
		if (isOwningStar() && invincibleDuration >= invincibleDurationTarget-2) {
			SoundManager.getSoundManager().stopMusic();
			SoundManager.getSoundManager().playMusic(true);
		}		
		if (isInvincible() && invincibleDuration < invincibleDurationTarget) {
			invincibleDuration += deltaTime;
		} else {
			invincible = false;
			if (owningStar) {
				owningStar = false;
				refreshAnimations();
			}							
			invincibleDuration = 0;
		}
	}		

	public Animation getMarioVictoryAnimation() {
		return marioVictoryAnimation;
	}

	public void setMarioVictoryAnimation(Animation marioVictoryAnimation) {
		this.marioVictoryAnimation = marioVictoryAnimation;
	}

	public void setDeathAnimation() {
		currentAnimation = marioDeathAnimation;
		currentFrame = currentAnimation.getKeyFrame(0, false);
		acceleration.x = 0;
		acceleration.y = 0.2f;
	}

	public void setGrowDownAnimation() {
		currentAnimation = direction == DirectionEnum.RIGHT ? marioGrowDownRightAnimation : marioGrowDownLeftAnimation;
		currentFrame = currentAnimation.getKeyFrame(1, false);
	}

	public void setGrowUpAnimation() {
		currentAnimation = direction == DirectionEnum.RIGHT ? marioGrowUpRightAnimation : marioGrowUpLeftAnimation;
		currentFrame = currentAnimation.getKeyFrame(1, false);
	}

	public void updateCinematicAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}

	public void updateAnimation(float delta) {

		stateTime = stateTime + delta;

		if (isCrouch) {
			currentAnimation = direction == DirectionEnum.RIGHT ? marioCrouchRightAnimation : marioCrouchLeftAnimation;
			if (!isOwningStar()) {
				currentFrame = currentAnimation.getKeyFrame(0, false);
			} else {
				currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			}			
		} else if (!onFloor) {
			currentAnimation = direction == DirectionEnum.RIGHT ? marioJumpRightAnimation : marioJumpLeftAnimation;
			currentFrame = currentAnimation.getKeyFrame(owningStar ? stateTime : 0, owningStar ? true :false);
		} else { //if (getState() != SpriteMoveEnum.JUMPING && getState() != SpriteMoveEnum.FALLING) {
			float xMove = getX() - getOldPosition().x;
			if (xMove == 0) {				
				if (getState() == SpriteMoveEnum.SLIDING_LEFT) {
					setDirection(DirectionEnum.RIGHT);
				} else if (getState() == SpriteMoveEnum.SLIDING_RIGHT) {
					setDirection(DirectionEnum.LEFT);
				}
				setState(SpriteMoveEnum.NO_MOVE);
				if (!isOwningStar()) {
					currentAnimation = direction == DirectionEnum.RIGHT ? marioRunRightAnimation : marioRunLeftAnimation;
					currentFrame = currentAnimation.getKeyFrame(0, false);
				} else {					
					if (direction==DirectionEnum.RIGHT && currentAnimation!=marioStandRightAnimation) {						
						currentAnimation = marioStandRightAnimation;
					} else if (direction==DirectionEnum.LEFT && currentAnimation!=marioStandLeftAnimation) {						
						currentAnimation = marioStandLeftAnimation;
					}					
					currentFrame = currentAnimation.getKeyFrame(stateTime, true);
				}
								
			} else {
				currentAnimation = 						
						state == SpriteMoveEnum.RUNNING_LEFT ? marioRunLeftAnimation
						: state == SpriteMoveEnum.RUNNING_RIGHT ? marioRunRightAnimation
								: state == SpriteMoveEnum.SLIDING_LEFT ? marioSlideLeftAnimation
										: state == SpriteMoveEnum.SLIDING_RIGHT ? marioSlideRightAnimation
												: direction == DirectionEnum.RIGHT ? marioRunRightAnimation
														: marioRunLeftAnimation;
				currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			}			
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

	public SpriteMoveEnum getState() {
		return state;
	}

	public void setState(SpriteMoveEnum pstate) {
		this.state = pstate;
	}

	public int getJumpTimer() {
		return jumpTimer;
	}

	public boolean isStuck() {
		return isStuck;
	}

	public void setStuck(boolean isStuck) {
		this.isStuck = isStuck;
	}

	public void incJumpTimer() {
		this.jumpTimer++;
	}

	public void setJumpTimer(int jumpTimer) {
		this.jumpTimer = jumpTimer;
	}

	public Animation getMarioRunRightAnimation() {
		return marioRunRightAnimation;
	}

	public void setMarioRunRightAnimation(Animation marioRunRightAnimation) {
		this.marioRunRightAnimation = marioRunRightAnimation;
	}

	public SpriteMoveEnum getPreviousState() {
		return previousState;
	}

	public void setPreviousState(SpriteMoveEnum previousState) {
		this.previousState = previousState;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public void dispose() {
		spriteSheet.dispose();
	}

	@Override
	public void render(Batch batch) {
		if (isInvincible() && !owningStar) {
			batch.setColor(1, 1, 1, 0.5f);
			super.render(batch);
			batch.setColor(1, 1, 1, 1);
		}  else {
			super.render(batch);
		}
	}

	public float getDeathNoMoveDuration() {
		return deathNoMoveDuration;
	}

	public void setDeathNoMoveDuration(float deathNoMoveDuration) {
		this.deathNoMoveDuration = deathNoMoveDuration;
	}

	public boolean isGrowingUp() {
		return growingUp;
	}

	public void setGrowingUp(boolean growingUp) {
		this.growingUp = growingUp;
	}

	public boolean isGrowingDown() {
		return growingDown;
	}

	public float getInvincibleDurationTarget() {
		return invincibleDurationTarget;
	}

	public void setInvincibleDurationTarget(float invincibleDurationTarget) {
		this.invincibleDurationTarget = invincibleDurationTarget;
	}

	public void setGrowingDown(boolean growingDown) {
		this.growingDown = growingDown;
	}

	public boolean isGrowing() {
		return isGrowingDown() || isGrowingUp();
	}

	public List<AbstractSprite> getFireballs() {
		return fireballs;
	}

	public void setFireballs(List<AbstractSprite> fireballs) {
		this.fireballs = fireballs;
	}

	public Animation getMarioFlagRightAnimation() {
		return marioFlagRightAnimation;
	}

	public void setMarioFlagRightAnimation(Animation marioFlagRightAnimation) {
		this.marioFlagRightAnimation = marioFlagRightAnimation;
	}

	public Animation getMarioFlagLeftAnimation() {
		return marioFlagLeftAnimation;
	}

	public void setMarioFlagLeftAnimation(Animation marioFlagLeftAnimation) {
		this.marioFlagLeftAnimation = marioFlagLeftAnimation;
	}

	public boolean isInTransfer() {
		return inTransfer;
	}

	public void setInTransfer(boolean inTransfer) {
		this.inTransfer = inTransfer;
	}

	public void transfer(TmxMap tilemap, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds,
			SpriteBatch spriteBatch) {
		if (isCrouch) {
			// Maybe we're coming from an horinzontal pipe, Mario had to crouch to get into
			uncrouch();
		}
		setOnFloor(true);
		setState(SpriteMoveEnum.NO_MOVE);
		setAcceleration(new Vector2(0, 0));
		setDirection(DirectionEnum.RIGHT);
		setX(transferItem.getTransferPosition().x);
		setY(transferItem.getTransferPosition().y);
		camera.setCameraOffset(2f);
		camera.getCamera().position.x = transferItem.getTransferPosition().x + 6;
		camera.getCamera().update();
		camera.setScrollable(transferItem.isScrollableCamera());
		scrollingBackgrounds.get(0).changeImage(transferItem.getBackgroundTypesEnum().get(0));
		if (transferItem.getBackgroundTypesEnum().size > 1) {
			if (scrollingBackgrounds.size > 1) {
				scrollingBackgrounds.get(1).changeImage(transferItem.getBackgroundTypesEnum().get(1));
			} else {
				scrollingBackgrounds.add(new LeftScrollingBackground(this, spriteBatch,
						transferItem.getBackgroundTypesEnum().get(1), 16));
			}
		} else {
			if (scrollingBackgrounds.size > 1) {
				scrollingBackgrounds.removeIndex(1);
			}
		}

		tilemap.setWorldType(transferItem.getWorldTypeEnum());
		
		SoundManager.getSoundManager().stopMusic();
		SoundManager.getSoundManager().setCurrentMusic(transferItem.getMusic());
		SoundManager.getSoundManager().playMusic(false);
	}

	public TransferItem getTransferItem() {
		return transferItem;
	}

	public boolean isOwningStar() {
		return owningStar;
	}

	public void setOwningStar(boolean owningStar) {
		this.owningStar = owningStar;
	}

	public void setTransferItem(TransferItem transferItem) {
		this.transferItem = transferItem;
	}
	
	public void crouch() {
		isCrouch = true;		
		setSize(1 - 2*offset.x, 1f - offset.y);		
		bounds.setWidth(1 - 2*offset.x);
		bounds.setHeight(1 - offset.y);						
		updateBounds();				
	}
	
	public void uncrouch() {		
		isCrouch = false;		
		setSize(1 - 2*offset.x, 2 - offset.y);		
		bounds.setWidth(1 - 2*offset.x);
		bounds.setHeight(2 - offset.y);	
		updateBounds();		
	}

	public boolean isCrouch() {
		return isCrouch;
	}

	public void setCrouch(boolean isCrouch) {
		this.isCrouch = isCrouch;
	}
			
}
