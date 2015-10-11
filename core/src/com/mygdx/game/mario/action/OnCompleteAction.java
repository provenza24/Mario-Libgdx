package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.impl.MysteryBlock;
import com.mygdx.game.mario.tilemap.TmxMap;

public class OnCompleteAction extends Action{

	private MysteryBlock block;
	
	private TmxMap tmxMap;
	
	private int x;
	
	private int y;
	
	private int value;
	
	public OnCompleteAction(TmxMap tmxMap, MysteryBlock block, int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.tmxMap = tmxMap;
		this.block = block;
	}
	
	Action completeAction = new Action(){
	    public boolean act( float delta ) {
	        // Do your stuff
	    	tmxMap.changeCellValue(x, y, value);
	        return true;
	    }
	};

	@Override
	public boolean act(float delta) { 
		block.setPosition(-5, 0);
		tmxMap.changeCellValue(x, y, value);
		return true;
	}
	
}
