package com.mygdx.game.mario.collision.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.mario.camera.GameCamera;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.tileobject.item.TransferItem;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;

public class TransferCollisionHandler extends AbstractItemCollisionHandler {

	public TransferCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera) {		
		TransferItem transferItem = (TransferItem)item;
		if (Gdx.input.isKeyPressed(transferItem.getKeyToPress())) {			
			mario.setAcceleration(new Vector2(0, 0));
			mario.setDirection(DirectionEnum.RIGHT);
			mario.setX(transferItem.getTransferPosition().x);
			mario.setY(transferItem.getTransferPosition().y);
			camera.setCameraOffset(2f);
			camera.getCamera().position.x = transferItem.getTransferPosition().x + 6;						
			camera.getCamera().update();			
			camera.setScrollable(transferItem.isScrollableCamera());			
		}
	}

}