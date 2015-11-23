package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.util.ResourcesLoader;

public class Flag extends AbstractTileObjectSprite {

	private float flagTargetPosition;
	
	public Flag(MapObject mapObject) {
		
		super(mapObject);	
		collidableWithTilemap = false;
		gravitating = false;				
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		flagTargetPosition = getX() - getWidth() /2;
	}
			
	@Override
	public void initializeAnimations()  {		
		spriteSheet = ResourcesLoader.FLAG;			
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);				
		TextureRegion[] flagFrames = new TextureRegion[3];
		flagFrames[0] = tmp[0][0];
		flagFrames[1] = tmp[0][1];
		flagFrames[2] = tmp[0][2];		
		currentAnimation = new Animation(0.15f, flagFrames);				
	}

	public float getFlagTargetPosition() {
		return flagTargetPosition;
	}
}
