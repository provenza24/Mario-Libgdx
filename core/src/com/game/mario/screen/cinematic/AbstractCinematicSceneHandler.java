package com.game.mario.screen.cinematic;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractCinematicSceneHandler {

	protected Mario mario;
	
	protected TmxMap tileMap;
	
	protected GameCamera camera;
	
	protected IScrollingBackground scrollingBackground;

	protected BitmapFont font;

	protected SpriteBatch spriteBatch;
	
	protected OrthogonalTiledMapRenderer renderer;
	
	protected Stage stage;
	
	protected Batch batch;
	
	public AbstractCinematicSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			IScrollingBackground scrollingBackground, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super();
		this.mario = mario;
		this.tileMap = tileMap;
		this.camera = camera;
		this.scrollingBackground = scrollingBackground;
		this.font = font;
		this.spriteBatch = spriteBatch;
		this.renderer = renderer;
		this.stage = stage;
		this.batch = batch;
	}

	public abstract void handleScene(float delta);
	
	protected void renderCinematicScene(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		scrollingBackground.render();
		renderer.setView(camera.getCamera());
		renderer.render();
		renderMysteryBlocks(delta);
		for (AbstractSprite enemy : tileMap.getEnemies()) {
			if (enemy.isVisible()) {
				enemy.render(renderer.getBatch());
			}				
		}						
		for (AbstractSprite item : tileMap.getItems()) {
			if (item.isVisible()) {
				item.update(tileMap, camera.getCamera(), delta);
				item.render(renderer.getBatch());
			}				
		}
		renderStatusBar();
		mario.render(renderer.getBatch());
		stage.act();
		stage.draw();
	}
	
	protected void renderStatusBar() {
		spriteBatch.begin();		
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbLifes(), 40, 470);
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbCoins(), 115, 470);		
		spriteBatch.end();
	}
	
	protected void renderMysteryBlocks(float delta) {

		// Get blocks from tilemap
		List<Block> blocks = tileMap.getBlocks();
		if (blocks.size() > 0) {
			MysteryBlock.updateStateTime(delta);
			batch = renderer.getBatch();
			batch.begin();					
			for (int i = 0; i < blocks.size(); i++) {
				// For each block
				Block block = blocks.get(i);
				block.update(tileMap, camera.getCamera(), delta);
				if (block.isDeletable()) {					
					blocks.remove(i--);
				} else if (block.isVisible()) {
					batch.draw(block.getCurrentFrame(), block.getX(), block.getY(), 1, 1);	
				}				
			}
			batch.end();
		}
	}

}
