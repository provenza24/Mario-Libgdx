package com.game.mario.screen.cinematic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class MarioDeathSceneHandler extends AbstractCinematicSceneHandler {

	private boolean waitBeforeDeathAnimating = true;	
	
	public MarioDeathSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			Array<IScrollingBackground> scrollingBackgrounds, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super(mario, tileMap, camera, scrollingBackgrounds, font, spriteBatch, renderer, stage, batch);
	}

	public void handleScene(float delta) {

		GameManager.getGameManager().setSizeState(0);

		if (!waitBeforeDeathAnimating) {
			mario.move(delta);
		}
		renderCinematicScene(delta);
		if (waitBeforeDeathAnimating) {
			mario.setDeathNoMoveDuration(mario.getDeathNoMoveDuration() + delta);
			if (mario.getDeathNoMoveDuration() >= 1) {
				waitBeforeDeathAnimating = false;
			}
		}
		if (mario.getY() < -50) {
			GameManager.getGameManager().restartLevel();
		}

	}
	
	protected void renderItems(float delta) {
		for (AbstractSprite item : tileMap.getItems()) {
			if (item.isVisible()) {							
				item.render(renderer.getBatch());
			}				
		}
	}
	
}
