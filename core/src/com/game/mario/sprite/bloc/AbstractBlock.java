package com.game.mario.sprite.bloc;

import java.util.HashMap;
import java.util.Map;

import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.util.constant.TileIdConstants;

public abstract class AbstractBlock extends AbstractSprite {
	
	protected static final Map<Integer, Integer> REPLACING_TILES_UNDERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_OVERGROUND = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_BONUS = new HashMap<Integer, Integer>();
	
	protected static final Map<Integer, Integer> REPLACING_TILES_CASTLE = new HashMap<Integer, Integer>();
	
	protected static final Map<WorldTypeEnum, Map<Integer, Integer>> REPLACING_TILES_LIST = new HashMap<WorldTypeEnum, Map<Integer, Integer>>();				
	
	protected static final Map<Integer, ItemEnum> MAP_ITEMS = new HashMap<Integer, ItemEnum>();
			
	protected ItemEnum itemEnum;
	
	protected Integer replacingTileValue;
	
	protected int tileId;
	
	protected WorldTypeEnum worldType;
		
	static {	
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_COIN, TileIdConstants.BLOCK_OVERGROUND);		
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_GREEN_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);	
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_RED_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_GREEN_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.WALL_OVERGROUND_10_COINS, TileIdConstants.BLOCK_OVERGROUND);		
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.WALL_OVERGROUND_RED_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.WALL_OVERGROUND_GREEN_MUSHROOM, TileIdConstants.BLOCK_OVERGROUND);
		REPLACING_TILES_OVERGROUND.put(TileIdConstants.WALL_OVERGROUND_STAR, TileIdConstants.BLOCK_OVERGROUND);
		
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_COIN, TileIdConstants.BLOCK_UNDERGROUND);		
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.WALL_UNDERGROUND_10_COINS, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.WALL_UNDERGROUND_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.WALL_UNDERGROUND_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_UNDERGROUND.put(TileIdConstants.WALL_UNDERGROUND_STAR, TileIdConstants.BLOCK_UNDERGROUND);
				
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_COIN, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);		
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.WALL_UNDERGROUND_10_COINS, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.WALL_UNDERGROUND_RED_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		REPLACING_TILES_BONUS.put(TileIdConstants.WALL_UNDERGROUND_GREEN_MUSHROOM, TileIdConstants.BLOCK_UNDERGROUND);
		
		REPLACING_TILES_CASTLE.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, TileIdConstants.BLOCK_CASTLE);
		REPLACING_TILES_CASTLE.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, TileIdConstants.BLOCK_CASTLE);
		REPLACING_TILES_CASTLE.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, TileIdConstants.BLOCK_CASTLE);
		REPLACING_TILES_CASTLE.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_RED_MUSHROOM, TileIdConstants.BLOCK_CASTLE);
		REPLACING_TILES_CASTLE.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_GREEN_MUSHROOM, TileIdConstants.BLOCK_CASTLE);
		
		REPLACING_TILES_LIST.put(WorldTypeEnum.OVERGROUND, REPLACING_TILES_OVERGROUND);
		REPLACING_TILES_LIST.put(WorldTypeEnum.UNDERGROUND, REPLACING_TILES_UNDERGROUND);
		REPLACING_TILES_LIST.put(WorldTypeEnum.BONUS, REPLACING_TILES_BONUS);
		REPLACING_TILES_LIST.put(WorldTypeEnum.CASTLE, REPLACING_TILES_CASTLE);
	}
	
	static {		
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_COIN, ItemEnum.COIN);
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, ItemEnum.RED_MUSHROOM);
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_GREEN_MUSHROOM, ItemEnum.GREEN_MUSHROOM);
		
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_COIN, ItemEnum.COIN);
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_RED_MUSHROOM, ItemEnum.RED_MUSHROOM);
		MAP_ITEMS.put(TileIdConstants.MYSTERY_BLOCK_INVIBLE_GREEN_MUSHROOM, ItemEnum.GREEN_MUSHROOM);
		
		MAP_ITEMS.put(TileIdConstants.WALL_OVERGROUND_10_COINS, ItemEnum.COINS_10);
		MAP_ITEMS.put(TileIdConstants.WALL_OVERGROUND_RED_MUSHROOM, ItemEnum.RED_MUSHROOM);
		MAP_ITEMS.put(TileIdConstants.WALL_OVERGROUND_GREEN_MUSHROOM, ItemEnum.GREEN_MUSHROOM);
		MAP_ITEMS.put(TileIdConstants.WALL_OVERGROUND_STAR, ItemEnum.STAR);
		
		MAP_ITEMS.put(TileIdConstants.WALL_UNDERGROUND_10_COINS, ItemEnum.COINS_10);
		MAP_ITEMS.put(TileIdConstants.WALL_UNDERGROUND_RED_MUSHROOM, ItemEnum.RED_MUSHROOM);
		MAP_ITEMS.put(TileIdConstants.WALL_UNDERGROUND_GREEN_MUSHROOM, ItemEnum.GREEN_MUSHROOM);		
		MAP_ITEMS.put(TileIdConstants.WALL_UNDERGROUND_STAR, ItemEnum.STAR);
	}
			
	public AbstractBlock(float x, float y, int tileId, WorldTypeEnum worldType) {
		super(x, y);	
		this.itemEnum = MAP_ITEMS.get(tileId);				
		this.tileId = tileId;
		this.worldType = worldType;
		this.replacingTileValue = REPLACING_TILES_LIST.get(worldType).get(tileId);		
		this.initializeAnimationsWithBackground();
	}
	
	public abstract void initializeAnimationsWithBackground();
	
	public void changeFrame() {		
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

	public abstract BlockTypeEnum getBlocType();
		
	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}

}
