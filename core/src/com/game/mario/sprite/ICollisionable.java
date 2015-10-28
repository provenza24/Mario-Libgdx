package com.game.mario.sprite;

import com.game.mario.tilemap.TmxMap;

public interface ICollisionable {

	public void collideWithTilemap(TmxMap tileMap);
}
