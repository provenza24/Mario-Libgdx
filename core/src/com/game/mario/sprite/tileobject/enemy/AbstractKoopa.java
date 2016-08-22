package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.animation.AnimationBuilder;

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
	
	protected static final float STEP_NUMBER = 6;	
	
	protected float DECCELERATION_STEP;
		
	protected float currentStep;
	
	public AbstractKoopa(MapObject mapObject) {
		
		super(mapObject, new Vector2(0.2f, 0.1f));				
		
		setSize(1 - offset.x * 2, 1 - offset.y);
		bounds = new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		
		String sState = (String)mapObject.getProperties().get("state");
		if ("flying".equals(sState)) {
			collidableWithTilemap = false;
			gravitating = false;
			onFloor = false;
			currentAnimation = flyAnimation;
			state = SpriteMoveEnum.FLYING;
			direction = DirectionEnum.DOWN;		
			DECCELERATION_STEP = STEP_NUMBER/2 + 0.01f;
			acceleration.y = direction==DirectionEnum.UP ? ACCELERATION_MIN : -ACCELERATION_MIN;		
		} else {			
			currentAnimation = walkLeftAnimation;
			acceleration.x = -1.9f;
			acceleration.y = 0;
			onFloor = true;			
		}										
		
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
		if (state==SpriteMoveEnum.FLYING) {
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
			if (!bumped && state==SpriteMoveEnum.NO_MOVE) {									
				noMoveTime = noMoveTime + deltaTime;
				if (noMoveTime<5) {
					// nothing to do
				} else if (noMoveTime>=5 && noMoveTime<=8) {
					currentAnimation = wakeUpAnimation;
				} else {
					//setSize(1 - offset.x * 2, 1);
					//renderingSize.y = 1.5f;
					currentAnimation = walkLeftAnimation;
					acceleration.x = -1.9f;		
					bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
					state = SpriteMoveEnum.WALKING;
				} 
			}			
		}			
	}
	
	@Override
	public void updateAnimation(float delta) {		
		if (!bumped && state==SpriteMoveEnum.WALKING) {
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
			if (state == SpriteMoveEnum.FLYING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteMoveEnum.FALLING;
				if (isEnemyHit) {
					mario.setY(getY()+getHeight());					
					mario.getAcceleration().y = 0.2f;	
					currentAnimation = walkLeftAnimation;
					acceleration.x = -1.9f;		
					bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
					state = SpriteMoveEnum.FALLING_AFTER_FLY;
					collidableWithTilemap = true;
					gravitating = true;
				}
			} else if (state == SpriteMoveEnum.WALKING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteMoveEnum.FALLING;
				if (isEnemyHit) {
					//setSize(1 - offset.x * 2, 0.875f);	
					bounds.set(getX(), getY(), getWidth(), getHeight());				
					mario.getAcceleration().y = 0.15f;				
					acceleration.x = 0;
					state = SpriteMoveEnum.NO_MOVE;
					currentAnimation = killedAnimation;
					noMoveTime = 0;
					mario.setY(getY()+1);
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);				
				}	
			} else if (state == SpriteMoveEnum.NO_MOVE) {			
				isEnemyHit = true;
				acceleration.x = mario.getX()+mario.getWidth()/2 < getX()+getWidth()/2 ? 10 : -10;
				setX(acceleration.x>0 ? mario.getX()+mario.getWidth()+0.1f :  mario.getX()-1f);
				state = SpriteMoveEnum.SLIDING;
				currentAnimation = slideAnimation;			
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
			} else if (state == SpriteMoveEnum.SLIDING) {
				isEnemyHit = mario.getY() > getY() && mario.getState() == SpriteMoveEnum.FALLING;
				if (isEnemyHit) {
					mario.setY(getY()+1);
					mario.getAcceleration().y = 0.15f;				
					acceleration.x = 0;
					state = SpriteMoveEnum.NO_MOVE;
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
		
		TextureRegion[][] textureRegions = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 11, spriteSheet.getHeight() / 1);
		/*walkLeftAnimation = AnimationBuilder.getInstance().build(textureRegions, 0, 2, 0.15f);
		walkRightAnimation = AnimationBuilder.getInstance().build(textureRegions, 5, 2, 0.15f);
		slideAnimation = AnimationBuilder.getInstance().build(textureRegions, 2, 3, 0.04f);
		killedAnimation = AnimationBuilder.getInstance().build(textureRegions, 3, 1, 1f);
		bumpAnimation = AnimationBuilder.getInstance().build(textureRegions, 7, 1, 1f);		
		flyAnimation = AnimationBuilder.getInstance().build(textureRegions, 8, 2, 0.15f);
		wakeUpAnimation = AnimationBuilder.getInstance().build(textureRegions, new int[] {2,4}, 0.1f);*/
		
		walkLeftAnimation = AnimationBuilder.getInstance().build(textureRegions, 0, 3, 0.15f);
		walkRightAnimation = AnimationBuilder.getInstance().build(textureRegions, 3, 3, 0.15f);
		slideAnimation = AnimationBuilder.getInstance().build(textureRegions, 6, 4, 0.04f);
		killedAnimation = AnimationBuilder.getInstance().build(textureRegions, 6, 1, 1f);
		bumpAnimation = AnimationBuilder.getInstance().build(textureRegions, 10, 1, 1f);
		wakeUpAnimation = AnimationBuilder.getInstance().build(textureRegions, new int[] {7,9}, 0.1f);
	}

}
