package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.mario.tilemap.TmxMap;

public interface IMoveable {

	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime);	
	
}
