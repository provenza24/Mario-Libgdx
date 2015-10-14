package com.mygdx.game.mario.collision.upperblock;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.action.ReplaceWallAction;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class MysteryBlockCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public MysteryBlockCollisionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		Block block = tileMap.getBlockAt(collidingCell.getX(), collidingCell.getY());
		float yWallBlock = block.getY();
		SequenceAction sequenceAction = new SequenceAction(
				ActionFacade.createMoveAction(block.getX(), yWallBlock + 0.4f, 0.08f),
				ActionFacade.createMoveAction(block.getX(), yWallBlock, 0.08f),
				new ReplaceWallAction(tileMap, block));
		block.addAction(sequenceAction);
	}

}
