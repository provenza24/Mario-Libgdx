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
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.tileobject.enemy.AbstractEnemy;
import com.game.mario.sprite.tileobject.enemy.Goomba;
import com.game.mario.sprite.tileobject.enemy.Koopa;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.item.Flag;
import com.game.mario.sprite.tileobject.item.TransferItemDown;
import com.game.mario.sprite.tileobject.item.TransferItemRight;
import com.game.mario.sprite.tileobject.mario.Mario;

public class TmxMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<Block> blocks;
	
	private List<AbstractEnemy> enemies;
	
	private List<AbstractSprite> items;
	
	private Mario mario;
		
	private BackgroundTypeEnum backgroundType;
	
	private Flag flag;
	
	private String musicTheme;
	
	public TmxMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		MapProperties properties = tileLayer.getProperties();				
		backgroundType = BackgroundTypeEnum.valueOf(((String)properties.get("background")).toUpperCase());
		musicTheme = ((String)properties.get("music")).toUpperCase();		
		initBlocks(backgroundType);
		items = new ArrayList<AbstractSprite>();
		initMapObjects();		
	}

	private void initMapObjects() {
		
		enemies = new ArrayList<AbstractEnemy>();
		
		MapObjects objects = objectsLayer.getObjects();
		for (MapObject mapObject : objects) {
			MapProperties objectProperty = mapObject.getProperties();		
								
			if (objectProperty.get("type").toString().equals("mario")) {
				mario = new Mario(mapObject);
			}
			if (objectProperty.get("type").toString().equals("goomba")) {				
				enemies.add(new Goomba(mapObject));
			}
			if (objectProperty.get("type").toString().equals("koopa")) {				
				enemies.add(new Koopa(mapObject));
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
				flag = new Flag(mapObject);
				items.add(flag);
			}
		}
	}

	private void initBlocks(BackgroundTypeEnum background) {

		blocks = new ArrayList<Block>();

		for (int i = 0; i < tileLayer.getWidth(); i++) {
			for (int j = 0; j < tileLayer.getHeight(); j++) {
				Cell cell = tileLayer.getCell(i, j);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					int id = tile.getId();
					if (id == 7 || id == 8) {
						blocks.add(new MysteryBlock(i, j, id, background));
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
			return cell.getTile().getId() <= 128;
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

	public BackgroundTypeEnum getBackgroundType() {
		return backgroundType;
	}

	public void setBackgroundType(BackgroundTypeEnum backgroundType) {
		this.backgroundType = backgroundType;
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
}
