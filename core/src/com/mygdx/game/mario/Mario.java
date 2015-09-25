package com.mygdx.game.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.DirectionEnum;

public class Mario {

	private DirectionEnum direction;
	
	private Texture marioSpriteSheet;
		
	private Animation marioRunRightAnimation;
	
	private Animation marioRunLeftAnimation;
	
	private float marioStateTime;
	
	private Vector2 acceleration;
	
	private Vector2 position;
	
	private Vector2 oldPosition;
		
	public Mario() {
		
		oldPosition = new Vector2(0,1);
		position = new Vector2(0,1);
		acceleration = new Vector2();
		
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
		
		marioStateTime = 0f;
		
		direction = DirectionEnum.RIGHT;
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

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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
		if (this.acceleration.x<5) {
			this.acceleration.x = this.acceleration.x + 0.1f;
		}
	}
	
	public void decelerate() {
		if (this.acceleration.x>0) {
			this.acceleration.x = this.acceleration.x - 0.3f; 
		}
		if (this.acceleration.x<0) {
			this.acceleration.x = 0;
		}
	}

	public void storeOldPosition() {
		oldPosition.set(position);
	}
	
}
