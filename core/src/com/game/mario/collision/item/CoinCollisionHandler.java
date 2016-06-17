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
		super.collide(mario, item, camera, scrollingBackgrounds);
		GameManager.getGameManager().addCoin();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);		
	}

	@Override
	public void bump(Stage stage, TmxMap tileMap, AbstractSprite item) {		
		AbstractSfxSprite ejectedCoin = new EjectedCoin(item.getX(), item.getY());
		GameManager.getGameManager().addCoin();
		item.setDeletable(true);
		tileMap.getSfxSprites().add(ejectedCoin);
		stage.addActor(ejectedCoin);
		ejectedCoin.addAppearAction();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_COIN);
	}

}
