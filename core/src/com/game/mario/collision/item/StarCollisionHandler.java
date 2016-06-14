package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class StarCollisionHandler extends AbstractItemCollisionHandler {
	
	public StarCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {		
		super.collide(mario, item, camera, scrollingBackgrounds);	
		mario.setInvincible(true);
		mario.setOwningStar(true);
		mario.changeStarAnimation();
		mario.setInvincibleDurationTarget(10);
		
		SoundManager.getSoundManager().stopMusic();
		SoundManager.getSoundManager().setCurrentMusic(SoundManager.SOUND_INVINCIBLE);
		SoundManager.getSoundManager().playMusic(false);
	}
	
	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		// No bumping
	}

}
