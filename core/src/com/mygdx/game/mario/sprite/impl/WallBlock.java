package com.mygdx.game.mario.sprite.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.enums.BlockTypeEnum;

public class WallBlock extends Block {

	public WallBlock(int x, int y, int tileId) {
		
		this.tileId = tileId;
		setPosition(x, y);

		spriteSheet = new Texture(Gdx.files.internal("sprites/wall-"+tileId+".png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		animation = new Animation(1, animationFrames);
				
		visible = false;
		stateTime = 0f;		
		//itemEnum = MAP_ITEMS.get(tileId);
		replacingTileValue = 5;
		setBlocType(BlockTypeEnum.WALL_BLOCK);
	}
	
	public void updateAnimation(float delta) {
		currentFrame = animation.getKeyFrame(stateTime, true);
	}
	
}