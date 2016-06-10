package com.game.mario.collision.plateform;

import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;

public interface IPlateformCollisionHandler {

	public void collide(Mario mario, AbstractMetalPlateform plateform);		
}
