package com.game.mario.collision.upperblock;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.ResourcesLoader;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.ChangeCellValueAction;
import com.game.mario.action.DeleteBlocSpriteAction;
import com.game.mario.collision.item.AbstractItemCollisionHandler;
import com.game.mario.collision.item.IItemCollisionHandler;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.WallBlock;
import com.game.mario.sprite.item.wallpiece.AbstractWallPiece;
import com.game.mario.sprite.item.wallpiece.BottomLeftWallPiece;
import com.game.mario.sprite.item.wallpiece.BottomRightWallPiece;
import com.game.mario.sprite.item.wallpiece.TopLeftWallPiece;
import com.game.mario.sprite.item.wallpiece.TopRightWallPiece;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;

public class WallCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public WallCollisionHandler() {
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		
		Mario mario = tileMap.getMario();		
		if (mario.getSizeState()==0) {
			// Mario is small, can't beak the wall, just move it
			moveWall(tileMap, collidingCell, stage);
		} else {
			// Mario is big, break the wall
			breakWall(tileMap, collidingCell, stage);
		}
		
		// Check if one or several items were over the wall
		bumpItems(tileMap, collidingCell, stage);
						
	}

	private void bumpItems(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		List<AbstractSprite> itemsToHandle = new ArrayList<AbstractSprite>();
		for (AbstractSprite item : tileMap.getItems()) {			
			if (collidingCell.getX() == (int)(item.getX()+item.getWidth()/2) 
					&& collidingCell.getY() == (int)(item.getY()) - 1) {
				itemsToHandle.add(item);							
			} 			
		}
		for (AbstractSprite item : itemsToHandle) {
			IItemCollisionHandler itemCollisionHandler = AbstractItemCollisionHandler.getHandler(item);
			if (itemCollisionHandler!=null) {
				itemCollisionHandler.bump(stage, tileMap, item);
			}			
		}
		
		List<AbstractSprite> enemiesToHandle = new ArrayList<AbstractSprite>();
		for (AbstractSprite enemy : tileMap.getEnemies()) {			
			if (collidingCell.getX() == (int)(enemy.getX()+enemy.getWidth()/2) 
					&& collidingCell.getY() == (int)(enemy.getY()) - 1) {
				enemiesToHandle.add(enemy);							
			} 			
		}
		for (AbstractSprite enemy : enemiesToHandle) {
			// TODO Do same stuff for enemies	
		}
	}

	private void breakWall(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		
		tileMap.removeCell(collidingCell.getX(), collidingCell.getY());
		
		AbstractWallPiece topLeftPiece = new TopLeftWallPiece(collidingCell.getX(), collidingCell.getY()+0.5f); 
		tileMap.getItems().add(topLeftPiece);
		stage.addActor(topLeftPiece);
		
		AbstractWallPiece topRightPiece = new TopRightWallPiece(collidingCell.getX()+0.5f, collidingCell.getY()+0.5f); 
		tileMap.getItems().add(topRightPiece);
		stage.addActor(topRightPiece);
		
		AbstractWallPiece bottomRightPiece = new BottomRightWallPiece(collidingCell.getX()+0.5f, collidingCell.getY()); 
		tileMap.getItems().add(bottomRightPiece);
		stage.addActor(bottomRightPiece);
		
		AbstractWallPiece bottomLeftPiece = new BottomLeftWallPiece(collidingCell.getX(), collidingCell.getY()); 
		tileMap.getItems().add(bottomLeftPiece);
		stage.addActor(bottomLeftPiece);
		
		ResourcesLoader.SOUND_BREAK_BLOCK.play();
	}

	private void moveWall(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
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
		
		ResourcesLoader.SOUND_BUMP.play();
	}
	
}
