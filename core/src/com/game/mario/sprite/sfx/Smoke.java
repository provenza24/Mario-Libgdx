package com.game.mario.sprite.sfx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Smoke extends AbstractSfxSprite {

	private static final float FRAMES_DURATION = 0.1f;
	
	private static final int SPRITESHEET_FRAMES = 3;

	public Smoke(Mario mario) {
		super(mario.getDirection()==DirectionEnum.RIGHT ? mario.getX() + mario.getWidth() : mario.getX()-0.5f, mario.getY(),
				new Vector2(0.5f, 0.5f), new Vector2());
		isAnimationLooping = false;			
		move.x = mario.getDirection()==DirectionEnum.RIGHT ? mario.getAcceleration().x /8 : - mario.getAcceleration().x / 8;	
	}

	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.SMOKE, 0, SPRITESHEET_FRAMES, FRAMES_DURATION);				
	}

	@Override
	public void addAppearAction() {		
		SequenceAction actions = new SequenceAction(
				ActionFacade.createMoveAction(getX() + move.x, getY(), 0.3f),				
				new DeleteItemAction(this));
		addAction(actions);
	}
		
}
