package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.impl.Block;
import com.mygdx.game.mario.tilemap.TmxMap;

public class DeleteWallAction extends Action {

	private TmxMap tmxMap;

	private Block block;

	public DeleteWallAction(TmxMap tmxMap, Block block) {
		this.block = block;
		this.tmxMap = tmxMap;
	}

	@Override
	public boolean act(float delta) {
		int x = (int) block.getX();
		int y = (int) block.getY();
		block.setPosition(-5, 0);
		tmxMap.changeCellValue(x, y, block.getTileId());
		return true;
	}

}
