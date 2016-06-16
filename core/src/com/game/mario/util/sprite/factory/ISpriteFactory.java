package com.game.mario.util.sprite.factory;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public interface ISpriteFactory {

	public AbstractSprite create(MapObject mapObject, WorldTypeEnum worldType, Mario mario);
	
}
