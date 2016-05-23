package com.game.mario.collision.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
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
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {		
		TransferItem transferItem = (TransferItem)item;
		if (Gdx.input.isKeyPressed(transferItem.getKeyToPress())) {	
			
			mario.setTransferItem(transferItem);
			mario.setInTransfer(true);			
			
			Action moveAction = null;
			
			if (transferItem.getKeyToPress()==Keys.DOWN) {
				mario.setX(((int)transferItem.getX()) + 0.5f);
				moveAction = ActionFacade.createMoveAction(mario.getX(), mario.getY()-2, 1.5f);
				mario.setCurrentAnimation(mario.getMarioVictoryAnimation());
			} else if (transferItem.getKeyToPress()==Keys.RIGHT) {
				mario.setY(((int)transferItem.getY()));
				moveAction = ActionFacade.createMoveAction(((int)transferItem.getX())+1, mario.getY(), 1.5f);
				mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			}
			mario.addAction(moveAction);			
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);			
 		}
	}

}
