package com.mygdx.game.mario.sprite.bloc;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.mario.enums.BlockTypeEnum;
import com.mygdx.game.mario.enums.ItemEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;

public abstract class Block extends AbstractSprite {
	
	protected BlockTypeEnum blocType;
	
	protected static final Map<Integer, ItemEnum> MAP_ITEMS = new HashMap<Integer, ItemEnum>();
			
	protected ItemEnum itemEnum;
	
	protected int replacingTileValue;
	
	protected int tileId;
	
	static {
		MAP_ITEMS.put(7, ItemEnum.COIN);
		MAP_ITEMS.put(8, ItemEnum.RED_MUSHROOM);
	}
	
	public Block(float x, float y, int tileId) {
		super(x, y);	
		this.tileId = tileId;
		collidableWithTilemap = false;
		moveable = false;
		gravitating = false;
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
