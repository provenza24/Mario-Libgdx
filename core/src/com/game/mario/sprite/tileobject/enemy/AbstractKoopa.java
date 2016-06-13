package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractKoopa extends AbstractTileObjectEnemy {

	protected Animation walkLeftAnimation;
	
	protected Animation walkRightAnimation;

	protected Animation slideAnimation;
	
	protected Animation wakeUpAnimation;
	
	protected Animation bumpAnimation;
	
	protected Animation flyAnimation;
	
	protected float noMoveTime;
	
	protected static final float ACCELERATION = 0.00055f;

	protected static final float ACCELERATION_MIN = 0.00025f;
	
	protected static final float ACCELERATION_MAX = 0.08f;
	
	protected float DECCELERATION_STEP;
	
	protected float STEP_NUMBER;		
	
	protected float currentStep;
	
	public AbstractKoopa(MapObject mapObject) {
		
		super(mapObject, new Vector2(0.2f, 0.1f));				
		setSize(1 - offset.x * 2, 1 - offset.y);
		
		String sState = (String)mapObject.getProperties().get("state");
		if ("flying".equals(sState)) {
			collidableWithTilemap = false;
			gravitating = false;
			onFloor = false;
			currentAnimation = flyAnimation;
			state = SpriteStateEnum.FLYING;
			direction = DirectionEnum.DOWN;		
			STEP_NUMBER = 6;
			DECCELERATION_STEP = STEP_NUMBER/2 + 0.01f;
			acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;		
		} else {
			gravitating = true;
			currentAnimation = walkLeftAnimation;
			acceleration.x = -1.9f;
			acceleration.y = 0;
			onFloor = true;			
		}								
		bounds = new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());	
		
	}
	
	@Override
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.KOOPA;
	}

	@Override
	public abstract void initializeAnimations();
	
	@Override
	public void move(float deltaTime) {
		
		super.move(deltaTime);
		if (state==SpriteStateEnum.FLYING) {
			if (currentStep<=STEP_NUMBER) {				
				float positiveAcceleration =  Math.abs(acceleration.y);
				currentStep = currentStep + positiveAcceleration;
				if (currentStep>=DECCELERATION_STEP) {
					acceleration.y += positiveAcceleration > ACCELERATION_MIN ? direction==DirectionEnum.UP ? -ACCELERATION : ACCELERATION : 0;
				} else {
					acceleration.y += positiveAcceleration < ACCELERATION_MAX ? direction==DirectionEnum.UP ? ACCELERATION : -ACCELERATION : 0;
				}
				 
			} else {
				currentStep = 0;
				direction = direction==DirectionEnum.UP ? DirectionEnum.DOWN : DirectionEnum.UP;
				acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;
			}			
		} 		
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		
		super.update(tileMap, camera, deltaTime);
		
		if (isAlive()) {
			if (!bumped && state==SpriteStateEnum.NO_MOVE) {									
				noMoveTime = noMoveTime + deltaTime;
				if (noMoveTime<5) {
					// nothing to do
				} else if (noMoveTime>=5 && noMoveTime<=8) {
					currentAnimation = wakeUpAnimation;
				} else {
					setSize(1 - offset.x * 2, 1);
					renderingSize.y = 1.5f;
					currentAnimation = walkLeftAnimation;
					acceleration.x = -1.9f;		
					bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
					state = SpriteStateEnum.WALKING;
				} 
			}			
		}			
	}
	
	@Override
	public void updateAnimation(float delta) {		
		if (!bumped && state==SpriteStateEnum.WALKING) {
			if (acceleration.x>0 && currentAnimation!=walkRightAnimation) {
				currentAnimation = walkRightAnimation;
				
			} else if (acceleration.x<0 && currentAnimation!=walkLeftAnimation) {
				currentAnimation = walkLeftAnimation;
			}
		}
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);		
	}
	
	@Override
	public boolean collideMario(Mario mario) {
				
		boolean isEnemyHit = false;
		
		if (mario.isOwningStar()) {
			bump();
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);		
		} else {
			if (state == SpriteStateEnum.FLYING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteStateEnum.FALLING;
				if (isEnemyHit) {
					mario.setY(getY()+getHeight());					
					mario.getAcceleration().y = 0.2f;	
					currentAnimation = walkLeftAnimation;
					acceleration.x = -1.9f;		
					bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
					state = SpriteStateEnum.FALLING_AFTER_FLY;
					collidableWithTilemap = true;
					gravitating = true;
				}
			} else if (state == SpriteStateEnum.WALKING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteStateEnum.FALLING;
				if (isEnemyHit) {
					setSize(1 - offset.x * 2, 0.875f);	
					bounds.set(getX(), getY(), getWidth(), getHeight());				
					mario.getAcceleration().y = 0.15f;				
					acceleration.x = 0;
					state = SpriteStateEnum.NO_MOVE;
					currentAnimation = killedAnimation;
					noMoveTime = 0;
					mario.setY(getY()+1);
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);				
				}	
			} else if (state == SpriteStateEnum.NO_MOVE) {			
				isEnemyHit = true;
				acceleration.x = mario.getX()+mario.getWidth()/2 < getX()+getWidth()/2 ? 10 : -10;
				setX(acceleration.x>0 ? mario.getX()+mario.getWidth()+0.1f :  mario.getX()-1f);
				state = SpriteStateEnum.SLIDING;
				currentAnimation = slideAnimation;			
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
			} else if (state == SpriteStateEnum.SLIDING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteStateEnum.FALLING;
				if (isEnemyHit) {
					mario.setY(getY()+1);
					mario.getAcceleration().y = 0.15f;				
					acceleration.x = 0;
					state = SpriteStateEnum.NO_MOVE;
					noMoveTime = 0;
					currentAnimation = killedAnimation;
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
				}
			}
		}		
		return isEnemyHit;
	}
	
	public void kill() {
	}
	
	@Override
	public void killByFireball(AbstractSprite fireball) {		
		if (!isBumped()) {
			super.bump();
			gravitating = true;
			collidableWithTilemap = false;
			this.currentAnimation = bumpAnimation;
			acceleration.x = fireball.getAcceleration().x > 0 ? 3 : -3;
			acceleration.y = 0.15f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
		} 	
	}
	
	@Override
	public void bump() {
		if (!isBumped()) {
			super.bump();
			gravitating = true;
			collidableWithTilemap = false;
			this.currentAnimation = bumpAnimation;
			acceleration.x = getAcceleration().x > 0 ? 3 : -3;
			acceleration.y = 0.15f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
		}
	}
	
	protected void initializeTextures() {
		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 10, spriteSheet.getHeight() / 1);

		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkLeftAnimation = new Animation(0.15f, walkFrames);
		
		TextureRegion[] walkRightFrames = new TextureRegion[2];
		walkRightFrames[0] = tmp[0][5];
		walkRightFrames[1] = tmp[0][6];
		walkRightAnimation = new Animation(0.15f, walkRightFrames);

		TextureRegion[] slideFrames = new TextureRegion[3];
		slideFrames[0] = tmp[0][2];
		slideFrames[1] = tmp[0][3];
		slideFrames[2] = tmp[0][4];
		slideAnimation = new Animation(0.04f, slideFrames);
		
		TextureRegion[] killedFrames = new TextureRegion[1];
		killedFrames[0] = tmp[0][3];
		killedAnimation = new Animation(0, killedFrames);
		
		TextureRegion[] wakeUpFrames = new TextureRegion[2];
		wakeUpFrames[0] = tmp[0][2];		
		wakeUpFrames[1] = tmp[0][4];
		wakeUpAnimation = new Animation(0.1f, wakeUpFrames);
		
		TextureRegion[] bumpedFrames = new TextureRegion[1];
		bumpedFrames[0] = tmp[0][7];				
		bumpAnimation = new Animation(0, bumpedFrames);
	
		TextureRegion[] flyFrames = new TextureRegion[2];
		flyFrames[0] = tmp[0][8];
		flyFrames[1] = tmp[0][9];		
		flyAnimation = new Animation(0.15f, flyFrames);		
	}

}
