package com.mygdx.game.mario.sprite.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.sprite.GameSprite;
import com.mygdx.game.mario.tilemap.TmxMap;

public class Goomba extends GameSprite {

	private Animation walkAnimation;
	
	public Goomba(float x, float y) {
		super(x/32, (y/32)+1);	
		spriteSheet = new Texture(Gdx.files.internal("sprites/goomba.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);
		
		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkAnimation = new Animation(0.15f, walkFrames);
		
		currentAnimation = walkAnimation;		
		stateTime = 0f;
	}

	@Override
	public void move(float deltaTime) {		
	}

	@Override
	public void collideWithTilemap(TmxMap tileMap) {
		
	}

}
