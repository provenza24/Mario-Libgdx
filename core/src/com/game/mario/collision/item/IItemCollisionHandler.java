package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public interface IItemCollisionHandler {

	public void collide(Mario mario, AbstractSprite item, GameCamera camera);
	
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item);
}
