package com.game.mario.collision.upperblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractUpperBlockCollisionHandler implements IUpperBlockCollisionHandler  {

	private static Map<Integer, IUpperBlockCollisionHandler> handlers = new HashMap<Integer, IUpperBlockCollisionHandler>();
	
	static {
		handlers.put(4, new WallCollisionHandler());
		handlers.put(64, new WallCollisionHandler());
		handlers.put(7, new MysteryBlockCollisionHandler());
		handlers.put(8, new MysteryBlockCollisionHandler());
		
		handlers.put(10, new WallCollisionHandler());
	}
	
	public static IUpperBlockCollisionHandler getHandler(Integer tileId) {
		return handlers.get(tileId);
	}
	
	public void bumpElements(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		List<AbstractSprite> itemsToHandle = new ArrayList<AbstractSprite>();
		for (AbstractSprite item : tileMap.getItems()) {			
			if (collidingCell.getX() == (int)(item.getX()+item.getWidth()/2) 
					&& collidingCell.getY() == (int)(item.getY()) - 1) {
				itemsToHandle.add(item);							
			} 			
		}
		for (AbstractSprite item : itemsToHandle) {
			IItemCollisionHandler itemCollisionHandler = AbstractItemCollisionHandler.getHandler(item);
			if (itemCollisionHandler!=null) {
				itemCollisionHandler.bump(stage, tileMap, item);
			}			
		}
		
		List<AbstractSprite> enemiesToHandle = new ArrayList<AbstractSprite>();
		for (AbstractSprite enemy : tileMap.getEnemies()) {			
			if (collidingCell.getX() == (int)(enemy.getX()+enemy.getWidth()/2) 
					&& collidingCell.getY() == (int)(enemy.getY()) - 1) {
				enemiesToHandle.add(enemy);							
			} 			
		}
		for (AbstractSprite enemy : enemiesToHandle) {
			// TODO Do same stuff for enemies	
			enemy.bump();
		}
	}
	
}
