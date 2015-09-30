package com.mygdx.game.mario.tilemap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.sprite.Mario;

public class MarioTileMap {
	
	private TiledMap map;
	
	TiledMapTileLayer layer;
	
	public MarioTileMap(String levelName) {
		map = new TmxMapLoader().load(levelName);
		layer = (TiledMapTileLayer)map.getLayers().get(0);
	}
	 
	public boolean isCollisioningTileAt(int x, int y) {
		Cell cell = layer.getCell(x,y);		
		if (cell!=null) {
			return cell.getTile().getId() < 128;
		}
		return false;
	}
	
	public void checkHorizontalMapCollision(Mario mario) {
		
		mario.reinitHorizontalMapCollisionEvent();
		
		Vector2 leftBottomCorner = new Vector2(mario.getX(), mario.getY());
		Vector2 leftTopCorner = new Vector2(mario.getX(), mario.getY() + 1);
		Vector2 rightBottomCorner = new Vector2(mario.getX()+1, mario.getY());
		Vector2 rightTopCorner = new Vector2(mario.getX()+1, mario.getY() + 1);
				
		boolean isCollision = isCollisioningTileAt((int)leftBottomCorner.x, (int)leftBottomCorner.y);		
		mario.getMapCollisionEvent().setCollidingLeft(isCollision);
		
		isCollision = isCollisioningTileAt((int)leftTopCorner.x, (int)leftTopCorner.y);		
		mario.getMapCollisionEvent().setCollidingLeft(mario.getMapCollisionEvent().isCollidingLeft() || isCollision);
		
		isCollision = isCollisioningTileAt((int)rightBottomCorner.x, (int)rightBottomCorner.y);		
		mario.getMapCollisionEvent().setCollidingRight(mario.getMapCollisionEvent().isCollidingRight() || isCollision);
					
		isCollision = isCollisioningTileAt((int)rightTopCorner.x, (int)rightTopCorner.y);		
		mario.getMapCollisionEvent().setCollidingRight(mario.getMapCollisionEvent().isCollidingRight() ||isCollision);		
							
	}
	
	public void checkVerticalMapCollision(Mario mario) {
		
		mario.reinitVerticalMapCollisionEvent();
		
		Vector2 leftBottomCorner = new Vector2(mario.getX()+0.1f, mario.getY());
		Vector2 leftTopCorner = new Vector2(mario.getX()+0.1f, mario.getY() + 1);
		Vector2 rightBottomCorner = new Vector2(mario.getX()+0.9f, mario.getY());
		Vector2 rightTopCorner = new Vector2(mario.getX()+0.9f, mario.getY() + 1);
				
		boolean isCollision = isCollisioningTileAt((int)leftBottomCorner.x, (int)leftBottomCorner.y);		
		mario.getMapCollisionEvent().setCollidingBottom(isCollision);
		
		isCollision = isCollisioningTileAt((int)leftTopCorner.x, (int)leftTopCorner.y);		
		mario.getMapCollisionEvent().setCollidingTop(mario.getMapCollisionEvent().isCollidingTop() || isCollision);
		
		isCollision = isCollisioningTileAt((int)rightBottomCorner.x, (int)rightBottomCorner.y);		
		mario.getMapCollisionEvent().setCollidingBottom(mario.getMapCollisionEvent().isCollidingBottom() || isCollision);
					
		isCollision = isCollisioningTileAt((int)rightTopCorner.x, (int)rightTopCorner.y);		
		mario.getMapCollisionEvent().setCollidingTop(mario.getMapCollisionEvent().isCollidingTop() ||isCollision);
		
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
}
