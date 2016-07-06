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

	public Smoke(Mario mario) {
		super(mario.getDirection()==DirectionEnum.RIGHT ? mario.getX() + mario.getWidth() : mario.getX()-0.5f, mario.getY(),
				new Vector2(0.5f, 0.5f), new Vector2());
		isAnimationLooping = false;		
	}

	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.SMOKE, 0, 3, 0.1f);				
	}

	@Override
	public void addAppearAction() {		
		SequenceAction actions = new SequenceAction(
				ActionFacade.createMoveAction(getX(), getY(), 0.3f),				
				new DeleteItemAction(this));
		addAction(actions);
	}
		
}
