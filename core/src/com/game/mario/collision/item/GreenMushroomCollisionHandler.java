package com.game.mario.collision.item;

import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;

public class GreenMushroomCollisionHandler extends AbstractMushroomCollisionHandler {
	
	public GreenMushroomCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {		
		// Delete the sprite
		super.collide(mario, item, camera, scrollingBackgrounds);
		// Add a life to Mario
		GameManager.getGameManager().addLife();
		// Play correspondong sound
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);					
	}
	
}
