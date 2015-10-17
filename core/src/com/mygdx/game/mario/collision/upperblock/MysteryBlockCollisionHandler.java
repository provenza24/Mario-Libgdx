package com.mygdx.game.mario.collision.upperblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.action.ReplaceWallAction;
import com.mygdx.game.mario.enums.ItemEnum;
import com.mygdx.game.mario.sprite.AbstractItem;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.sprite.bloc.MysteryBlock;
import com.mygdx.game.mario.sprite.item.EjectedCoin;
import com.mygdx.game.mario.sprite.item.Mushroom;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class MysteryBlockCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public MysteryBlockCollisionHandler() {		
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		Block block = tileMap.getBlockAt(collidingCell.getX(), collidingCell.getY());
		if (block!=null) {
			float yWallBlock = block.getY();
			SequenceAction sequenceAction = new SequenceAction(
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
					item = new Mushroom(block.getX(), yWallBlock);
				} else if (itemEnum==ItemEnum.COIN) {
					item = new EjectedCoin(block.getX(), yWallBlock+1);
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
