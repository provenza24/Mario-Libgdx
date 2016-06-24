package com.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.game.mario.sprite.bloc.AbstractBlock;
import com.game.mario.tilemap.TmxMap;

public class ReplaceWallAction extends Action{

	private AbstractBlock block;
	
	private TmxMap tmxMap;
	
	public ReplaceWallAction(TmxMap tmxMap, AbstractBlock block) {
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
