package com.game.mario.collision.tilemap;

import com.game.mario.sprite.AbstractSprite;
import com.game.mario.tilemap.TmxMap;

public interface ITilemapCollisionHandler {

	public void collideWithTilemap(TmxMap tileMap, AbstractSprite sprite);

}
