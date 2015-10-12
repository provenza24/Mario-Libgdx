package com.mygdx.game.mario.sprite.impl;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.mario.enums.BlockTypeEnum;
import com.mygdx.game.mario.enums.ItemEnum;

public abstract class Block extends Actor {
	
	protected BlockTypeEnum blocType;
	
	protected static final Map<Integer, ItemEnum> MAP_ITEMS = new HashMap<Integer, ItemEnum>();
	
	protected Texture spriteSheet;
			
	protected Animation animation; 
	
	protected TextureRegion currentFrame;		
		
	protected float stateTime;
		
	protected boolean visible;
	
	protected ItemEnum itemEnum;
	
	protected int replacingTileValue;
	
	protected int tileId;
	
	static {
		MAP_ITEMS.put(7, ItemEnum.COIN);
		MAP_ITEMS.put(8, ItemEnum.RED_MUSHROOM);
	}

	public abstract void updateAnimation(float delta);
	
	public Texture getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(Texture spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public ItemEnum getItemEnum() {
		return itemEnum;
	}

	public void setItemEnum(ItemEnum itemEnum) {
		this.itemEnum = itemEnum;
	}

	public int getReplacingTileValue() {
		return replacingTileValue;
	}

	public void setReplacingTileValue(int replacingTileValue) {
		this.replacingTileValue = replacingTileValue;
	}

	public BlockTypeEnum getBlocType() {
		return blocType;
	}

	public void setBlocType(BlockTypeEnum blocType) {
		this.blocType = blocType;
	}

	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}

}
