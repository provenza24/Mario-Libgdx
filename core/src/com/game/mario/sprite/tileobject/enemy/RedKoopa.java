package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class RedKoopa extends AbstractKoopa {
			
	public RedKoopa(MapObject mapObject) {
		super(mapObject);		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.KOOPA_RED;
		initializeTextures();		
	}

	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		super.update(tileMap, camera, deltaTime);
		if (isAlive() && !isOnFloor() && state!=SpriteStateEnum.SLIDING 
				&& state!=SpriteStateEnum.FALLING_AFTER_FLY 
				&& state!=SpriteStateEnum.FLYING) {						
			setX(oldPosition.x);
			setY(oldPosition.y);
			acceleration.x = -acceleration.x;			
		}			
	}
	
	
	
}
