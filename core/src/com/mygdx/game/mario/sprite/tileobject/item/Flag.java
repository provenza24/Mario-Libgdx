package com.mygdx.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.mario.sprite.tileobject.AbstractTileObjectSprite;

public class Flag extends AbstractTileObjectSprite {

	public Flag(MapObject mapObject) {
		
		super(mapObject);	
		collidableWithTilemap = false;
		gravitating = false;				
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
			
	@Override
	public void initializeAnimations()  {		
		spriteSheet = new Texture(Gdx.files.internal("sprites/flag.png"));				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);				
		TextureRegion[] flagFrames = new TextureRegion[3];
		flagFrames[0] = tmp[0][0];
		flagFrames[1] = tmp[0][1];
		flagFrames[2] = tmp[0][2];		
		currentAnimation = new Animation(0.15f, flagFrames);				
	}
}
