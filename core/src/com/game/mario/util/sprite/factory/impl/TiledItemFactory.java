package com.game.mario.util.sprite.factory.impl;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.item.Flag;
import com.game.mario.sprite.tileobject.item.TransferItemDown;
import com.game.mario.sprite.tileobject.item.TransferItemRight;
import com.game.mario.sprite.tileobject.item.plateform.AscendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.DescendingMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.HorizontalMetalPlateform;
import com.game.mario.sprite.tileobject.item.plateform.VerticalMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.util.sprite.factory.AbstractTiledItemFactory;

public class TiledItemFactory extends AbstractTiledItemFactory {

	public TiledItemFactory() {	
	}

	@Override
	public AbstractTileObjectSprite create(MapObject mapObject, WorldTypeEnum worldType, Mario mario) {
		
		AbstractTileObjectSprite item = null;
		
		MapProperties mapProperties = mapObject.getProperties();
		String type = (String)mapProperties.get("type");
		
		if (type.equals("transferDown")) {				
			item = new TransferItemDown(mapObject);
		}
		if (type.equals("transferRight")) {							
			item = new TransferItemRight(mapObject);
		}
		if (type.equals("coin")) {							
			item = new Coin(mapObject);
		}
		if (type.equals("flag")) {							
			item = new Flag(mapObject, worldType);
		}
		
		if (type.equals("metalPlateform")) {
			
			String mode = mapProperties.get("mode").toString();
			DirectionEnum direction = DirectionEnum.valueOf(mapProperties.get("direction").toString().toUpperCase());			
			if (mode.equals("infinite")) {
				if (direction==DirectionEnum.UP) {
					item = new AscendingMetalPlateform(mapObject);	
				} else {
					item = new DescendingMetalPlateform(mapObject);					
				}									
			} else if (mode.equals("predifined")) {
				if (direction==DirectionEnum.UP || direction==DirectionEnum.DOWN) {
					item = new VerticalMetalPlateform(mapObject);					
				} else {
					item = new HorizontalMetalPlateform(mapObject);					
				}
			}
		}
		
		if (item==null) {
			System.out.println("null");
		}
		return item;
		
	}

}
