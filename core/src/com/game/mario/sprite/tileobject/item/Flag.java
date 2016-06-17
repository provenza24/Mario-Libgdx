package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.ItemEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Flag extends AbstractTileObjectItem {

	private float flagTargetPosition;
	
	public Flag(MapObject mapObject, WorldTypeEnum backgroundTypeEnum) {
		
		super(mapObject, new Vector2());	
		collidableWithTilemap = false;
		gravitating = false;				
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		flagTargetPosition = getX() - getWidth() /2;
		
		initializeAnimations(backgroundTypeEnum);
	}
				
	public void initializeAnimations(WorldTypeEnum backgroundTypeEnum)  {		
		spriteSheet = backgroundTypeEnum.equals(WorldTypeEnum.CASTLE) ?  ResourcesLoader.HAWK : ResourcesLoader.FLAG;	
		currentAnimation = AnimationBuilder.getInstance().build(spriteSheet, 0, 3, 0.15f);					
	}

	public float getFlagTargetPosition() {
		return flagTargetPosition;
	}
	
	@Override
	public void initializeAnimations()  {		
	
	}

	@Override
	public void addAppearAction() {	
	}

	@Override
	public ItemEnum getType() {		
		return null;
	}
}
