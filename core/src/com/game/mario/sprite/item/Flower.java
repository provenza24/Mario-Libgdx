package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.ResourcesLoader;
import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractItem;

public class Flower extends AbstractItem {

	public Flower(float x, float y) {
		super(x, y);				
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.FLOWER;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[4];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];
		animationFrames[3] = tmp[0][3];
		currentAnimation = new Animation(0.01f, animationFrames);
	}

	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));
	}
		
}
