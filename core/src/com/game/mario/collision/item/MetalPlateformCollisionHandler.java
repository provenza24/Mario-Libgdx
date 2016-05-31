package com.game.mario.collision.item;

import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class MetalPlateformCollisionHandler extends AbstractItemCollisionHandler {

	public MetalPlateformCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {		
		if (mario.getMove().y<=0) {
			mario.setOnFloor(true);
			mario.getAcceleration().y = 0;
			mario.setY(item.getY()+item.getHeight());
			if (mario.getState()==SpriteStateEnum.JUMPING || mario.getState()==SpriteStateEnum.FALLING) {
				mario.setState(SpriteStateEnum.NO_MOVE);
			}
			mario.updateAnimation(0);
		}
				
	}
}
