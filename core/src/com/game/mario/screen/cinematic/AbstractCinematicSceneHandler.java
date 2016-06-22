package com.game.mario.screen.cinematic;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public abstract class AbstractCinematicSceneHandler {

	protected Mario mario;
	
	protected TmxMap tileMap;
	
	protected GameCamera camera;
	
	protected Array<IScrollingBackground> scrollingBackgrounds;
	
	protected BitmapFont font;

	protected SpriteBatch spriteBatch;
	
	protected OrthogonalTiledMapRenderer renderer;
	
	protected Stage stage;
	
	protected Batch batch;
	
	protected boolean updatePlateforms;
	
	protected boolean updateItems;
	
	protected boolean updateEnemies;
	
	protected boolean updateSfxSprites;
	
	public AbstractCinematicSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			 Array<IScrollingBackground> backgrounds, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super();
		this.mario = mario;
		this.tileMap = tileMap;
		this.camera = camera;
		this.scrollingBackgrounds = backgrounds;
		this.font = font;
		this.spriteBatch = spriteBatch;
		this.renderer = renderer;
		this.stage = stage;
		this.batch = batch;
		this.updateItems = true;	
		this.updateEnemies = false;
		this.updatePlateforms = false;
		this.updateSfxSprites = true;
	}

	public abstract void handleScene(float delta);		
	
	protected void renderCinematicScene(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		AbstractSprite.updateCommonStateTime(delta);
		
		renderBackgrounds();		
		renderer.setView(camera.getCamera());
		renderer.render();
		renderMysteryBlocks(delta);							
		renderItems(delta);
		renderPlateforms(delta);
		renderEnemies(delta);
		mario.render(renderer.getBatch());
		renderSfxSprites(delta);		
		renderStatusBar();		
		stage.act();
		stage.draw();
	}

	protected void renderBackgrounds() {
		scrollingBackgrounds.get(0).render();
		if (scrollingBackgrounds.size>1) {
			scrollingBackgrounds.get(1).render();
		}
	}

	private void renderSfxSprites(float delta) {
		for (AbstractSprite sfxSprites : tileMap.getSfxSprites()) {			
			if (sfxSprites.isVisible()) {							
				if (updateSfxSprites) {
					sfxSprites.update(tileMap, camera.getCamera(), delta);
				}
				sfxSprites.render(renderer.getBatch());
			}				
		}
	}

	protected void renderEnemies(float delta) {
		for (AbstractSprite enemy : tileMap.getEnemies()) {
			if (enemy.isVisible()) {
				if (updateEnemies) {
					enemy.update(tileMap, camera.getCamera(), delta);
				}
				enemy.render(renderer.getBatch());
			}				
		}
	}

	private void renderItems(float delta) {
		for (AbstractSprite item : tileMap.getItems()) {
			if (item.isVisible()) {	
				if (updateItems) {
					item.update(tileMap, camera.getCamera(), delta);
				}
				item.render(renderer.getBatch());
			}				
		}
	}
	
	private void renderPlateforms(float delta) {
		for (AbstractSprite plateform : tileMap.getPlateforms()) {	
			if (plateform.isVisible()) {
				if (updatePlateforms) {
					plateform.update(tileMap, camera.getCamera(), delta);
				}
				plateform.render(renderer.getBatch());
			}
				
		}
	}
	
	private void renderStatusBar() {
		spriteBatch.begin();		
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbLifes(), 40, Gdx.graphics.getHeight()-10);
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbCoins(), 115, Gdx.graphics.getHeight()-10);		
		spriteBatch.end();
	}
	
	private void renderMysteryBlocks(float delta) {

		// Get blocks from tilemap
		List<Block> blocks = tileMap.getBlocks();
		if (blocks.size() > 0) {
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
