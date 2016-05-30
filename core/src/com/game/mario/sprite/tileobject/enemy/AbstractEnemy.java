package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public abstract class AbstractEnemy extends AbstractTileObjectSprite {
			
	public AbstractEnemy(MapObject mapObject) {
		super(mapObject);
		state = SpriteStateEnum.WALKING;
	}
	
	public abstract EnemyTypeEnum getEnemyType();
	
	public void killByFireball(AbstractSprite fireball) {
		this.bump();
		acceleration.x = fireball.getAcceleration().x > 0 ? 3 : -3;
	}		

}
