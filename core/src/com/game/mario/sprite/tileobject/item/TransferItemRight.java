package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.util.ResourcesLoader;

public class TransferItemRight extends TransferItem {

	public TransferItemRight(MapObject mapObject) {
		super(mapObject);
		keyToPress = Keys.RIGHT;
		pipe = new Sprite(ResourcesLoader.PIPE_LEFT);
		pipe.setPosition((int)getX() + 1, (int)getY());
	}

}
