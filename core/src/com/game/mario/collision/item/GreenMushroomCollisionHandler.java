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
		super.collide(mario, item, camera, scrollingBackgrounds);
		GameManager.getGameManager().addLife();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);					
	}
	
}
