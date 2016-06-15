package com.game.mario.sprite;

import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.tileobject.mario.Mario;

public abstract class AbstractEnemy extends AbstractSprite {
			
	protected boolean killableByPlayer;
	
	protected boolean killableByFireball;
	
	public AbstractEnemy(float x, float y) {
		super(x, y);		
		state = SpriteMoveEnum.WALKING;
		killableByPlayer = true;
		killableByFireball = true;
	}
	
	public abstract EnemyTypeEnum getEnemyType();
	
	public void killByFireball(AbstractSprite fireball) {
		if (killableByFireball) {
			this.bump();
			acceleration.x = fireball.getAcceleration().x > 0 ? 3 : -3;
		}
	}		
	
	public void killByStar() {
		this.bump();		
	}
	
	public boolean collideMario(Mario mario) {
		
		boolean isEnemyHit = false;
		
		if (mario.isOwningStar()) {
			isEnemyHit = mario.isOwningStar();
			killByStar();
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);
		} else {
			isEnemyHit = isKillable() && mario.getY() > getY() && mario.getState() == SpriteMoveEnum.FALLING;
			if (isEnemyHit) {
				kill();
				mario.getAcceleration().y = 0.15f;
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_KICK);			
			}
		}
		
		 return isEnemyHit;
	}

	public boolean isKillable() {
		return killableByPlayer;
	}

	public void setKillable(boolean killable) {
		this.killableByPlayer = killable;
	}
		

}
