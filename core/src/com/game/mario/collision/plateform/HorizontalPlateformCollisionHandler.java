package com.game.mario.collision.plateform;

import com.game.mario.enums.DirectionEnum;
import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;

public class HorizontalPlateformCollisionHandler extends AbstractPlateformCollisionHandler {

	public HorizontalPlateformCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractMetalPlateform plateform) {
		
		super.collide(mario, plateform);		
		mario.setX(mario.getX() + (plateform.getDirection()==DirectionEnum.LEFT ? -plateform.getAcceleration().x: plateform.getAcceleration().x));
	}

}
