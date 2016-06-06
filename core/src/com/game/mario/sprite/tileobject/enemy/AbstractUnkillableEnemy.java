package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractUnkillableEnemy extends AbstractEnemy {

	public AbstractUnkillableEnemy(MapObject mapObject, Vector2 offset) {
		super(mapObject, offset);		
	}

	@Override
	public boolean collideMario(Mario mario) {
		return false;
	}
	
}
