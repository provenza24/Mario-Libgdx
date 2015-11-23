package com.game.mario.sprite.bloc;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.util.ResourcesLoader;

public class MysteryBlock extends Block {
		
	protected static final Map<Integer, Integer> REPLACING_TILES_UNDERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_OVERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<BackgroundTypeEnum, Map<Integer, Integer>> REPLACING_TILES_LIST = new HashMap<BackgroundTypeEnum, Map<Integer, Integer>>();				
		
	protected static float blocStateTime;
	
	static {					
		REPLACING_TILES_UNDERGROUND.put(7, 65);
		REPLACING_TILES_UNDERGROUND.put(8, 65);
		
		REPLACING_TILES_OVERGROUND.put(7, 5);
		REPLACING_TILES_OVERGROUND.put(8, 5);
		
		REPLACING_TILES_LIST.put(BackgroundTypeEnum.OVERWORLD, REPLACING_TILES_OVERGROUND);
		REPLACING_TILES_LIST.put(BackgroundTypeEnum.UNDERWORLD, REPLACING_TILES_UNDERGROUND);
	}
	
	public MysteryBlock(int x, int y, int tileId, BackgroundTypeEnum backgroundType) {		
		super(x,y, tileId);						
		itemEnum = MAP_ITEMS.get(tileId);
		replacingTileValue = REPLACING_TILES_LIST.get(backgroundType).get(tileId);		
		setBlocType(BlockTypeEnum.MYSTERY_BLOCK);
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.MYSTERY_BLOC;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);		
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];		
		currentAnimation = new Animation(0.15f, animationFrames);
	}
	
	public static void updateStateTime(float delta) {		
		blocStateTime = blocStateTime + delta;
	}
	
	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(blocStateTime, true);		
	}
	
}
