package com.game.mario.collision.tilemap;

import java.util.ArrayList;

import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class FireballTilemapCollisionHandler extends AbstractTilemapCollisionHandler {
	
	public FireballTilemapCollisionHandler() {						
	}
			
	public void collideWithTilemap(TmxMap tileMap, AbstractSprite sprite) {
		
		sprite.setCollidingCells(new ArrayList<TmxCell>());
				
		checkMapCollision(tileMap, sprite);				
												
		if (sprite.getMapCollisionEvent().getCollisionPoints().size()>0) {
			
			if (sprite.getMapCollisionEvent().isBlockedBottomOnly()) {
				sprite.getAcceleration().y = 0.15f;
				sprite.setY((int)sprite.getY()+1);
			} else {
				sprite.setDeletable(true);
			}						
		}				
		
	}	

}
