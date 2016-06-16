package com.game.mario.util.sprite.factory;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractTiledItemFactory implements ISpriteFactory {

	public AbstractTiledItemFactory() {		
	}

	@Override	
	public abstract AbstractTileObjectSprite create(MapObject mapObject, WorldTypeEnum worldType, Mario mario);

}
