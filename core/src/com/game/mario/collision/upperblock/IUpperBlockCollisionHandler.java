package com.game.mario.collision.upperblock;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public interface IUpperBlockCollisionHandler {

	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage);
}
