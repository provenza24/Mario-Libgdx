package com.game.mario.sprite.tileobject.enemy;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.mario.action.ActionFacade;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.AbstractTileObjectEnemy;
import com.game.mario.util.ResourcesLoader;
import com.game.mario.util.animation.AnimationBuilder;

public class Goomba extends AbstractTileObjectEnemy {

	protected static final Map<WorldTypeEnum, Texture> TEXTURES = new HashMap<WorldTypeEnum, Texture>();
	
	static {
		TEXTURES.put(WorldTypeEnum.OVERGROUND, ResourcesLoader.GOOMBA_OVERWORLD);
		TEXTURES.put(WorldTypeEnum.UNDERGROUND, ResourcesLoader.GOOMBA_UNDERWORLD);
	}
	
	private Animation walkAnimation;
	
	private Animation bumpAnimation;
		
	public Goomba(MapObject mapObject, WorldTypeEnum backgroundTypeEnum) {
		
		super(mapObject, new Vector2(0.2f, 0.1f));															
		acceleration.x = -1.9f; 				
		initializeAnimations(backgroundTypeEnum);				
	}		
	
	public void kill() {
		super.kill();
		acceleration.x = 0;
		this.currentAnimation = killedAnimation;
		addAction(ActionFacade.createMoveAction(getX(), getY(), 1f));
	}
	
	public void bump() {
		if (!isBumped()) {
			super.bump();			
			collidableWithTilemap = false;
			this.currentAnimation = bumpAnimation;
			acceleration.x = acceleration.x>0 ? 3 : -3;
			acceleration.y = 0.15f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
		} 		
	}		

	@Override
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.GOOMBA;
	}

	@Override
	public void initializeAnimations() {				
		
	}
	
	public void initializeAnimations(WorldTypeEnum backgroundTypeEnum) {

		spriteSheet = TEXTURES.get(backgroundTypeEnum);				
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);		
		
		walkAnimation = AnimationBuilder.getInstance().build(tmp, 0, 2, 0.15f);
		killedAnimation = AnimationBuilder.getInstance().build(tmp, 2, 1, 0.15f);
		bumpAnimation = AnimationBuilder.getInstance().build(tmp, 3, 1, 0.15f);
		
		currentAnimation = walkAnimation;
	}

}
