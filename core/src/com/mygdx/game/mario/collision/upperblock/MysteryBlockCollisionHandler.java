package com.mygdx.game.mario.collision.upperblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.action.ReplaceWallAction;
import com.mygdx.game.mario.enums.ItemEnum;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.sprite.bloc.MysteryBlock;
import com.mygdx.game.mario.sprite.item.Mushroom;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class MysteryBlockCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public MysteryBlockCollisionHandler() {		
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		Block block = tileMap.getBlockAt(collidingCell.getX(), collidingCell.getY());
		float yWallBlock = block.getY();
		SequenceAction sequenceAction = new SequenceAction(
				ActionFacade.createMoveAction(block.getX(), yWallBlock + 0.4f, 0.08f),
				ActionFacade.createMoveAction(block.getX(), yWallBlock, 0.08f),
				new ReplaceWallAction(tileMap, block));
		block.addAction(sequenceAction);
		MysteryBlock mysteryBlock = (MysteryBlock) block;
		ItemEnum itemEnum = mysteryBlock.getItemEnum();
		if (itemEnum==ItemEnum.RED_MUSHROOM) {
			Gdx.app.log("ITEM", "Creating mushroom");
			Mushroom mushroom = new Mushroom(block.getX(), yWallBlock);
			tileMap.getItems().add(mushroom);
			stage.addActor(mushroom);
			mushroom.addAction(ActionFacade.createMoveAction(mushroom.getX(), mushroom.getY()+1, 0.5f));			
		}
	}

}
