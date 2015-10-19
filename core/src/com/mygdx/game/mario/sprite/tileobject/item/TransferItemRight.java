package com.mygdx.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.MapObject;

public class TransferItemRight extends TransferItem {

	public TransferItemRight(MapObject mapObject) {
		super(mapObject);
		keyToPress = Keys.RIGHT;
	}

}
