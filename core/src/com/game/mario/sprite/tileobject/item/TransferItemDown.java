package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.util.ResourcesLoader;

public class TransferItemDown extends TransferItem {

	public TransferItemDown(MapObject mapObject) {
		super(mapObject);
		keyToPress = Keys.DOWN;
		pipe = new Sprite(ResourcesLoader.PIPE_DOWN);
		pipe.setPosition((int)getX(), (int)getY() -2);
	}
	
}
