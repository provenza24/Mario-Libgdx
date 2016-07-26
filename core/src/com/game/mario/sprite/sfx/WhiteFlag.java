package com.game.mario.sprite.sfx;

import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class WhiteFlag extends AbstractSfxSprite {

	private static final float FRAMES_DURATION = 0.15f;
	
	private static final int SPRITESHEET_FRAMES = 4;

	public WhiteFlag(float x, float y) {
		super(x, y);			
	}

	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.WHITE_FLAG, 0, SPRITESHEET_FRAMES, FRAMES_DURATION);				
	}

	@Override
	public void addAppearAction() {		
		addAction(ActionFacade.createMoveAction(getX(), getY()+1, 2f));		
	}
		
}
