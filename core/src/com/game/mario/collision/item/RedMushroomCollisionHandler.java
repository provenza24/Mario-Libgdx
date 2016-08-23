package com.game.mario.collision.item;

import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class RedMushroomCollisionHandler extends AbstractMushroomCollisionHandler {
	
	public RedMushroomCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		// Remove the sprite
		super.collide(mario, item, camera, scrollingBackgrounds);
		if (mario.getSizeState()<=1) {
			// Mario becomes big if he's small, the grow up animation is played
			mario.setGrowingUp(true);						
			mario.changeSizeState(2);
			mario.setGrowUpAnimation();
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);			
		}
	}

}
