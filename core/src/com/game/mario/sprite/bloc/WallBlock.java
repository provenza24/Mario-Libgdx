package com.game.mario.sprite.bloc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.mario.enums.BlockTypeEnum;

public class WallBlock extends Block {

	int coins;
	
	public WallBlock(int x, int y, int tileId) {
		
		super(x,y, tileId);							
		//itemEnum = MAP_ITEMS.get(tileId);
		replacingTileValue = 5;
		setBlocType(BlockTypeEnum.WALL_BLOCK);
		
		spriteSheet = new Texture(Gdx.files.internal("sprites/wall-"+tileId+".png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);
		
		coins = tileId == 10 ? 1 : 0; 
		
		replacingTileValue = 5;
	}

	@Override
	public void initializeAnimations() {		
	}

	public void removeCoin() {
		coins--;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
	
}
