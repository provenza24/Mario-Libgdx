package com.mygdx.game.mario.tilemap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mygdx.game.mario.sprite.AbstractGameSprite;
import com.mygdx.game.mario.sprite.impl.Block;
import com.mygdx.game.mario.sprite.impl.Goomba;
import com.mygdx.game.mario.sprite.impl.Mario;
import com.mygdx.game.mario.sprite.impl.MysteryBlock;

public class TmxMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<Block> blocks;
	
	private List<AbstractGameSprite> enemies;
	
	private Mario mario;
	
	private static final Map<String, String> BACKGROUNDS = new HashMap<String, String>();
	
	private String background;
	
	static {
		BACKGROUNDS.put("overworld", "overworld.gif");
		BACKGROUNDS.put("underworld", "underworld.png");
	}
	
	public TmxMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		MapProperties properties = tileLayer.getProperties();
		background = BACKGROUNDS.get((String)properties.get("background"));				
		initBlocks(background);			
		initMapObjects();									
	}

	private void initMapObjects() {
		
		enemies = new ArrayList<AbstractGameSprite>();
		
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

	private void initBlocks(String background) {

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
	
	public void checkHorizontalMapCollision(AbstractGameSprite sprite) {

		sprite.reinitHorizontalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(sprite.getX() + sprite.getOffset().x, sprite.getY());
		Vector2 leftTopCorner = new Vector2(sprite.getX() + sprite.getOffset().x, sprite.getY() + 0.9f);
		Vector2 rightBottomCorner = new Vector2(sprite.getX() + 1 - sprite.getOffset().x, sprite.getY());
		Vector2 rightTopCorner = new Vector2(sprite.getX() + 1 - sprite.getOffset().x, sprite.getY() + 0.9f);

		boolean isCollision = isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		sprite.getMapCollisionEvent().setCollidingLeft(isCollision);

		isCollision = isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		sprite.getMapCollisionEvent().setCollidingLeft(sprite.getMapCollisionEvent().isCollidingLeft() || isCollision);

		isCollision = isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		sprite.getMapCollisionEvent().setCollidingRight(sprite.getMapCollisionEvent().isCollidingRight() || isCollision);

		isCollision = isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		sprite.getMapCollisionEvent().setCollidingRight(sprite.getMapCollisionEvent().isCollidingRight() || isCollision);

	}

	public void checkVerticalMapCollision(AbstractGameSprite sprite) {

		sprite.reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(sprite.getX() + 0.1f + sprite.getOffset().x, sprite.getY());
		Vector2 leftTopCorner = new Vector2(sprite.getX() + 0.1f + sprite.getOffset().x, sprite.getY() + 0.9f);
		Vector2 rightBottomCorner = new Vector2(sprite.getX() + 0.9f - sprite.getOffset().x, sprite.getY());
		Vector2 rightTopCorner = new Vector2(sprite.getX() + 0.9f - sprite.getOffset().x, sprite.getY() + 0.9f);

		boolean isCollision = isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		sprite.getMapCollisionEvent().setCollidingBottom(isCollision);

		int x = (int) leftTopCorner.x;
		int y = (int) leftTopCorner.y;
		isCollision = isCollisioningTileAt(x ,y);		
		sprite.getMapCollisionEvent().setCollidingTop(sprite.getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			sprite.getCollidingCells().add(new TmxCell(getTileAt(x, y), x ,y));
		}

		isCollision = isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		sprite.getMapCollisionEvent()
				.setCollidingBottom(sprite.getMapCollisionEvent().isCollidingBottom() || isCollision);
		
		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;
		isCollision = isCollisioningTileAt(x, y);
		sprite.getMapCollisionEvent().setCollidingTop(sprite.getMapCollisionEvent().isCollidingTop() || isCollision);
		if (isCollision) {
			sprite.getCollidingCells().add(new TmxCell(getTileAt(x, y), x ,y));
		}

	}
	
	public boolean isCollisioningTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			return cell.getTile().getId() < 128;
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

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	

	public List<AbstractGameSprite> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<AbstractGameSprite> enemies) {
		this.enemies = enemies;
	}

	public Mario getMario() {
		return mario;
	}

	public void setMario(Mario mario) {
		this.mario = mario;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
}
