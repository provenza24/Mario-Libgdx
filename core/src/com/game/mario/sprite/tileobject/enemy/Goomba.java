package com.game.mario.sprite.tileobject.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.game.mario.action.ActionFacade;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.util.ResourcesLoader;

public class Goomba extends AbstractEnemy {

	private Animation walkAnimation;
	
	private Animation bumpAnimation;
		
	public Goomba(MapObject mapObject) {
		
		super(mapObject);								
		offset.x = 0.2f;
		setSize(1-offset.x*2, 1);		
		currentAnimation = walkAnimation;				
		acceleration.x = -1.9f;
		gravitating = true;		
		bounds=new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
			
	@Override
	public void initializeAnimations()  {
		spriteSheet = ResourcesLoader.GOOMBA;		
		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 4, spriteSheet.getHeight() / 1);		
		
		TextureRegion[] walkFrames = new TextureRegion[2];
		walkFrames[0] = tmp[0][0];
		walkFrames[1] = tmp[0][1];
		walkAnimation = new Animation(0.15f, walkFrames);
		
		TextureRegion[] killedFrames = new TextureRegion[1];
		killedFrames[0] = tmp[0][2];
		killedAnimation = new Animation(0, killedFrames);		
		
		TextureRegion[] bumpFrames = new TextureRegion[1];
		bumpFrames[0] = tmp[0][3];
		bumpAnimation = new Animation(0, bumpFrames);
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
			acceleration.y = 0.1f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
		} 		
	}

	@Override
	public EnemyTypeEnum getEnemyType() {		
		return EnemyTypeEnum.GOOMBA;
	}

}
