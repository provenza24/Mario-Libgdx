package com.mygdx.game.mario.tilemap;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.mario.sprite.impl.Goomba;
import com.mygdx.game.mario.sprite.impl.Mario;
import com.mygdx.game.mario.sprite.impl.MysteryBlock;

public class TmxMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<MysteryBlock> mysteryBlocks;
	
	private List<AbstractGameSprite> enemies;
	
	private Mario mario;
	
	public TmxMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		initMysteryBlocks();			
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

	private void initMysteryBlocks() {

		mysteryBlocks = new ArrayList<MysteryBlock>();

		for (int i = 0; i < tileLayer.getWidth(); i++) {
			for (int j = 0; j < tileLayer.getHeight(); j++) {
				Cell cell = tileLayer.getCell(i, j);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					int id = tile.getId();
					if (id == 7 || id == 8) {
						mysteryBlocks.add(new MysteryBlock(i, j, id));
					}
				}
			}
		}
	}
	
	public void checkHorizontalMapCollision(AbstractGameSprite sprite) {

		sprite.reinitHorizontalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(sprite.getX(), sprite.getY());
		Vector2 leftTopCorner = new Vector2(sprite.getX(), sprite.getY() + 0.9f);
		Vector2 rightBottomCorner = new Vector2(sprite.getX() + 1, sprite.getY());
		Vector2 rightTopCorner = new Vector2(sprite.getX() + 1, sprite.getY() + 0.9f);

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

		Vector2 leftBottomCorner = new Vector2(sprite.getX() + 0.1f, sprite.getY());
		Vector2 leftTopCorner = new Vector2(sprite.getX() + 0.1f, sprite.getY() + 0.9f);
		Vector2 rightBottomCorner = new Vector2(sprite.getX() + 0.9f, sprite.getY());
		Vector2 rightTopCorner = new Vector2(sprite.getX() + 0.9f, sprite.getY() + 0.9f);

		boolean isCollision = isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		sprite.getMapCollisionEvent().setCollidingBottom(isCollision);

		isCollision = isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		sprite.getMapCollisionEvent().setCollidingTop(sprite.getMapCollisionEvent().isCollidingTop() || isCollision);

		isCollision = isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		sprite.getMapCollisionEvent()
				.setCollidingBottom(sprite.getMapCollisionEvent().isCollidingBottom() || isCollision);

		isCollision = isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		sprite.getMapCollisionEvent().setCollidingTop(sprite.getMapCollisionEvent().isCollidingTop() || isCollision);

	}
	
	private boolean isCollisioningTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			return cell.getTile().getId() < 128;
		}
		return false;
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public List<MysteryBlock> getMysteryBlocks() {
		return mysteryBlocks;
	}

	public void setMysteryBlocks(List<MysteryBlock> mysteryBlocks) {
		this.mysteryBlocks = mysteryBlocks;
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
}
