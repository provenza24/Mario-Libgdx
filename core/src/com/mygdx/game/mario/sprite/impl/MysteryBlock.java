package com.mygdx.game.mario.sprite.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.enums.BlockTypeEnum;

public class MysteryBlock extends Block {
		
	public MysteryBlock(int x, int y, int tileId) {
							
		this.tileId = tileId;
		setPosition(x, y);

		spriteSheet = new Texture(Gdx.files.internal("sprites/mystery.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);		
		TextureRegion[] animationFrames = new TextureRegion[3];
		animationFrames[0] = tmp[0][0];
		animationFrames[1] = tmp[0][1];
		animationFrames[2] = tmp[0][2];		
		animation = new Animation(0.15f, animationFrames);
				
		visible = false;
		stateTime = 0f;		
		itemEnum = MAP_ITEMS.get(tileId);
		replacingTileValue = 5;
		setBlocType(BlockTypeEnum.MYSTERY_BLOCK);
	}
	
	public void updateAnimation(float delta) {
		stateTime = stateTime + delta;
    	currentFrame = animation.getKeyFrame(stateTime, true);    	
	}
	
}
