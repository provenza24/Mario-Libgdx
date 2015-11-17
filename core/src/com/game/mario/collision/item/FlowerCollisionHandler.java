package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.ResourcesLoader;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class FlowerCollisionHandler extends AbstractItemCollisionHandler {
	
	public FlowerCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera) {		
		super.collide(mario, item, camera);
		if (mario.getSizeState()==0) {
			mario.setGrowingUp(true);			
			mario.setGrowUpAnimation();
			mario.changeSizeState(1);
			ResourcesLoader.SOUND_POWERUP.play();
		} else {
			mario.changeSizeState(2);
			ResourcesLoader.SOUND_POWERUP.play();
		}
	}
	
	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {
		// No bumping
	}

}
