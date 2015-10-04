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
import com.mygdx.game.mario.sprite.impl.Mario;
import com.mygdx.game.mario.sprite.impl.MysteryBlock;

public class MarioTileMap {

	private TiledMap map;

	private TiledMapTileLayer tileLayer;
	
	private MapLayer objectsLayer;

	private List<MysteryBlock> mysteryBlocks;
	
	private List<MysteryBlock> enemies;
	
	public MarioTileMap(String levelName) {
		
		map = new TmxMapLoader().load(levelName);
		tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
		objectsLayer = map.getLayers().get(1);
		initMysteryBlocks();			
		initMapObjects();		
	}

	private void initMapObjects() {
		
		MapObjects objects = objectsLayer.getObjects();
		for (MapObject mapObject : objects) {
			MapProperties objectProperty = mapObject.getProperties();		
			Gdx.app.log("New object from layer", objectProperty.get("type").toString());			
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

	public boolean isCollisioningTileAt(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			return cell.getTile().getId() < 128;
		}
		return false;
	}

	public void checkHorizontalMapCollision(Mario mario) {

		mario.reinitHorizontalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(mario.getX(), mario.getY());
		Vector2 leftTopCorner = new Vector2(mario.getX(), mario.getY() + 1);
		Vector2 rightBottomCorner = new Vector2(mario.getX() + 1, mario.getY());
		Vector2 rightTopCorner = new Vector2(mario.getX() + 1, mario.getY() + 1);

		boolean isCollision = isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		mario.getMapCollisionEvent().setCollidingLeft(isCollision);

		isCollision = isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		mario.getMapCollisionEvent().setCollidingLeft(mario.getMapCollisionEvent().isCollidingLeft() || isCollision);

		isCollision = isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		mario.getMapCollisionEvent().setCollidingRight(mario.getMapCollisionEvent().isCollidingRight() || isCollision);

		isCollision = isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		mario.getMapCollisionEvent().setCollidingRight(mario.getMapCollisionEvent().isCollidingRight() || isCollision);

	}

	public void checkVerticalMapCollision(Mario mario) {

		mario.reinitVerticalMapCollisionEvent();

		Vector2 leftBottomCorner = new Vector2(mario.getX() + 0.1f, mario.getY());
		Vector2 leftTopCorner = new Vector2(mario.getX() + 0.1f, mario.getY() + 1);
		Vector2 rightBottomCorner = new Vector2(mario.getX() + 0.9f, mario.getY());
		Vector2 rightTopCorner = new Vector2(mario.getX() + 0.9f, mario.getY() + 1);

		boolean isCollision = isCollisioningTileAt((int) leftBottomCorner.x, (int) leftBottomCorner.y);
		mario.getMapCollisionEvent().setCollidingBottom(isCollision);

		isCollision = isCollisioningTileAt((int) leftTopCorner.x, (int) leftTopCorner.y);
		mario.getMapCollisionEvent().setCollidingTop(mario.getMapCollisionEvent().isCollidingTop() || isCollision);

		isCollision = isCollisioningTileAt((int) rightBottomCorner.x, (int) rightBottomCorner.y);
		mario.getMapCollisionEvent()
				.setCollidingBottom(mario.getMapCollisionEvent().isCollidingBottom() || isCollision);

		isCollision = isCollisioningTileAt((int) rightTopCorner.x, (int) rightTopCorner.y);
		mario.getMapCollisionEvent().setCollidingTop(mario.getMapCollisionEvent().isCollidingTop() || isCollision);

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
}
