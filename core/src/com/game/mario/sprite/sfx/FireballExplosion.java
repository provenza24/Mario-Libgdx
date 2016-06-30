package com.game.mario.sprite.sfx;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class FireballExplosion extends AbstractSfxSprite {
	
	private static final int SPRITESHEET_FRAMES = 3;	
	private static final float FRAME_DURATION = 0.05f;
	
	public FireballExplosion(AbstractSprite fireball) {		
		super(fireball.getX(), fireball.getY());					
	}

	@Override
	public void initializeAnimations() {
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FIREBALL_EXPLOSION, 0, SPRITESHEET_FRAMES, FRAME_DURATION);						
	}

	@Override
	public void addAppearAction() {
		SequenceAction actions = new SequenceAction(				
				ActionFacade.createMoveAction(getX(), getY(), 0.15f),
				new DeleteItemAction(this));
		addAction(actions);		
	}

}
