package com.game.mario.sprite.tileobject.sfx;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.sprite.tileobject.AbstractTileObjectSfx;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class WhiteFlag extends AbstractTileObjectSfx {

	public WhiteFlag(MapObject mapObject) {		
		super(mapObject, new Vector2());		
	}
	
	@Override
	public void initializeAnimations() {		
		currentAnimation = AnimationBuilder.getInstance().build(ResourcesLoader.WHITE_FLAG, 0, 4, 0.15f);				
	}

	@Override
	public void addAppearAction() {					
	}
		
}
