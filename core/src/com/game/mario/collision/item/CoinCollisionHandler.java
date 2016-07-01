package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.sfx.EjectedCoin;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class CoinCollisionHandler extends AbstractItemCollisionHandler {

	public CoinCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, Array<IScrollingBackground> scrollingBackgrounds) {
		// Delete coin sprite
		super.collide(mario, item, camera, scrollingBackgrounds);
		// Increase Mario coins
		GameManager.getGameManager().addCoin();
		// Play coin sound
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);		
	}

	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {		
		// Delete coin sprite			
		item.setDeletable(true);		
		// Increase Mario coins
		GameManager.getGameManager().addCoin();
		// Play coin sound
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);
		// Since Mario bumped the wall under the coin to get it, we play an small animation (coin explodes in the air)
		AbstractSfxSprite ejectedCoin = new EjectedCoin(item.getX(), item.getY());
		tileMap.getSfxSprites().add(ejectedCoin);
		stage.addActor(ejectedCoin);
		ejectedCoin.addAppearAction();		
	}

}
