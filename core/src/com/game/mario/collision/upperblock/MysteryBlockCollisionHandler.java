package com.game.mario.collision.upperblock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.ChangeCellValueAction;
import com.game.mario.action.ReplaceWallAction;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.TileIdConstants;

public class MysteryBlockCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public MysteryBlockCollisionHandler() {		
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		
		Block block = tileMap.getBlockAt(collidingCell.getX(), collidingCell.getY());
		if (block!=null) {
			float yWallBlock = block.getY();
			SequenceAction sequenceAction = new SequenceAction(
					new ChangeCellValueAction(tileMap, (int)block.getX(), (int)yWallBlock, TileIdConstants.INVISIBLE_BLOCK),
					ActionFacade.createMoveAction(block.getX(), yWallBlock + 0.4f, 0.08f),
					ActionFacade.createMoveAction(block.getX(), yWallBlock, 0.08f),
					new ReplaceWallAction(tileMap, block));
			block.addAction(sequenceAction);		
			// Add item 
			addItemFromBlock(tileMap, stage, block);
			// Check if one or several items were over the wall
			bumpElements(tileMap, collidingCell, stage);
		} else {
			Gdx.app.log("ERROR", "Block already taken in account");
		}
		
	}

}
