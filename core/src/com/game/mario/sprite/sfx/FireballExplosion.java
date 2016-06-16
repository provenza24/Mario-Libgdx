package com.game.mario.sprite.sfx;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.game.mario.action.ActionFacade;
import com.game.mario.action.DeleteItemAction;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

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
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.FIREBALL_EXPLOSION, 0, 3, 0.05f);						
	}

	@Override
	public void addAppearAction() {
		SequenceAction actions = new SequenceAction(				
				ActionFacade.createMoveAction(getX(), getY(), 0.15f),
				new DeleteItemAction(this));
		addAction(actions);		
	}	

}
