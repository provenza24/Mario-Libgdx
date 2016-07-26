package com.game.mario.sprite.sfx;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class EjectedCoin extends AbstractSfxSprite {

	private static final int SPRITESHEET_FRAMES = 9;	
	
	private static final float FRAME_DURATION = 0.45f / SPRITESHEET_FRAMES;
	
	public EjectedCoin(float x, float y) {
		super(x, y);				
		isAnimationLooping = false;
	}

	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.COIN_BLOC, 0, SPRITESHEET_FRAMES, FRAME_DURATION);				
	}

	@Override
	public void addAppearAction() {
		SequenceAction actions = new SequenceAction(
				ActionFacade.createMoveAction(getX(), getY()+2, 0.2f),
				ActionFacade.createMoveAction(getX(), getY()+2, 0.25f),
				new DeleteItemAction(this));
		addAction(actions);
		
	}
		
}
