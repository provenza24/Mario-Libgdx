package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.ImageLoader;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public class Coin extends AbstractTileObjectSprite {

	public Coin(MapObject mapObject) {
		
		super(mapObject);												
		collidableWithTilemap = false;
		gravitating = false;		
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
			
	@Override
	public void initializeAnimations()  {		
		spriteSheet = ImageLoader.COIN;				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);				
		TextureRegion[] coinFrames = new TextureRegion[4];
		coinFrames[0] = tmp[0][0];
		coinFrames[1] = tmp[0][1];
		coinFrames[2] = tmp[0][2];
		coinFrames[3] = tmp[0][3];
		currentAnimation = new Animation(0.15f, coinFrames);				
	}
}