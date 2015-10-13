package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.tilemap.TmxMap;

public class ChangeCellValueAction extends Action {

	private TmxMap tmxMap;
	
	private int x;
	
	private int y;
	
	private int value;

	public ChangeCellValueAction(TmxMap tmxMap, int x, int y, int value) {		
		this.tmxMap = tmxMap;
		this.x = x;
		this.y = y;
		this.value = value;
	}

	@Override
	public boolean act(float delta) {		
		tmxMap.changeCellValue(x, y, value);
		return true;
	}

}
