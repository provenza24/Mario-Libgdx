package com.game.mario.sprite.tileobject.item.plateform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.game.mario.tilemap.TmxMap;

public class AscendingMetalPlateform extends AbstractMetalPlateform {

	public AscendingMetalPlateform(MapObject mapObject) {
		super(mapObject);
		acceleration.y = 0.05f;
	}
	
	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		super.update(tileMap, camera, deltaTime);		
		if (getY() >= 15) {
			setY(-2);
			updateBounds();
		}
	}


}
