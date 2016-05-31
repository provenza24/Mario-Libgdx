package com.game.mario.sprite.item;

import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractItem;

public abstract class AbstractMushroom extends AbstractItem {

	public AbstractMushroom(float x, float y) {
		super(x, y);
		offset.x = 0.1f;
		offset.y = 0.1f;
		setSize(1 - 2*offset.x, 1f - offset.y);
		acceleration.x = 4f;		
	}
	
	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));
	}

}
