package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.ResourcesLoader;
import com.game.mario.enums.KoopaStateEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class Koopa extends AbstractTileObjectSprite {
		
	private Animation walkLeftAnimation;
	
	private Animation walkRightAnimation;
	
	private Animation slideAnimation;
	
	private KoopaStateEnum koopaState;
	
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

		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 5,
				spriteSheet.getHeight() / 1);

		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkLeftAnimation = new Animation(0.15f, walkFrames);

		TextureRegion[] slideFrames = new TextureRegion[3];
		slideFrames[0] = tmp[0][2];
		slideFrames[1] = tmp[0][3];
		slideFrames[2] = tmp[0][4];
		slideAnimation = new Animation(0.04f, slideFrames);
		
		TextureRegion[] killedFrames = new TextureRegion[1];
		killedFrames[0] = tmp[0][3];
		killedAnimation = new Animation(0, killedFrames);
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
				currentAnimation = killedAnimation;			
			}
		}
		return isEnemyHit;
	}
	
	public void kill() {
	}
	
	public void updateAnimation(float delta) {
		// TODO : change here the current animation
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);		
	}
}
