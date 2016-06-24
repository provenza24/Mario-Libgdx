package com.game.mario.sprite.bloc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.util.ResourcesLoader;

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
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);		
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
