package com.mygdx.game.mario.collision.upperblock;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.action.ChangeCellValueAction;
import com.mygdx.game.mario.action.DeleteBlocSpriteAction;
import com.mygdx.game.mario.sprite.bloc.WallBlock;
import com.mygdx.game.mario.tilemap.TmxCell;
import com.mygdx.game.mario.tilemap.TmxMap;

public class WallCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public WallCollisionHandler() {
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		WallBlock wallBlock = new WallBlock(collidingCell.getX(), collidingCell.getY(),
				collidingCell.getCell().getTile().getId());
		tileMap.getBlocks().add(wallBlock);
		stage.addActor(wallBlock);

		float yWallBlock = wallBlock.getY();

		ChangeCellValueAction changeCellValueAction = new ChangeCellValueAction(tileMap, collidingCell.getX(), collidingCell.getY(), 128);
		Action moveUpAction1 = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock + 0.1f, 0f);
		Action moveUpAction2 = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock + 0.4f, 0.08f);
		Action moveDownAction = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock, 0.08f);
		ChangeCellValueAction changeCellValueAction2 = new ChangeCellValueAction(tileMap, collidingCell.getX(), collidingCell.getY(), wallBlock.getTileId());
		DeleteBlocSpriteAction deleteWallAction = new DeleteBlocSpriteAction(wallBlock);
		SequenceAction sequenceAction = new SequenceAction(moveUpAction1, changeCellValueAction, moveUpAction2, moveDownAction);
		sequenceAction.addAction(changeCellValueAction2);
		sequenceAction.addAction(moveUpAction1);
		sequenceAction.addAction(deleteWallAction);

		wallBlock.addAction(sequenceAction);
	}
	
}
