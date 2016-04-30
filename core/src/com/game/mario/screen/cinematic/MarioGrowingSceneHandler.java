package com.game.mario.screen.cinematic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class MarioGrowingSceneHandler extends AbstractCinematicSceneHandler {

	private float growingDuration = 0;	
	
	public MarioGrowingSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			IScrollingBackground scrollingBackground, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);
	}

	public void handleScene(float delta) {

		growingDuration = growingDuration + delta;
		mario.updateCinematicAnimation(delta);
		renderCinematicScene(delta);				
		if (growingDuration>=1) {
			growingDuration = 0;					
			if (mario.isGrowingDown()) {
				mario.changeSizeState(0);				
				mario.setGrowingDown(false);
				mario.setInvincible(true);
			} else if (mario.isGrowingUp()) {												
				mario.setGrowingUp(false);
			}
		}
		
	}
	
}
