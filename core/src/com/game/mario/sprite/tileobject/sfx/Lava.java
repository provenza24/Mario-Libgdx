package com.game.mario.sprite.tileobject.sfx;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.AbstractTileObjectSprite;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Lava extends AbstractTileObjectSprite {

	public Lava(MapObject mapObject) {		
		super(mapObject, new Vector2());		
		collidableWithTilemap = false;
		gravitating = false;		
		moveable = false;			
	}
	
	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.CASTLE_LAVA, 0, 8, 0.1f);					
	}

	protected void updateAnimation(float delta) {				
		currentFrame = currentAnimation.getKeyFrame(commonStateTime, true);		
	}
	
}
