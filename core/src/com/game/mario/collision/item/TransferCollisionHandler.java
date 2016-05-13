package com.game.mario.collision.item;

import com.badlogic.gdx.Gdx;
import com.game.mario.action.ActionFacade;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.item.TransferItem;
import com.game.mario.sprite.tileobject.mario.Mario;

public class TransferCollisionHandler extends AbstractItemCollisionHandler {

	public TransferCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, IScrollingBackground scrollingBackground) {		
		TransferItem transferItem = (TransferItem)item;
		if (Gdx.input.isKeyPressed(transferItem.getKeyToPress())) {	
			mario.setTransferItem(transferItem);
			mario.setInTransfer(true);
			mario.setCurrentAnimation(mario.getMarioVictoryAnimation());
			mario.addAction(ActionFacade.createMoveAction(mario.getX(), mario.getY()-2, 2f));			
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);			
 		}
	}

}
