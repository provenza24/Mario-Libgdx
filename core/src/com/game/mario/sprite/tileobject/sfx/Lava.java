package com.game.mario.sprite.tileobject.sfx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.util.ResourcesLoader;

public class Lava extends AbstractTileObjectSprite {

	public Lava(MapObject mapObject) {		
		super(mapObject, new Vector2());		
		collidableWithTilemap = false;
		gravitating = false;		
		moveable = false;			
	}
	
	@Override
	public void initializeAnimations() {
		
		spriteSheet = ResourcesLoader.CASTLE_LAVA;				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 8, spriteSheet.getHeight() / 1);				
		TextureRegion[] frames = new TextureRegion[8];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[0][2];
		frames[3] = tmp[0][3];	
		frames[4] = tmp[0][4];	
		frames[5] = tmp[0][5];	
		frames[6] = tmp[0][6];	
		frames[7] = tmp[0][7];	
		currentAnimation = new Animation(0.1f, frames);			
	}

	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(blocStateTime, true);		
	}
	
}
