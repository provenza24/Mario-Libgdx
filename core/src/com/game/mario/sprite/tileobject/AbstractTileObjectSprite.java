package com.game.mario.sprite.tileobject;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.sprite.AbstractSprite;

public abstract class AbstractTileObjectSprite extends AbstractSprite {

	public AbstractTileObjectSprite(MapObject mapObject) {
		super((Float) mapObject.getProperties().get("x")/32, (Float) mapObject.getProperties().get("y")/32+1);
		String xAliveString = (String) mapObject.getProperties().get("xAlive");
		xAlive = xAliveString!=null ? Float.parseFloat(xAliveString) / 32 : getX() - 16 ;
		collidableWithTilemap = true;
		moveable = true;
	}

}
