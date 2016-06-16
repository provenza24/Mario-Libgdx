package com.game.mario.util.sprite.factory.impl;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.sprite.tileobject.enemy.Bowser;
import com.game.mario.sprite.tileobject.enemy.CastleFirebar;
import com.game.mario.sprite.tileobject.enemy.Goomba;
import com.game.mario.sprite.tileobject.enemy.Koopa;
import com.game.mario.sprite.tileobject.enemy.PiranhaPlant;
import com.game.mario.sprite.tileobject.enemy.RedKoopa;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.sprite.factory.AbstractTiledEnemyFactory;

public class TiledEnemyFactory extends AbstractTiledEnemyFactory {

	public TiledEnemyFactory() {	
	}

	@Override
	public AbstractTileObjectEnemy create(MapObject mapObject, WorldTypeEnum worldType, Mario mario) {
		
		AbstractTileObjectEnemy enemy = null;
		
		MapProperties mapProperties = mapObject.getProperties();
		String type = (String)mapProperties.get("type");
		
		if (type.equals("piranha")) {				
			enemy = new PiranhaPlant(mapObject, mario);
		}
		if (type.equals("goomba")) {				
			enemy = new Goomba(mapObject, worldType);
		}
		if (type.equals("koopa")) {				
			enemy = new Koopa(mapObject);
		}			
		if (type.equals("redKoopa")) {				
			enemy = new RedKoopa(mapObject);
		}
		if (type.equals("castleFirebar")) {
			enemy = new CastleFirebar(mapObject);		
		}			
		if (type.equals("bowser")) {				
			enemy = new Bowser(mapObject);
		}
		
		return enemy;
		
	}

	
}
