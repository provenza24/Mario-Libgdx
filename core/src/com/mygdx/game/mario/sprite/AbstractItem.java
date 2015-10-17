package com.mygdx.game.mario.sprite;

public abstract class AbstractItem extends AbstractSprite {

	public AbstractItem(float x, float y) {
		super(x, y);		
		alive = true;
		moveable = true;
		collidableWithTilemap = true;
		gravitating = true;
	}
	
	public abstract void addAppearAction();


}
