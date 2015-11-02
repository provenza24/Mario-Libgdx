package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.ResourcesLoader;
import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractItem;

public class Mushroom extends AbstractItem {

	public Mushroom(float x, float y) {
		super(x, y);		
		acceleration.x = -2.5f;		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.MUSHROOM;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);
	}

	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+1, 0.5f));		
	}
		
}
