package com.game.mario.screen.cinematic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class TransferSceneHandler extends AbstractCinematicSceneHandler {

	private float timer = 0;

	public TransferSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			Array<IScrollingBackground> scrollingBackgrounds, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super(mario, tileMap, camera, scrollingBackgrounds, font, spriteBatch, renderer, stage, batch);			
	}

	public void handleScene(float delta) {
		
		mario.act(delta);
		mario.updateCinematicAnimation(delta);
		renderCinematicScene(delta);
		
		renderer.getBatch().begin();				
		renderer.getBatch().draw(mario.getTransferItem().getPipe(), mario.getTransferItem().getPipe().getX(), mario.getTransferItem().getPipe().getY(), 2,2);	
		renderer.getBatch().end();
				
		timer += delta;
		
		if (timer>2f) {
			mario.getActions().removeAll(mario.getActions(), true);
			mario.setInTransfer(false);
			mario.transfer(tileMap, camera, scrollingBackgrounds, spriteBatch);
			timer = 0;			
		}
	}
	
}
