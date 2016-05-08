package com.game.mario.collision.item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.item.EjectedCoin;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class CoinCollisionHandler extends AbstractItemCollisionHandler {

	public CoinCollisionHandler() {		
	}

	@Override
	public void collide(Mario mario, AbstractSprite item, GameCamera camera, IScrollingBackground scrollingBackground) {		
		super.collide(mario, item, camera, scrollingBackground);
		GameManager.getGameManager().addCoin();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);		
	}

	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {		
		AbstractItem ejectedCoin = new EjectedCoin(item.getX(), item.getY());
		GameManager.getGameManager().addCoin();
		item.setDeletable(true);
		tileMap.getItems().add(ejectedCoin);
		stage.addActor(ejectedCoin);
		ejectedCoin.addAppearAction();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);
	}

}
