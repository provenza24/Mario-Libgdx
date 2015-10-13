package com.mygdx.game.mario.sprite;

import com.badlogic.gdx.maps.MapObject;

public abstract class AbstractTilemapSprite extends AbstractSprite {

	public AbstractTilemapSprite(MapObject mapObject) {
		super((Float) mapObject.getProperties().get("x")/32, (Float) mapObject.getProperties().get("y")/32 + 1);
		String xAliveString = (String) mapObject.getProperties().get("xAlive");
		xAlive = xAliveString!=null ? Float.parseFloat(xAliveString) / 32 : getX() - 16 ;		
	}

}
