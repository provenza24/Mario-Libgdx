package com.game.mario.util.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationBuilder {
	
	private static AnimationBuilder instance = new AnimationBuilder();

	private AnimationBuilder() {		
	}
	
	public static AnimationBuilder getInstance() {
		return instance;
	}
	
	public Animation createAnimation(Texture texture, int startFrameIndex, int nbFrames, float frameDelay) {
			
		TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth()/nbFrames, texture.getHeight());		
		TextureRegion[] frames = new TextureRegion[nbFrames];
		for (int i=0;i<nbFrames;i++) {			
			frames[i] = textureRegions[0][startFrameIndex+i];
		}		
		return new Animation(frameDelay, frames);		
	}
	
	public Animation createAnimation(TextureRegion[][] textureRegions, int startFrameIndex, int nbFrames, float frameDelay) {
		
		TextureRegion[] frames = new TextureRegion[nbFrames];
		for (int i=0;i<nbFrames;i++) {			
			frames[i] = textureRegions[0][startFrameIndex+i];
		}		
		return new Animation(frameDelay, frames);		
	}
	
	public Animation createReversedAnimation(Texture texture, int startFrameIndex, int nbFrames, float frameDelay) {
		
		TextureRegion[][] textureRegions = TextureRegion.split(texture, texture.getWidth()/nbFrames, texture.getHeight());		
		TextureRegion[] frames = new TextureRegion[nbFrames];
		int j = 0;
		for (int i=startFrameIndex;i>=startFrameIndex+1-nbFrames;i--) {			
			frames[j] = textureRegions[0][i];
			j++;
		}		
		return new Animation(frameDelay, frames);		
	}
	
	public Animation createReversedAnimation(TextureRegion[][] textureRegions, int startFrameIndex, int nbFrames, float frameDelay) {		
		TextureRegion[] frames = new TextureRegion[nbFrames];
		int j = 0;
		for (int i=startFrameIndex;i>=startFrameIndex+1-nbFrames;i--) {			
			frames[j] = textureRegions[0][i];
			j++;
		}		
		return new Animation(frameDelay, frames);		
	}
	
	

}
