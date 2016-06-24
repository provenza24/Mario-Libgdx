package com.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.game.mario.sprite.bloc.AbstractBlock;

public class DeleteBlocSpriteAction extends Action {

	private AbstractBlock block;

	public DeleteBlocSpriteAction(AbstractBlock block) {
		this.block = block;		
	}

	@Override
	public boolean act(float delta) {
			block.setPosition(-5, 0);		
		return true;
	}

}
