package com.game.mario.sprite.bloc;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.util.ResourcesLoader;

public class InvisibleMysteryBlock extends AbstractBlock {
	
	private static final Map<WorldTypeEnum, Integer> BLOCK_VISIBLE_FRAMES = new HashMap<WorldTypeEnum, Integer>();
	
	static {
		BLOCK_VISIBLE_FRAMES.put(WorldTypeEnum.OVERGROUND, 1);
		BLOCK_VISIBLE_FRAMES.put(WorldTypeEnum.UNDERGROUND, 2);
		BLOCK_VISIBLE_FRAMES.put(WorldTypeEnum.BONUS, 2);
		BLOCK_VISIBLE_FRAMES.put(WorldTypeEnum.CASTLE, 3);
	}
	
	public InvisibleMysteryBlock(int x, int y, int tileId, WorldTypeEnum worldType) {		
		super(x,y, tileId, worldType);											
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.MYSTERY_BLOC_INVISIBLE;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[4];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];
		animationFrames[3] = tmp[0][3];
		currentAnimation = new Animation(1, animationFrames);		
	}
		
	@Override
	public void initializeAnimationsWithBackground() {		
	}
	
	@Override
	public BlockTypeEnum getBlocType() {
		return BlockTypeEnum.MYSTERY_BLOCK_INVISIBLE;
	}
			
	protected void updateAnimation(float delta) {
		stateTime = stateTime + delta;
		currentFrame = currentAnimation.getKeyFrame(frame, false);				
	}
	
	public void changeFrame() {
		frame = BLOCK_VISIBLE_FRAMES.get(worldType);
	}
	
	
}
