package com.game.mario.sprite.sfx;

import com.badlogic.gdx.math.MathUtils;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Firework extends AbstractSfxSprite {

	private static final float FRAMES_DURATION = 0.1f;
	
	private static final int SPRITESHEET_FRAMES = 5;

	public Firework(float x, float y) {
		super(x, y);
		isAnimationLooping = false;
	}

	@Override
	public void initializeAnimations() {
		int fireworkNum = MathUtils.random(3);
		currentAnimation = AnimationBuilder.getInstance().build(fireworkNum==0 ? ResourcesLoader.FIREWORK_RED 
				: fireworkNum==1 ? ResourcesLoader.FIREWORK_YELLOW :ResourcesLoader.FIREWORK_PURPLE
						, 0, SPRITESHEET_FRAMES, FRAMES_DURATION);				
	}

	@Override
	public void addAppearAction() {				
	}
		
}
