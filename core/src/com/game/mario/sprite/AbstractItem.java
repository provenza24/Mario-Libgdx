package com.game.mario.sprite;

import com.game.mario.enums.ItemEnum;

public abstract class AbstractItem extends AbstractSprite implements IAppearable {

	public AbstractItem(float x, float y) {
		super(x, y);		
		alive = true;
		moveable = true;
		collidableWithTilemap = true;
		gravitating = true;
	}
	
	public abstract void addAppearAction();

	public abstract ItemEnum getType();

}
