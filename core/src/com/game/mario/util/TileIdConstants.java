package com.game.mario.util;

import java.util.HashMap;
import java.util.Map;

import com.game.mario.enums.BlockTypeEnum;

public class TileIdConstants {
	
	public static final Map<Integer, BlockTypeEnum> SPECIAL_BLOCK_TYPES = new HashMap<Integer, BlockTypeEnum>();
	
	public static final int INVISIBLE_BLOCK = 20;
	
	public static final int MYSTERY_BLOCK_COIN = 7;	
	public static final int MYSTERY_BLOCK_RED_MUSHROOM = 8;
	public static final int MYSTERY_BLOCK_GREEN_MUSHROOM = 9;
	
	public static final int WALL_OVERGROUND = 4;
	public static final int BLOCK_OVERGROUND = 5;
	
	public static final int WALL_UNDERGROUND = 64;	
	public static final int BLOCK_UNDERGROUND = 65;
	
	public static final int BLOCK_CASTLE = 105;
	
	public static final int WALL_OVERGROUND_10_COINS = 11;
	public static final int WALL_OVERGROUND_RED_MUSHROOM = 12;
	public static final int WALL_OVERGROUND_GREEN_MUSHROOM = 13;
	
	public static final int MYSTERY_BLOCK_INVIBLE_COIN = 14;
	
	public static final int WALL_UNDERGROUND_10_COINS = 71;
	public static final int WALL_UNDERGROUND_RED_MUSHROOM = 72;
	public static final int WALL_UNDERGROUND_GREEN_MUSHROOM = 73;
		
	static {
		SPECIAL_BLOCK_TYPES.put(MYSTERY_BLOCK_COIN, BlockTypeEnum.MYSTERY_BLOCK );
		SPECIAL_BLOCK_TYPES.put(MYSTERY_BLOCK_RED_MUSHROOM, BlockTypeEnum.MYSTERY_BLOCK );
		SPECIAL_BLOCK_TYPES.put(MYSTERY_BLOCK_GREEN_MUSHROOM, BlockTypeEnum.MYSTERY_BLOCK );
		
		SPECIAL_BLOCK_TYPES.put(WALL_OVERGROUND_10_COINS, BlockTypeEnum.WALL_BLOCK );		
		SPECIAL_BLOCK_TYPES.put(WALL_OVERGROUND_RED_MUSHROOM, BlockTypeEnum.WALL_BLOCK );
		SPECIAL_BLOCK_TYPES.put(WALL_OVERGROUND_GREEN_MUSHROOM, BlockTypeEnum.WALL_BLOCK );
		
		SPECIAL_BLOCK_TYPES.put(WALL_UNDERGROUND_10_COINS, BlockTypeEnum.WALL_BLOCK );
		SPECIAL_BLOCK_TYPES.put(WALL_UNDERGROUND_RED_MUSHROOM, BlockTypeEnum.WALL_BLOCK );
		SPECIAL_BLOCK_TYPES.put(WALL_UNDERGROUND_GREEN_MUSHROOM, BlockTypeEnum.WALL_BLOCK );
	}
	
	public static BlockTypeEnum getSpecialBlockType(Integer tileId) {
		return SPECIAL_BLOCK_TYPES.get(tileId);
	}

}
