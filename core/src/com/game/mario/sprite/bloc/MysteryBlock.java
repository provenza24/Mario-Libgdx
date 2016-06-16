package com.game.mario.sprite.bloc;

import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class MysteryBlock extends Block {
				
	public MysteryBlock(int x, int y, int tileId, WorldTypeEnum backgroundType) {		
		super(x,y, tileId, backgroundType);									
		setBlocType(BlockTypeEnum.MYSTERY_BLOCK);
	}	

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.MYSTERY_BLOC, 0, 3, 0.15f);		
	}
	
	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(commonStateTime, true);		
	}

	@Override
	public void initializeAnimationsWithBackground() {		
	}
	
}
