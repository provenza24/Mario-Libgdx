package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.KoopaStateEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public abstract class AbstractEnemy extends AbstractTileObjectSprite {
	
	
	
	public AbstractEnemy(MapObject mapObject) {
		super(mapObject);
	}
	
	public abstract EnemyTypeEnum getEnemyType();
	
	public abstract KoopaStateEnum getEnemyState();

}
