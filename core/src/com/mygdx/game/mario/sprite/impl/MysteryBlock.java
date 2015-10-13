package com.mygdx.game.mario.sprite.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.enums.BlockTypeEnum;

public class MysteryBlock extends Block {
		
	protected static final Map<Integer, Integer> REPLACING_TILES_UNDERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_OVERGROUND = new HashMap<Integer, Integer>();
	
	static {
		REPLACING_TILES_UNDERGROUND.put(7, 65);
		REPLACING_TILES_UNDERGROUND.put(8, 65);
		
		REPLACING_TILES_OVERGROUND.put(7, 5);
		REPLACING_TILES_OVERGROUND.put(8, 5);
	}
	
	public MysteryBlock(int x, int y, int tileId, String background) {		
		super(x,y, tileId);				
		visible = false;
		stateTime = 0f;		
		itemEnum = MAP_ITEMS.get(tileId);
		replacingTileValue = background.startsWith("overworld") ? REPLACING_TILES_OVERGROUND.get(tileId) : REPLACING_TILES_UNDERGROUND.get(tileId);
		setBlocType(BlockTypeEnum.MYSTERY_BLOCK);
	}	

	@Override
	public void initializeAnimations() {
		spriteSheet = new Texture(Gdx.files.internal("sprites/mystery.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);		
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];		
		currentAnimation = new Animation(0.15f, animationFrames);		
	}
	
}
