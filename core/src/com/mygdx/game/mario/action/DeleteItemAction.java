package com.mygdx.game.mario.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.mygdx.game.mario.sprite.AbstractSprite;

public class DeleteItemAction extends Action {

	private AbstractSprite sprite;

	public DeleteItemAction(AbstractSprite sprite) {
		this.sprite = sprite;		
	}

	@Override
	public boolean act(float delta) {
		sprite.setDeletable(true);	
		return true;
	}

}
