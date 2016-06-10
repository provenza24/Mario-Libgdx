package com.game.mario.collision.plateform;

import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;

public class MetalPlateformCollisionHandler extends AbstractPlateformCollisionHandler {

	public MetalPlateformCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractMetalPlateform plateform) {		

		super.collide(mario, plateform);
		
	}
}
