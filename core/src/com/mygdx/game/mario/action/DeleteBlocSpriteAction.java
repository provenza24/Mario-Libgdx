package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.impl.Block;

public class DeleteBlocSpriteAction extends Action {

	private Block block;

	public DeleteBlocSpriteAction(Block block) {
		this.block = block;		
	}

	@Override
	public boolean act(float delta) {
			block.setPosition(-5, 0);		
		return true;
	}

}
