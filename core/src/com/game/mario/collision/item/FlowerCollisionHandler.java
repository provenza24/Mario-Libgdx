package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class FlowerCollisionHandler extends AbstractItemCollisionHandler {
	
	public FlowerCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		// Delete the flower sprite
		super.collide(mario, item, camera, scrollingBackgrounds);
		if (mario.getSizeState()<=1) {
			// If Mario is small, Mario becomes big (play the grow up animation)
			mario.setGrowingUp(true);			
			mario.setGrowUpAnimation();
			mario.changeSizeState(2);
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);			
		} else {
			// Mario is already big, just play a sound and change its state 
			mario.changeSizeState(3);
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);			
		}
	}
	
	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		// No bumping
	}

}
