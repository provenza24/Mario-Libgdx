package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class RedMushroomCollisionHandler extends AbstractItemCollisionHandler {
	
	public RedMushroomCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, IScrollingBackground scrollingBackground) {		
		super.collide(mario, item, camera, scrollingBackground);
		if (mario.getSizeState()==0) {
			mario.setGrowingUp(true);			
			mario.setGrowUpAnimation();
			mario.changeSizeState(1);
			SoundManager.getSoundManager().playSound(SoundManager.SOUND_POWERUP);			
		}
	}
	
	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		item.getAcceleration().x = -item.getAcceleration().x;
		item.getAcceleration().y = 0.1f;
	}

}
