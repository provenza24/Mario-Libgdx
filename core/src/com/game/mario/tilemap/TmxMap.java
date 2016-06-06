package com.game.mario.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.BlockTypeEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.InvisibleMysteryBlock;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.bloc.WallBlock;
import com.game.mario.sprite.tileobject.enemy.AbstractEnemy;
import com.game.mario.sprite.tileobject.enemy.Bowser;
import com.game.mario.sprite.tileobject.enemy.CastleFirebar;
import com.game.mario.sprite.tileobject.enemy.Goomba;
import com.game.mario.sprite.tileobject.enemy.Koopa;
import com.game.mario.sprite.tileobject.enemy.PiranhaPlant;
import com.game.mario.sprite.tileobject.enemy.RedKoopa;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.item.Flag;
import com.game.mario.sprite.tileobject.item.TransferItemDown;
import com.game.mario.sprite.tileobject.item.TransferItemRight;
import com.game.mario.sprite.tileobject.item.plateform.AscendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.DescendingMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.TileIdConstants;
import com.game.mario.util.TilemapPropertiesConstants;

public class TmxMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<Block> blocks;
	
	private List<WallBlock> wallBlocks;
	
	private List<AbstractEnemy> enemies;
	
	private List<AbstractSprite> items;
	
	private Mario mario;
		
	private WorldTypeEnum worldType;
	
	private Flag flag;
	
	private String musicTheme;
	
	private Array<BackgroundTypeEnum> backgroundTypesEnum;
	
	private float scrollMaxValue;
			
	public TmxMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		MapProperties properties = tileLayer.getProperties();				
		worldType = WorldTypeEnum.valueOf(((String)properties.get(TilemapPropertiesConstants.WORLD)).toUpperCase());		
		musicTheme = ((String)properties.get("music")).toUpperCase();
		String sScrollableTo = (String)properties.get("scrollableTo");
		scrollMaxValue = sScrollableTo!=null && !sScrollableTo.equals("") ? Float.parseFloat(sScrollableTo) : 1000;
 		initBlocks(worldType);		
		initMapObjects();		
		initBackgrounds(properties);
	}
	
	private void initBackgrounds(MapProperties properties) {
				
		backgroundTypesEnum = new Array<BackgroundTypeEnum>();
		
		String backgrounds[] = ((String)properties.get(TilemapPropertiesConstants.BACKGROUNDS)).toUpperCase().split(",");
		for (String background : backgrounds) {
			backgroundTypesEnum.add(BackgroundTypeEnum.valueOf(background.toUpperCase()));
		}
	}

	private void initMapObjects() {
		
		items = new ArrayList<AbstractSprite>();
		enemies = new ArrayList<AbstractEnemy>();
		
		MapObjects objects = objectsLayer.getObjects();
		for (MapObject mapObject : objects) {
			MapProperties objectProperty = mapObject.getProperties();		
								
			if (objectProperty.get("type").toString().equals("mario")) {
				mario = new Mario(mapObject);
			}
			if (objectProperty.get("type").toString().equals("piranha")) {				
				enemies.add(new PiranhaPlant(mapObject, mario));
			}
			if (objectProperty.get("type").toString().equals("goomba")) {				
				enemies.add(new Goomba(mapObject, worldType));
			}
			if (objectProperty.get("type").toString().equals("koopa")) {				
				enemies.add(new Koopa(mapObject));
			}			
			if (objectProperty.get("type").toString().equals("redKoopa")) {				
				enemies.add(new RedKoopa(mapObject));
			}
			if (objectProperty.get("type").toString().equals("castleFirebar")) {
				enemies.add(new CastleFirebar(mapObject));		
			}
			if (objectProperty.get("type").toString().equals("bowser")) {				
				enemies.add(new Bowser(mapObject));
			}
			if (objectProperty.get("type").toString().equals("transferDown")) {				
				items.add(new TransferItemDown(mapObject));
			}
			if (objectProperty.get("type").toString().equals("transferRight")) {				
				items.add(new TransferItemRight(mapObject));
			}
			if (objectProperty.get("type").toString().equals("coin")) {				
				items.add(new Coin(mapObject));
			}
			if (objectProperty.get("type").toString().equals("flag")) {
				flag = new Flag(mapObject, worldType);
				items.add(flag);
			}
			
			if (objectProperty.get("type").toString().equals("metalPlateform")) {
				if (objectProperty.get("mode").toString().equals("ascending")) {
					items.add(new AscendingMetalPlateform(mapObject));		
				} else if (objectProperty.get("mode").toString().equals("descending")) {
					items.add(new DescendingMetalPlateform(mapObject));		
				}				
				
			}
						
		}
	}

	private void initBlocks(WorldTypeEnum background) {
		
		blocks = new ArrayList<Block>();
		wallBlocks = new ArrayList<WallBlock>();

		for (int i = 0; i < tileLayer.getWidth(); i++) {
			for (int j = 0; j < tileLayer.getHeight(); j++) {
				Cell cell = tileLayer.getCell(i, j);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					int id = tile.getId();
					
					BlockTypeEnum blockTypeEnum = TileIdConstants.getSpecialBlockType(id);
					if (blockTypeEnum==BlockTypeEnum.MYSTERY_BLOCK) {
						blocks.add(new MysteryBlock(i, j, id, background));
					} else if (blockTypeEnum==BlockTypeEnum.WALL_BLOCK) {
						wallBlocks.add(new WallBlock(i, j, id, background));
					} else if (blockTypeEnum==BlockTypeEnum.MYSTERY_BLOCK_INVISIBLE) {
						blocks.add(new InvisibleMysteryBlock(i, j, id, background));
					} 					
				}
			}
		}
	}
	
	public Block getBlockAt(int x, int y) {
		for (Block block : blocks) {
			if (block.getX()==x && block.getY()==y) {
				return block;
			}
		}
		return null;
	}
	
	public boolean isCollisioningTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			return cell.getTile().getId() <= 128 && !TileIdConstants.isInvisibleBlock(cell.getTile().getId());
		}		
		return false;
	}
	
	public boolean isCollisioningInvisibleTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			return TileIdConstants.isInvisibleBlock(cell.getTile().getId());
		}		
		return false;
	}
	
	public Cell getTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);		
		return cell;
	}
	
	public void changeCellValue(int x, int y, int value) {
		Cell cell = tileLayer.getCell(x, y);		
		cell.setTile(map.getTileSets().getTile(value));
	}
	
	public void removeCell(int x, int y) {		
		tileLayer.setCell(x, y, null);
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	

	public List<AbstractEnemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<AbstractEnemy> enemies) {
		this.enemies = enemies;
	}

	public Mario getMario() {
		return mario;
	}

	public void setMario(Mario mario) {
		this.mario = mario;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	

	public List<AbstractSprite> getItems() {
		return items;
	}

	public void setItems(List<AbstractSprite> items) {
		this.items = items;
	}

	public float getFlagTargetPosition() {
		return flag.getFlagTargetPosition();
	}
	
	public Flag getFlag() {
		return flag;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
	public void dispose() {	
		map.dispose();							
	}

	public String getMusicTheme() {
		return musicTheme;
	}

	public void setMusicTheme(String musicTheme) {
		this.musicTheme = musicTheme;
	}

	public List<WallBlock> getWallBlocks() {
		return wallBlocks;
	}

	public void setWallBlocks(List<WallBlock> wallBlocks) {
		this.wallBlocks = wallBlocks;
	}

	public WorldTypeEnum getWorldType() {
		return worldType;
	}

	public void setWorldType(WorldTypeEnum worldType) {
		this.worldType = worldType;
	}

	public Array<BackgroundTypeEnum> getBackgroundTypesEnum() {
		return backgroundTypesEnum;
	}

	public void setBackgroundTypesEnum(Array<BackgroundTypeEnum> backgroundsTypesEnum) {
		this.backgroundTypesEnum = backgroundsTypesEnum;
	}

	public float getScrollMaxValue() {
		return scrollMaxValue;
	}

	public void setScrollMaxValue(float scrollMaxValue) {
		this.scrollMaxValue = scrollMaxValue;
	}

}
