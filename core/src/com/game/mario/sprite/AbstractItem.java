package com.game.mario.sprite;

import com.badlogic.gdx.math.Vector2;
import com.game.mario.action.ActionFacade;
import com.game.mario.enums.ItemEnum;

public abstract class AbstractItem extends AbstractSprite implements IAppearable {

	public AbstractItem(float x, float y, Vector2 size, Vector2 offset) {
		super(x, y, size, offset);		
		moveable = true;
		collidableWithTilemap = true;
		gravitating = true;
		deletableOutScreenRight = false;
	}
	
	public AbstractItem(float x, float y) {
		this(x, y, new Vector2(1,1), new Vector2());
	}

	public abstract ItemEnum getType();
	
	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+0.9f, 0.5f));
	}

}
