package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.util.ResourcesLoader;

public class GreenMushroom extends AbstractItem {

	public GreenMushroom(float x, float y) {
		super(x, y);
		offset.x = 0.2f;		
		setSize(1 - 2*offset.x, 1f - offset.y);
		acceleration.x = 3f;		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.GREEN_MUSHROOM;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);
	}

	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));
	}
		
}