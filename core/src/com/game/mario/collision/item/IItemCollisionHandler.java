package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public interface IItemCollisionHandler {

	/** Method called when an item collides with Mario */
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds);

	/** Method called the item is over a wall, and Mario bump the wall */
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item);
	
}
