package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.impl.MysteryBlock;
import com.mygdx.game.mario.tilemap.TmxMap;

public class OnCompleteAction extends Action{

	private MysteryBlock block;
	
	private TmxMap tmxMap;
	
	public OnCompleteAction(TmxMap tmxMap, MysteryBlock block) {
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
