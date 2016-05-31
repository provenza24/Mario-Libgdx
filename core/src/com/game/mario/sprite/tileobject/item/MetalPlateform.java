package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.ResourcesLoader;

public class MetalPlateform extends AbstractTileObjectSprite {

	public MetalPlateform(MapObject mapObject) {
		super(mapObject);
		setSize(3, 0.5f);
		setRenderingSize(3, 0.5f);
		gravitating = false;
		collidableWithTilemap = false;
		acceleration.y = 0.05f;
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}	
	
	@Override
	public void initializeAnimations() {
		
		spriteSheet = ResourcesLoader.METAL_PLATEFORM;
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);						
	}




	@Override
	public void update(TmxMap tileMap, OrthographicCamera camera, float deltaTime) {		
		super.update(tileMap, camera, deltaTime);		
		if (getY() >= 15) {
			setY(-1);
			updateBounds();
		}
	}

}
