package com.game.mario.sprite.tileobject.mario;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.item.TransferItem;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class Mario extends AbstractTileObjectSprite {

	private static final float ACCELERATION_MAX = 5f; // 7.5f;
	
	private static final float ACCELERATION_MAX_SPEEDUP = 8.5f;

	private static final float DECELERATION_COEF = 0.2f;

	private static final float ACCELERATION_COEF = 0.2f;
	
	private static final float ACCELERATION_COEF_SPEEDUP = 0.2f;
	
	private static final float HALF_WIDHT = 0.5f;;

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

	private MarioStateEnum state;

	private MarioStateEnum previousState;

	private int jumpTimer;

	private boolean canInitiateJump;

	private boolean canJumpHigher;	
	
	private boolean invincible;
	
	private float invincibleDuration;
	
	private float deathNoMoveDuration;
	
	private boolean growingUp;
	
	private boolean growingDown;
	
	private boolean inTransfer;	
	
	private TransferItem transferItem;
		
	private List<AbstractSprite> fireballs;
	
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
		sizeState = GameManager.getGameManager().getSizeState();
		changeSizeState(sizeState);		
		invincible = false;
		invincibleDuration = 0;
		alive = true;
		deathNoMoveDuration = 0;
		fireballs = new ArrayList<AbstractSprite>();
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
		setBounds(getX(), getY(), getWidth(), getHeight());
		marioRunRightAnimation = animations[i][0]; 
		marioRunLeftAnimation = animations[i][1];
		marioSlideRightAnimation =  animations[i][2];
		marioSlideLeftAnimation=  animations[i][3];				
		marioJumpRightAnimation = animations[i][4];
		marioJumpLeftAnimation = animations[i][5];
		marioDeathAnimation = animations[i][6];
		marioFlagRightAnimation = animations[i][7];
		marioFlagLeftAnimation = animations[i][8];
		marioVictoryAnimation = animations[i][9];
	}
	
	@Override
	public void initializeAnimations() {			
		initializeAnimation(ResourcesLoader.MARIO_SMALL, 0);		
		initializeAnimation(ResourcesLoader.MARIO_BIG, 1);
		initializeAnimation(ResourcesLoader.MARIO_FLOWER, 2);
		
		Texture growDownRightTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-right.png"));
		TextureRegion[][] tmp = TextureRegion.split(growDownRightTexture, growDownRightTexture.getWidth() / 3, growDownRightTexture.getHeight() / 1);
		TextureRegion[] frames = new TextureRegion[2];
		frames[0] = tmp[0][1];
		frames[1] = tmp[0][0];		
		marioGrowDownRightAnimation = new Animation(0.15f, frames);				
		
		Texture growDownLeftTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-left.png"));
		tmp = TextureRegion.split(growDownLeftTexture, growDownLeftTexture.getWidth() / 3, growDownLeftTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][1];
		frames[1] = tmp[0][0];		
		marioGrowDownLeftAnimation = new Animation(0.15f, frames);
		
		Texture growUpLeftTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-left.png"));
		tmp = TextureRegion.split(growUpLeftTexture, growUpLeftTexture.getWidth() / 3, growUpLeftTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];		
		marioGrowUpLeftAnimation = new Animation(0.15f, frames);
		
		Texture growUpRightTexture = new Texture(Gdx.files.internal("sprites/mario/mario-grow-up-right.png"));
		tmp = TextureRegion.split(growUpRightTexture, growUpRightTexture.getWidth() / 3, growUpRightTexture.getHeight() / 1);
		frames = new TextureRegion[2];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];		
		marioGrowUpRightAnimation = new Animation(0.15f, frames);
	}

	private void initializeAnimation(Texture texture, int i) {
				
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / 14,
				texture.getHeight() / 1);

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
		
		TextureRegion[] marioDeathFrames = new TextureRegion[1];
		marioDeathFrames[0] = tmp[0][10];
		marioDeathAnimation = new Animation(1, marioDeathFrames);
		
		TextureRegion[] marioFlagRightFrames = new TextureRegion[1];
		marioFlagRightFrames[0] = tmp[0][11];
		marioFlagRightAnimation = new Animation(1, marioFlagRightFrames);
			
		TextureRegion[] marioFlagLeftFrames = new TextureRegion[1];
		marioFlagLeftFrames[0] = tmp[0][12];
		marioFlagLeftAnimation = new Animation(1, marioFlagLeftFrames);
		
		TextureRegion[] marioVictoryFrames = new TextureRegion[1];
		marioVictoryFrames[0] = tmp[0][13];
		marioVictoryAnimation = new Animation(1, marioVictoryFrames);
		
		if (animations == null) {
			 animations = new Animation[3][10] ;
		}
		animations[i] = new Animation[10];
		animations[i][0] = marioRunRightAnimation; 
		animations[i][1] = marioRunLeftAnimation;
		animations[i][2] = marioSlideRightAnimation;
		animations[i][3] = marioSlideLeftAnimation;
		animations[i][4] = marioJumpRightAnimation;
		animations[i][5] = marioJumpLeftAnimation;
		animations[i][6] = marioDeathAnimation;
		animations[i][7] = marioFlagRightAnimation;
		animations[i][8] = marioFlagLeftAnimation;
		animations[i][9] = marioVictoryAnimation;
		
	}
	
	public void accelerate(boolean accelerationKeyHold) {		
		if (this.acceleration.x < (accelerationKeyHold ? ACCELERATION_MAX_SPEEDUP : ACCELERATION_MAX)) {
			this.acceleration.x = this.acceleration.x + (accelerationKeyHold ? ACCELERATION_COEF_SPEEDUP : ACCELERATION_COEF);
		}
		
		if (accelerationKeyHold==false && this.acceleration.x > ACCELERATION_MAX) {
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

	public void setStateIfNotJumping(MarioStateEnum pstate) {
		if (state != MarioStateEnum.FALLING && state != MarioStateEnum.JUMPING) {
			this.state = pstate;
		}
	}
	
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		move(deltaTime);
		collideWithTilemap(tileMap);
		updateAnimation(deltaTime);
		updateInvincibleStatus(deltaTime);
		updateAliveStatus();
	}
	
	private void updateAliveStatus() {
		setAlive(getY()>=-getHeight());			
	}

	private void updateInvincibleStatus(float deltaTime) {
		if (isInvincible() && invincibleDuration<3) {			
			invincibleDuration +=deltaTime;
		} else {
			invincible = false;
			invincibleDuration = 0;
		}
	}	
	
	public void checkHorizontalMapCollision(TmxMap tilemap) {

		reinitHorizontalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + getOffset().x, getY());
		Vector2 leftTopCorner = new Vector2(getX() + getOffset().x, getY() + getHeight() - 0.1f);
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() + getOffset().x, getY());
		Vector2 rightTopCorner = new Vector2(getX() + getWidth() + getOffset().x, getY() + getHeight() - 0.1f);

		boolean isCollision = tilemap.isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		getMapCollisionEvent().setCollidingLeft(isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		getMapCollisionEvent().setCollidingLeft(getMapCollisionEvent().isCollidingLeft() || isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);

		isCollision = tilemap.isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);

		if (sizeState>0) {
			Vector2 rightMiddle = new Vector2(getX() + 0.95f - getOffset().x, getY() + getHeight()/2 - 0.1f);
			isCollision = tilemap.isCollisioningTileAt((int) rightMiddle.x, (int) rightMiddle.y);
			getMapCollisionEvent().setCollidingRight(getMapCollisionEvent().isCollidingRight() || isCollision);
			Vector2 leftMiddle = new Vector2(getX() + getOffset().x, getY() + getHeight()/2 - 0.1f);
			isCollision = tilemap.isCollisioningTileAt((int) leftMiddle.x, (int) leftMiddle.y);
			getMapCollisionEvent().setCollidingLeft(getMapCollisionEvent().isCollidingLeft() || isCollision);
		}		
		
	}

	public void checkVerticalMapCollision(TmxMap tilemap) {

		reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + 0.1f + getOffset().x, getY());
		Vector2 leftTopCorner = new Vector2(getX() + 0.1f + getOffset().x, getY() + getHeight() - 0.1f);
		Vector2 rightBottomCorner = new Vector2(getX() + getWidth() - 0.1f + getOffset().x, getY());
		Vector2 rightTopCorner = new Vector2(getX() + getWidth() - 0.1f +getOffset().x, getY() + getHeight() - 0.1f);

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
	
	public Animation getMarioVictoryAnimation() {
		return marioVictoryAnimation;
	}

	public void setMarioVictoryAnimation(Animation marioVictoryAnimation) {
		this.marioVictoryAnimation = marioVictoryAnimation;
	}

	public void checkVerticalBottomMapCollision(TmxMap tilemap) {

		reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(getX() + 0.05f + getOffset().x, getY());
		Vector2 rightBottomCorner = new Vector2(getX() + 0.95f - getOffset().x, getY());
		
		boolean isCollision = tilemap.isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		getMapCollisionEvent().setCollidingBottom(isCollision);
		
		isCollision = tilemap.isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		getMapCollisionEvent().setCollidingBottom(getMapCollisionEvent().isCollidingBottom() || isCollision);				
	}
	
	public void checkVerticalUpperMapCollision(TmxMap tilemap) {
	
		Vector2 leftTopCorner = new Vector2(getX() + 0.05f + getOffset().x, getY() + getHeight() - 0.1f);
		Vector2 rightTopCorner = new Vector2(getX() + 0.95f - getOffset().x, getY() + getHeight() - 0.1f);
		
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

	public Animation getMarioRunRightAnimation() {
		return marioRunRightAnimation;
	}

	public void setMarioRunRightAnimation(Animation marioRunRightAnimation) {
		this.marioRunRightAnimation = marioRunRightAnimation;
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
		if (isInvincible()) {
			batch.setColor(1,1,1, 0.5f);
			super.render(batch);
			batch.setColor(1,1,1,1);
		} else {
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
	
	public void transfer(TmxMap tilemap, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds, SpriteBatch spriteBatch) {		
		setAcceleration(new Vector2(0, 0));
		setDirection(DirectionEnum.RIGHT);
		setX(transferItem.getTransferPosition().x);
		setY(transferItem.getTransferPosition().y);
		camera.setCameraOffset(2f);
		camera.getCamera().position.x = transferItem.getTransferPosition().x + 6;						
		camera.getCamera().update();			
		camera.setScrollable(transferItem.isScrollableCamera());
		scrollingBackgrounds.get(0).changeImage(transferItem.getBackgroundTypesEnum().get(0));
		if (transferItem.getBackgroundTypesEnum().size>1) {
			if (scrollingBackgrounds.size>1) {
				scrollingBackgrounds.get(1).changeImage(transferItem.getBackgroundTypesEnum().get(1));
			} else {
				scrollingBackgrounds.add(new LeftScrollingBackground(this, spriteBatch, transferItem.getBackgroundTypesEnum().get(1), 16));
			}						
		} else {
			if (scrollingBackgrounds.size>1) {
				scrollingBackgrounds.removeIndex(1);
			}			
		}
		
		tilemap.setWorldType(transferItem.getWorldTypeEnum());
		
		//scrollingBackground.changeImage(transferItem.getBackgroundTypeEnum());					
		SoundManager.getSoundManager().stopMusic();
		SoundManager.getSoundManager().setCurrentMusic(transferItem.getMusic());
		SoundManager.getSoundManager().playMusic(false);
	}

	public TransferItem getTransferItem() {
		return transferItem;
	}

	public void setTransferItem(TransferItem transferItem) {
		this.transferItem = transferItem;
	}

}
