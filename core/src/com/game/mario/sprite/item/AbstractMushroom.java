package com.game.mario.sprite.item;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.action.ActionFacade;
import com.game.mario.sprite.AbstractItem;

public abstract class AbstractMushroom extends AbstractItem {

	public AbstractMushroom(float x, float y) {
		super(x, y, new Vector2(1, 1), new Vector2(0.1f, 0.1f));
		alive = true;				
		acceleration.x = 4f;				
	}
	
	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));
	}

}
