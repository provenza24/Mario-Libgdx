package com.game.mario.sprite;

public abstract class AbstractSfxSprite extends AbstractSprite implements IAppearable {

	public AbstractSfxSprite(float x, float y) {
		super(x, y);		
		alive = true;				
		moveable = false;
		collidableWithTilemap = false;
		gravitating = false; 
	}
	
	public abstract void addAppearAction();
}
