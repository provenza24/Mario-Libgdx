package com.game.mario.sprite;

import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteStateEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractEnemy extends AbstractSprite {
			
	protected boolean killable;
	
	public AbstractEnemy(float x, float y) {
		super(x, y);		
		state = SpriteStateEnum.WALKING;
		killable = true;
	}
	
	public abstract EnemyTypeEnum getEnemyType();
	
	public void killByFireball(AbstractSprite fireball) {
		this.bump();
		acceleration.x = fireball.getAcceleration().x > 0 ? 3 : -3;
	}		
	
	public boolean collideMario(Mario mario) {
		boolean isEnemyHit = isKillable() && mario.getY() > getY() && mario.getState() == SpriteStateEnum.FALLING;
		if (isEnemyHit) {
			kill();
			mario.getAcceleration().y = 0.15f;
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);			
		}
		 return isEnemyHit;
	}

	public boolean isKillable() {
		return killable;
	}

	public void setKillable(boolean killable) {
		this.killable = killable;
	}
		

}
