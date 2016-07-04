package com.game.mario.collision.upperblock;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.GameManager;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.ChangeCellValueAction;
import com.game.mario.action.DeleteBlocSpriteAction;
import com.game.mario.enums.ItemEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.sprite.bloc.AbstractBlock;
import com.game.mario.sprite.bloc.WallBlock;
import com.game.mario.sprite.sfx.EjectedCoin;
import com.game.mario.sprite.sfx.wallpiece.AbstractWallPiece;
import com.game.mario.sprite.sfx.wallpiece.BottomLeftWallPiece;
import com.game.mario.sprite.sfx.wallpiece.BottomRightWallPiece;
import com.game.mario.sprite.sfx.wallpiece.TopLeftWallPiece;
import com.game.mario.sprite.sfx.wallpiece.TopRightWallPiece;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxCell;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.constant.TileIdConstants;

public class WallCollisionHandler extends AbstractUpperBlockCollisionHandler {

	public WallCollisionHandler() {
	}
	
	public void handle(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
				
		
		Mario mario = tileMap.getMario();		
		
		WallBlock wallBlock = findBlock(tileMap, collidingCell);
		
		if (wallBlock!=null) {			
			moveWall(tileMap, collidingCell, stage, wallBlock);			
		} else {			
			if (mario.getSizeState()==0) {
				// Mario is small, can't beak the wall, just move it
				moveWall(tileMap, collidingCell, stage, null);
			} else {
				// Mario is big, break the wall
				breakWall(tileMap, collidingCell, stage);
			}
		}		
		
		// Check if one or several items were over the wall
		bumpElements(tileMap, collidingCell, stage);
						
	}

	private WallBlock findBlock(TmxMap tileMap, TmxCell collidingCell) {
		
		 WallBlock wallBlock = null;
		
		for (int i=0; i<tileMap.getWallBlocks().size() && wallBlock==null;i++) {
			WallBlock block = tileMap.getWallBlocks().get(i);
			if (block.getX()==collidingCell.getX() && block.getY()== collidingCell.getY()) {
				wallBlock = block;
			}
		}
		return wallBlock;
	}

	private void breakWall(TmxMap tileMap, TmxCell collidingCell, Stage stage) {
		
		tileMap.removeCell(collidingCell.getX(), collidingCell.getY());
		
		AbstractWallPiece topLeftPiece = new TopLeftWallPiece(collidingCell.getX(), collidingCell.getY()+0.5f, tileMap.getWorldType()); 
		tileMap.getSfxSprites().add(topLeftPiece);
		stage.addActor(topLeftPiece);
		
		AbstractWallPiece topRightPiece = new TopRightWallPiece(collidingCell.getX()+0.5f, collidingCell.getY()+0.5f, tileMap.getWorldType()); 
		tileMap.getSfxSprites().add(topRightPiece);
		stage.addActor(topRightPiece);
		
		AbstractWallPiece bottomRightPiece = new BottomRightWallPiece(collidingCell.getX()+0.5f, collidingCell.getY(), tileMap.getWorldType()); 
		tileMap.getSfxSprites().add(bottomRightPiece);
		stage.addActor(bottomRightPiece);
		
		AbstractWallPiece bottomLeftPiece = new BottomLeftWallPiece(collidingCell.getX(), collidingCell.getY(), tileMap.getWorldType()); 
		tileMap.getSfxSprites().add(bottomLeftPiece);
		stage.addActor(bottomLeftPiece);
		
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_BREAK_BLOCK);				
	}

	private void moveWall(TmxMap tileMap, TmxCell collidingCell, Stage stage, WallBlock specialWallBlock) {
		
		AbstractBlock wallBlock = new WallBlock(collidingCell.getX(), collidingCell.getY(),collidingCell.getCell().getTile().getId(), tileMap.getWorldType());		
		tileMap.getBlocks().add(wallBlock);
		stage.addActor(wallBlock);
		float yWallBlock = wallBlock.getY();
		ChangeCellValueAction changeCellValueAction = new ChangeCellValueAction(tileMap, collidingCell.getX(), collidingCell.getY(), TileIdConstants.INVISIBLE_BLOCK);
		Action moveUpAction1 = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock + 0.1f, 0f);
		Action moveUpAction2 = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock + 0.4f, 0.08f);
		Action moveDownAction = ActionFacade.createMoveAction(wallBlock.getX(), yWallBlock, 0.08f);
		
		int replacingTileValue = wallBlock.getTileId();
		
		if (specialWallBlock!=null) {
								
			if (specialWallBlock.getItemEnum()==ItemEnum.COINS_10 && specialWallBlock.getCoins() > 0 ) {				
				GameManager.getGameManager().addCoin();
				specialWallBlock.removeCoin();				
				AbstractSfxSprite sprite = new EjectedCoin(specialWallBlock.getX(), specialWallBlock.getY()+1);									
				tileMap.getSfxSprites().add(sprite);
				stage.addActor(sprite);				   				
				sprite.addAppearAction();		
				if (specialWallBlock.getCoins()==0) {
					replacingTileValue = specialWallBlock.getReplacingTileValue();
				}											
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);
			} else {
				super.addItemFromBlock(tileMap, stage, specialWallBlock);
				replacingTileValue = specialWallBlock.getReplacingTileValue();
			}			
						
		}
		
		ChangeCellValueAction changeCellValueAction2 = new ChangeCellValueAction(tileMap, collidingCell.getX(), collidingCell.getY(), replacingTileValue);
		DeleteBlocSpriteAction deleteWallAction = new DeleteBlocSpriteAction(wallBlock);
		SequenceAction sequenceAction = new SequenceAction(moveUpAction1, changeCellValueAction, moveUpAction2, moveDownAction);
		sequenceAction.addAction(changeCellValueAction2);
		sequenceAction.addAction(moveUpAction1);
		sequenceAction.addAction(deleteWallAction);
		wallBlock.addAction(sequenceAction);		
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_BUMP);		
	}
		
			
}
