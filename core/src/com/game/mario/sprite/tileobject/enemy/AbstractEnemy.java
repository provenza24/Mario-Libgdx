package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.EnemyStateEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public abstract class AbstractEnemy extends AbstractTileObjectSprite {
	
	protected EnemyStateEnum state;
			
	public AbstractEnemy(MapObject mapObject) {
		super(mapObject);
		state = EnemyStateEnum.WALKING;
	}
	
	public abstract EnemyTypeEnum getEnemyType();
	
	public EnemyStateEnum getEnemyState() {
		return state;
	}
	
	public void killByFireball(AbstractSprite fireball) {
		this.bump();
		acceleration.x = fireball.getAcceleration().x > 0 ? 3 : -3;
	}

}
