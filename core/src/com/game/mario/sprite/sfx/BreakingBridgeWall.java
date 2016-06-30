package com.game.mario.sprite.sfx;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class BreakingBridgeWall extends AbstractSfxSprite {
		
	private static final float WIDHT = 1;	
	private static final float HEIGHT = 3.5f;			
	private static final int   SPRITESHEET_FRAMES = 12;	
	private static final float FRAME_DURATION = 0.1f;
	
	public BreakingBridgeWall(float x, float y) {		
		super(x, y, new Vector2(WIDHT, HEIGHT), new Vector2());									
		isAnimationLooping = false;
	}
	
	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.BRIDGE_BREAKING_WALL;
		currentAnimation = AnimationBuilder.getInstance().build( ResourcesLoader.BRIDGE_BREAKING_WALL, 0, SPRITESHEET_FRAMES, FRAME_DURATION);																
	}

	@Override
	public void addAppearAction() {			
	}
	
}
