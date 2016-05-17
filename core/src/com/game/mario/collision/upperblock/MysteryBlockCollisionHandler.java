package com.game.mario.collision.upperblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.GameManager;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.ChangeCellValueAction;
import com.game.mario.action.ReplaceWallAction;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.item.EjectedCoin;
import com.game.mario.sprite.item.Flower;
import com.game.mario.sprite.item.GreenMushroom;
import com.game.mario.sprite.item.RedMushroom;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class MysteryBlockCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public MysteryBlockCollisionHandler() {		
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		Block block = tileMap.getBlockAt(collidingCell.getX(), collidingCell.getY());
		if (block!=null) {
			float yWallBlock = block.getY();
			SequenceAction sequenceAction = new SequenceAction(
					new ChangeCellValueAction(tileMap, (int)block.getX(), (int)yWallBlock, 128),
					ActionFacade.createMoveAction(block.getX(), yWallBlock + 0.4f, 0.08f),
					ActionFacade.createMoveAction(block.getX(), yWallBlock, 0.08f),
					new ReplaceWallAction(tileMap, block));
			block.addAction(sequenceAction);
			MysteryBlock mysteryBlock = (MysteryBlock) block;
			ItemEnum itemEnum = mysteryBlock.getItemEnum();
			//@TODO refactor this in an Item abstract class, method appearAction
			if (itemEnum!=null) {
				AbstractItem item = null;
				if (itemEnum==ItemEnum.RED_MUSHROOM) {
					Mario mario = tileMap.getMario();
					if (mario.getSizeState()==0) {
						item = new RedMushroom(block.getX(), yWallBlock+0.1f);
					} else {
						item = new Flower(block.getX(), yWallBlock+0.1f);
					}
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP_APPEAR);						
				} if (itemEnum==ItemEnum.GREEN_MUSHROOM) {
					item = new GreenMushroom(block.getX(), yWallBlock+0.1f);
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP_APPEAR);						
				} else if (itemEnum==ItemEnum.COIN) {
					GameManager.getGameManager().addCoin();
					item = new EjectedCoin(block.getX(), yWallBlock+1);
					SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);						
				}
				tileMap.getItems().add(item);
				stage.addActor(item);
				item.addAppearAction();
			}
			// Check if one or several items were over the wall
			bumpElements(tileMap, collidingCell, stage);
		} else {
			Gdx.app.log("ERROR", "Block already taken in account");
		}
		
	}

}
