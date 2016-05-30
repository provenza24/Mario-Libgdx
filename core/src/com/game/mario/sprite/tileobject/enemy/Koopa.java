package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.maps.MapObject;
import com.game.mario.util.ResourcesLoader;

public class Koopa extends AbstractKoopa {
	
	public Koopa(MapObject mapObject) {
		super(mapObject);		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = ResourcesLoader.KOOPA;
		initializeTextures();
	}
	
}
