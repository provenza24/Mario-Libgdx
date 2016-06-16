package com.game.mario.sprite.tileobject.item;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Coin extends AbstractTileObjectSprite {

	public Coin(MapObject mapObject) {
		
		super(mapObject, new Vector2());												
		collidableWithTilemap = false;
		gravitating = false;		
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
			
	@Override
	public void initializeAnimations()  {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.COIN, 0, 4, 0.15f);					
	}
	
	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(commonStateTime, true);		
	}
}
