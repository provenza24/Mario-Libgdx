package com.game.mario.sprite.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.util.ResourcesLoader;

public class GreenMushroom extends AbstractMushroom {

	public GreenMushroom(float x, float y) {
		super(x, y);
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.GREEN_MUSHROOM;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);
	}

}
