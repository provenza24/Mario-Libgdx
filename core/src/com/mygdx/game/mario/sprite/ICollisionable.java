package com.mygdx.game.mario.sprite;

import com.mygdx.game.mario.tilemap.TmxMap;

public interface ICollisionable {

	public void collideWithTilemap(TmxMap tileMap);
}
