package com.mygdx.game.mario.collision.upperblock;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public interface IUpperBlockCollisionHandler {

	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage);
}
