package com.game.mario.sprite.bloc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.util.ResourcesLoader;

public class MysteryBlock extends Block {
		
	protected static float blocStateTime;
		
	public MysteryBlock(int x, int y, int tileId, BackgroundTypeEnum backgroundType) {		
		super(x,y, tileId, backgroundType);									
		setBlocType(BlockTypeEnum.MYSTERY_BLOCK);
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.MYSTERY_BLOC;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);		
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];		
		currentAnimation = new Animation(0.15f, animationFrames);
	}
	
	public static void updateStateTime(float delta) {		
		blocStateTime = blocStateTime + delta;
	}
	
	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(blocStateTime, true);		
	}

	@Override
	public void initializeAnimationsWithBackground() {		
	}
	
}
