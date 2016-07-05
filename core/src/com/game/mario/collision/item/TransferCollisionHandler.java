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

/**
 * A TransferItem can teleporte Mario from a point of the tilemap to another (from a pipe to another pipe)
 */
public class TransferCollisionHandler extends AbstractItemCollisionHandler {

	public TransferCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		
		TransferItem transferItem = (TransferItem)item;
		
		if (Gdx.input.isKeyPressed(transferItem.getKeyToPress())) {	
			// Mario collides with the transport element, check if the user if pressing the correct key to teleport Mario
			
			// Store the item in Mario class to play the animation before moving physically Mario to his new position
			mario.setTransferItem(transferItem);
			mario.setInTransfer(true);			
			
			// Define an action to move Mario during the animation of the teleportation
			Action moveAction = null;
			
			if (transferItem.getKeyToPress()==Keys.DOWN) {
				// Mario is entering a pipe to the bottom
				mario.setX(((int)transferItem.getX()) + 0.5f);
				moveAction = ActionFacade.createMoveAction(mario.getX(), mario.getY()-2, 1.5f);
				mario.setCurrentAnimation(mario.getMarioVictoryAnimation());
			} else if (transferItem.getKeyToPress()==Keys.RIGHT) {
				// Mario is entering a pipe on his right
				mario.setY(((int)transferItem.getY()));
				moveAction = ActionFacade.createMoveAction(((int)transferItem.getX())+1, mario.getY(), 1.5f);
				mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			}
			mario.addAction(moveAction);			
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);			
 		}
	}

}
