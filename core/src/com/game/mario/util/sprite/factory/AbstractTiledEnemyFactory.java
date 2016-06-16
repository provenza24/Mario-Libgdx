package com.game.mario.util.sprite.factory;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractTiledEnemyFactory implements ISpriteFactory {

	public AbstractTiledEnemyFactory() {		
	}

	@Override	
	public abstract AbstractTileObjectEnemy create(MapObject mapObject, WorldTypeEnum worldType, Mario mario);

}
