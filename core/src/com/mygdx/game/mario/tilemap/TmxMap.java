package com.mygdx.game.mario.tilemap;

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
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.enums.BackgroundTypeEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.sprite.bloc.MysteryBlock;
import com.mygdx.game.mario.sprite.tileobject.enemy.Goomba;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public class TmxMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<Block> blocks;
	
	private List<AbstractSprite> enemies;
	
	private List<AbstractSprite> items;
	
	private Mario mario;
		
	private BackgroundTypeEnum backgroundType;
	
	public TmxMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		MapProperties properties = tileLayer.getProperties();				
		backgroundType = BackgroundTypeEnum.valueOf(((String)properties.get("background")).toUpperCase());				
		initBlocks(backgroundType);			
		initMapObjects();
		items = new ArrayList<AbstractSprite>();
	}

	private void initMapObjects() {
		
		enemies = new ArrayList<AbstractSprite>();
		
		MapObjects objects = objectsLayer.getObjects();
		for (MapObject mapObject : objects) {
			MapProperties objectProperty = mapObject.getProperties();		
			//Gdx.app.log("New object from layer", objectProperty.get("type").toString());
			if (objectProperty.get("type").toString().equals("mario")) {
				mario = new Mario(mapObject);
			}
			if (objectProperty.get("type").toString().equals("goomba")) {				
				enemies.add(new Goomba(mapObject));
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

	

	public List<AbstractSprite> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<AbstractSprite> enemies) {
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
}
