package com.game.mario.sprite.tileobject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.AbstractItem;

public abstract class AbstractTileObjectItem extends AbstractItem {
			
	public AbstractTileObjectItem(MapObject mapObject, Vector2 offset) {
		
		super((Float) mapObject.getProperties().get("x")/32, (float)(((Float) mapObject.getProperties().get("y")) /32));
		
		this.offset.x = offset.x;
		this.offset.y = offset.y;
						
		float spriteWidth = (Float) mapObject.getProperties().get("width")/32;
		float spriteHeight = (Float) mapObject.getProperties().get("height")/32;
		
		setSize(spriteWidth - offset.x * 2, spriteHeight - offset.y);
		setRenderingSize(spriteWidth, spriteHeight);		
		setY(getY()+getHeight());
		bounds=new Rectangle(getX() + offset.x, getY(), getWidth(), getHeight());
		
		String xAliveString = (String) mapObject.getProperties().get("xAlive");
		xAlive = xAliveString!=null ? Float.parseFloat(xAliveString) / 32 : getX() - 16 ;			
	}
	
	@Override
	public void addAppearAction() {		
	}
}
