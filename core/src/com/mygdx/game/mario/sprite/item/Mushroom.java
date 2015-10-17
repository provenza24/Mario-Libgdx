package com.mygdx.game.mario.sprite.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.action.ActionFacade;
import com.mygdx.game.mario.sprite.AbstractItem;

public class Mushroom extends AbstractItem {

	public Mushroom(float x, float y) {
		super(x, y);		
		acceleration.x = -2.5f;		
	}

	@Override
	public void initializeAnimations() {
		spriteSheet = new Texture(Gdx.files.internal("sprites/mushroom.png"));
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 1, spriteSheet.getHeight() / 1);
		TextureRegion[] animationFrames = new TextureRegion[1];
		animationFrames[0] = tmp[0][0];
		currentAnimation = new Animation(0, animationFrames);
	}

	@Override
	public void addAppearAction() {
		addAction(ActionFacade.createMoveAction(getX(), getY()+1, 0.5f));		
	}
		
}
