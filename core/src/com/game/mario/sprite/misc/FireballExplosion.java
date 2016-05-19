package com.game.mario.sprite.misc;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.util.ResourcesLoader;

public class FireballExplosion extends AbstractItem {
	
	public FireballExplosion(AbstractSprite fireball) {
		
		super(fireball.getX(), fireball.getY());		
		setSize(1,1);		
		renderingSize = new Vector2(1,1);
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
		alive = true;				
		moveable = false;
		collidableWithTilemap = false;
		gravitating = false; 
	}

	@Override
	public void initializeAnimations() {
		
		spriteSheet = ResourcesLoader.FIREBALL_EXPLOSION;				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 1);				
		TextureRegion[] frames = new TextureRegion[3];
		frames[0] = tmp[0][0];
		frames[1] = tmp[0][1];
		frames[2] = tmp[0][2];				
		currentAnimation = new Animation(0.05f, frames);			
		
	}

	@Override
	public void addAppearAction() {
		SequenceAction actions = new SequenceAction(				
				ActionFacade.createMoveAction(getX(), getY(), 0.15f),
				new DeleteItemAction(this));
		addAction(actions);		
	}	

}
