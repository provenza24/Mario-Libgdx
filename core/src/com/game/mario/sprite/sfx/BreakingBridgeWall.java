package com.game.mario.sprite.sfx;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class BreakingBridgeWall extends AbstractSfxSprite {
		
	public BreakingBridgeWall(float x, float y) {		
		super(x ,y);						
		setSize(spriteSheet.getWidth()/12/32, spriteSheet.getHeight()/32);
		renderingSize = new Vector2(spriteSheet.getWidth()/12/32, spriteSheet.getHeight()/32);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());				
	}

	@Override
	protected void updateAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, false);	
	}

	@Override
	public void initializeAnimations() {		
		spriteSheet = ResourcesLoader.BRIDGE_BREAKING_WALL;
		currentAnimation = AnimationBuilder.getInstance().build( ResourcesLoader.BRIDGE_BREAKING_WALL, 0, 12, 0.1f);																
	}

	@Override
	public void addAppearAction() {			
	}
	
}
