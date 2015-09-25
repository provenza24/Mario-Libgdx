package com.mygdx.game.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Mario {

	private Texture marioSpriteSheet;
	
	private TextureRegion[] marioRunRightFrames; 
	
	private Animation marioRunRightAnimation;
	
	private float marioStateTime;
			
	Vector2 position;
	
	public Mario() {
		
		position = new Vector2(0,1);
		
		marioSpriteSheet = new Texture(Gdx.files.internal("mario.gif"));

		TextureRegion[][] tmp = TextureRegion.split(marioSpriteSheet, marioSpriteSheet.getWidth() / 14, marioSpriteSheet.getHeight() / 1);
		
		marioRunRightFrames = new TextureRegion[3];
		marioRunRightFrames[0] = tmp[0][0];
		marioRunRightFrames[1] = tmp[0][1];
		marioRunRightFrames[2] = tmp[0][2];		
		marioRunRightAnimation = new Animation(0.05f, marioRunRightFrames);
		
		marioStateTime = 0f;
	}

	public Texture getMarioSpriteSheet() {
		return marioSpriteSheet;
	}

	public void setMarioSpriteSheet(Texture marioSpriteSheet) {
		this.marioSpriteSheet = marioSpriteSheet;
	}

	public TextureRegion[] getMarioRunRightFrames() {
		return marioRunRightFrames;
	}

	public void setMarioRunRightFrames(TextureRegion[] marioRunRightFrames) {
		this.marioRunRightFrames = marioRunRightFrames;
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
	
}
