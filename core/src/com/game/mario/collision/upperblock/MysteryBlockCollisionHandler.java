package com.game.mario.collision.upperblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.GameManager;
import com.game.mario.ResourcesLoader;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.ChangeCellValueAction;
import com.game.mario.action.ReplaceWallAction;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.item.EjectedCoin;
import com.game.mario.sprite.item.Mushroom;
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
					item = new Mushroom(block.getX(), yWallBlock+0.1f);
					ResourcesLoader.SOUND_POWERUP_APPEAR.play();
				} else if (itemEnum==ItemEnum.COIN) {
					GameManager.getGameManager().addCoin();
					item = new EjectedCoin(block.getX(), yWallBlock+1);
					ResourcesLoader.SOUND_COIN.play();
				}
				tileMap.getItems().add(item);
				stage.addActor(item);
				item.addAppearAction();
			}			
		} else {
			Gdx.app.log("ERROR", "Block already taken in account");
		}
		
	}

}
