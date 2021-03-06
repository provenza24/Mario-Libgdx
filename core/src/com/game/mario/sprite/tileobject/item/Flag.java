package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.enums.ItemEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectItem;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Flag extends AbstractTileObjectItem {

	private float flagTargetPosition;
	
	private WorldTypeEnum worldTypeEnum;
	
	public Flag(MapObject mapObject, WorldTypeEnum worldType) {
		
		super(mapObject, new Vector2());			
		collidableWithTilemap = false;
		gravitating = false;				
		flagTargetPosition = getX() - getWidth() /2;
		worldTypeEnum = worldType;
		initializeAnimations(worldType);		
	}
				
	public void initializeAnimations(WorldTypeEnum worldType)  {		
		spriteSheet = worldType.equals(WorldTypeEnum.CASTLE) ?  ResourcesLoader.HAWK : ResourcesLoader.FLAG;	
		currentAnimation = AnimationBuilder.getInstance().build(spriteSheet, 0, 3, 0.15f);					
	}

	public float getFlagTargetPosition() {
		return flagTargetPosition;
	}
	
	@Override
	public ItemEnum getType() {		
		return worldTypeEnum==WorldTypeEnum.CASTLE ? ItemEnum.HAWK : ItemEnum.FLAG;
	}
	
	@Override
	public void initializeAnimations()  {		
	
	}

}
