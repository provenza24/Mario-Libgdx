package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.MapObject;

public class TransferItemDown extends TransferItem {

	public TransferItemDown(MapObject mapObject) {
		super(mapObject);
		keyToPress = Keys.DOWN;
	}

}
