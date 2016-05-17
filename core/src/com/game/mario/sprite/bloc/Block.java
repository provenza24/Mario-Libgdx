package com.game.mario.sprite.bloc;

import java.util.HashMap;
import java.util.Map;

import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractSprite;

public abstract class Block extends AbstractSprite {
	
	protected static final Map<Integer, Integer> REPLACING_TILES_UNDERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_OVERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_BONUS = new HashMap<Integer, Integer>();
	
	protected static final Map<BackgroundTypeEnum, Map<Integer, Integer>> REPLACING_TILES_LIST = new HashMap<BackgroundTypeEnum, Map<Integer, Integer>>();				
	
	
	static {					
		REPLACING_TILES_UNDERGROUND.put(7, 65);
		REPLACING_TILES_UNDERGROUND.put(8, 65);
		REPLACING_TILES_UNDERGROUND.put(10, 65);
		
		REPLACING_TILES_OVERGROUND.put(7, 5);
		REPLACING_TILES_OVERGROUND.put(8, 5);
		REPLACING_TILES_OVERGROUND.put(10, 5);
		
		REPLACING_TILES_BONUS.put(7, 65);
		REPLACING_TILES_BONUS.put(8, 65);
		REPLACING_TILES_BONUS.put(10, 65);
		
		REPLACING_TILES_LIST.put(BackgroundTypeEnum.OVERWORLD, REPLACING_TILES_OVERGROUND);
		REPLACING_TILES_LIST.put(BackgroundTypeEnum.UNDERWORLD, REPLACING_TILES_UNDERGROUND);
		REPLACING_TILES_LIST.put(BackgroundTypeEnum.BONUS, REPLACING_TILES_BONUS);
	}
	
	protected BlockTypeEnum blocType;
	
	protected static final Map<Integer, ItemEnum> MAP_ITEMS = new HashMap<Integer, ItemEnum>();
			
	protected ItemEnum itemEnum;
	
	protected Integer replacingTileValue;
	
	protected int tileId;
	
	protected BackgroundTypeEnum backgroundTypeEnum;
	
	static {
		MAP_ITEMS.put(7, ItemEnum.COIN);
		MAP_ITEMS.put(8, ItemEnum.RED_MUSHROOM);		
		MAP_ITEMS.put(10, ItemEnum.COINS_10);
	}
	
	public Block(float x, float y, int tileId, BackgroundTypeEnum backgroundTypeEnum) {
		super(x, y);	
		itemEnum = MAP_ITEMS.get(tileId);				
		this.tileId = tileId;
		this.backgroundTypeEnum = backgroundTypeEnum;
		replacingTileValue = REPLACING_TILES_LIST.get(backgroundTypeEnum).get(tileId);
		collidableWithTilemap = false;
		moveable = false;
		gravitating = false;
		initializeAnimationsWithBackground();
	}
	
	public abstract void initializeAnimationsWithBackground();

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
