package com.game.mario.collision.item;

import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class FireFlameCollisionHandler extends AbstractItemCollisionHandler {

	public FireFlameCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {		
			
		if (!mario.isInvincible()){							
			if (mario.getSizeState()>0) {								
				mario.setGrowingDown(true);
				mario.setGrowDownAnimation();
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);									
			} else {
				mario.setAlive(false);
				mario.setDeathAnimation();
				SoundManager.getSoundManager().stopMusic();
				SoundManager.getSoundManager().playSound(SoundManager.SOUND_MARIO_DEATH);									
			}
		}
	}
}
