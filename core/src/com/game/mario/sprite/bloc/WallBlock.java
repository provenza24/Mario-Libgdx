package com.game.mario.sprite.bloc;

import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class WallBlock extends AbstractBlock {

	private int coins;
	
	public WallBlock(int x, int y, int tileId, WorldTypeEnum backgroundType) {		
		super(x,y, tileId, backgroundType);													
		coins = itemEnum==ItemEnum.COINS_10 ? 10 :0;								 	
	}

	@Override
	public void initializeAnimations() {
		
	}
	
	@Override
	public void initializeAnimationsWithBackground() {
		spriteSheet = ResourcesLoader.WALL_TEXTURES.get(worldType);				
		currentAnimation = AnimationBuilder.getInstance().build(spriteSheet, 0, 1, 0);		
	}
	
	public void removeCoin() {
		coins--;
	}

	public int getCoins() {
		return coins;
	}

	@Override
	public BlockTypeEnum getBlocType() {		
		return BlockTypeEnum.WALL_BLOCK;
	}
}
