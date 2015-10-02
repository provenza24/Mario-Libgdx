package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MysteryBlock extends Sprite {
	
	private Texture spriteSheet;
			
	private static Animation animation; 
	
	private static TextureRegion currentFrame;		
		
	private static float stateTime;
		
	private boolean visible;
	
	public MysteryBlock(int x, int y) {
							
		setPosition(x, y);

		spriteSheet = new Texture(Gdx.files.internal("mystery.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);		
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];		
		animation = new Animation(0.15f, animationFrames);
				
		visible = false;
		stateTime = 0f;				
	}
	
	public static void updateAnimation(float delta) {
		stateTime = stateTime + delta;
    	currentFrame = animation.getKeyFrame(stateTime, true);    	
	}

	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Texture spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
}
