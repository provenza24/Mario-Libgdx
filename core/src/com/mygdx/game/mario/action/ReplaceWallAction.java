package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.tilemap.TmxMap;

public class ReplaceWallAction extends Action{

	private Block block;
	
	private TmxMap tmxMap;
	
	public ReplaceWallAction(TmxMap tmxMap, Block block) {
		this.tmxMap = tmxMap;
		this.block = block;
	}
	
	@Override
	public boolean act(float delta) { 		
		tmxMap.changeCellValue((int)block.getX(), (int)block.getY(), block.getReplacingTileValue());
		block.setPosition(-5, 0);
		return true;
	}
	
}
