package com.game.mario.collision.upperblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.GameManager;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.item.EjectedCoin;
import com.game.mario.sprite.item.Flower;
import com.game.mario.sprite.item.GreenMushroom;
import com.game.mario.sprite.item.RedMushroom;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.TileIdConstants;

public abstract class AbstractUpperBlockCollisionHandler implements IUpperBlockCollisionHandler  {

	private static Map<Integer, IUpperBlockCollisionHandler> handlers = new HashMap<Integer, IUpperBlockCollisionHandler>();
	
	static {
		// Mystery blocks collision handlers		
		handlers.put(TileIdConstants.MYSTERY_BLOCK_COIN, new MysteryBlockCollisionHandler());
		handlers.put(TileIdConstants.MYSTERY_BLOCK_RED_MUSHROOM, new MysteryBlockCollisionHandler());
		
		// Walls collision handlers
		handlers.put(TileIdConstants.WALL_OVERGROUND, new WallCollisionHandler());
		handlers.put(TileIdConstants.WALL_UNDERGROUND, new WallCollisionHandler());
		handlers.put(TileIdConstants.WALL_OVERGROUND_10_COINS, new WallCollisionHandler());
		handlers.put(TileIdConstants.WALL_UNDERGROUND_10_COINS, new WallCollisionHandler());
		handlers.put(TileIdConstants.WALL_UNDERGROUND_GREEN_MUSHROOM, new WallCollisionHandler());
		handlers.put(TileIdConstants.WALL_OVERGROUND_GREEN_MUSHROOM, new WallCollisionHandler());		
	}
	
	public static IUpperBlockCollisionHandler getHandler(Integer tileId) {
		return handlers.get(tileId);
	}
	
	protected void bumpElements(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
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
	
	protected void addItemFromBlock(TmxMap tileMap, Stage stage, Block block) {
		
		float yWallBlock = block.getY();
		
		ItemEnum itemEnum = block.getItemEnum();
		
		if (itemEnum!=null) {
			AbstractItem item = null;
			if (itemEnum==ItemEnum.RED_MUSHROOM) {
				Mario mario = tileMap.getMario();
				if (mario.getSizeState()==0) {
					item = new RedMushroom(block.getX(), yWallBlock+0.1f);
				} else {
					item = new Flower(block.getX(), yWallBlock+0.1f);
				}
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP_APPEAR);						
			} else if (itemEnum==ItemEnum.GREEN_MUSHROOM) {
				item = new GreenMushroom(block.getX(), yWallBlock+0.1f);
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP_APPEAR);						
			} else if (itemEnum==ItemEnum.COIN) {
				GameManager.getGameManager().addCoin();
				item = new EjectedCoin(block.getX(), yWallBlock+1);
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);						
			}
			tileMap.getItems().add(item);
			stage.addActor(item);
			item.addAppearAction();
		}
		
		
	}
	
}
