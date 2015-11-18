package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.ResourcesLoader;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.KoopaStateEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class Koopa extends AbstractEnemy {
		
	private Animation walkLeftAnimation;
	
	private Animation walkRightAnimation;

	private Animation slideAnimation;
	
	private Animation wakeUpAnimation;
	
	private KoopaStateEnum koopaState;
	
	private float noMoveTime;
	
	public Koopa(MapObject mapObject) {

		super(mapObject);
		offset.x = 0.2f;
		setSize(1 - offset.x * 2, 1.5f);
		renderingSize.y = 1.5f;
		currentAnimation = walkLeftAnimation;
		acceleration.x = -1.9f;		
		gravitating = true;
		bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		koopaState = KoopaStateEnum.WALKING;
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.KOOPA;

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 7,
				spriteSheet.getHeight() / 1);

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
		
	}

	public boolean collideMario(Mario mario) {
		boolean isEnemyHit = false;
		if (koopaState == KoopaStateEnum.WALKING) {
			isEnemyHit = mario.getY() > getY() && mario.getState() == MarioStateEnum.FALLING;
			if (isEnemyHit) {
				setSize(1 - offset.x * 2, 0.875f);	
				bounds.set(getX(), getY(), getWidth(), getHeight());				
				mario.getAcceleration().y = 0.15f;
				ResourcesLoader.SOUND_KICK.play();	
				acceleration.x = 0;
				koopaState = KoopaStateEnum.NO_MOVE;
				currentAnimation = killedAnimation;
				noMoveTime = 0;
			}	
		} else if (koopaState == KoopaStateEnum.NO_MOVE) {
			isEnemyHit = true;
			acceleration.x = mario.getX()+mario.getWidth()/2 < getX()+getWidth()/2 ? 10 : -10;
			setX(acceleration.x>0 ? mario.getX()+mario.getWidth()+0.1f :  mario.getX()-1f);
			koopaState = KoopaStateEnum.SLIDING;
			currentAnimation = slideAnimation;			
		} else if (koopaState == KoopaStateEnum.SLIDING) {
			isEnemyHit = mario.getY() > getY() && mario.getState() == MarioStateEnum.FALLING;
			if (isEnemyHit) {
				mario.getAcceleration().y = 0.15f;
				ResourcesLoader.SOUND_KICK.play();	
				acceleration.x = 0;
				koopaState = KoopaStateEnum.NO_MOVE;
				noMoveTime = 0;
				currentAnimation = killedAnimation;			
			}
		}
		return isEnemyHit;
	}
	
	public void kill() {
	}
	
	public void updateAnimation(float delta) {		
		if (koopaState==KoopaStateEnum.WALKING) {
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
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.KOOPA;
	}

	@Override
	// TODO : remonter ça au niveau enemy
	public KoopaStateEnum getEnemyState() {		
		return koopaState;
	}

	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {
		// TODO Auto-generated method stub
		super.update(tileMap, camera, deltaTime);
		if (koopaState==KoopaStateEnum.NO_MOVE) {									
			noMoveTime = noMoveTime + deltaTime;
			if (noMoveTime<5) {
				// nothing to do
			} else if (noMoveTime>=5 && noMoveTime<=8) {
				currentAnimation = wakeUpAnimation;
			} else {
				setSize(1 - offset.x * 2, 1.5f);
				renderingSize.y = 1.5f;
				currentAnimation = walkLeftAnimation;
				acceleration.x = -1.9f;		
				bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
				koopaState = KoopaStateEnum.WALKING;
			} 
		}
		if (isAlive() && camera.position.x < tileMap.getFlag().getX()) {
			deletable = camera.position.x+8<getX();
		} 
	}
	
}
