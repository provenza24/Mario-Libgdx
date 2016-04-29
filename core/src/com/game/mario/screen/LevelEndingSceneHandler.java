package com.game.mario.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.GameManager;
import com.game.mario.action.ActionFacade;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class LevelEndingSceneHandler extends AbstractCinematicSceneHandler {

	private float timer = 0;

	private int endLevelState = 0;
		
	public LevelEndingSceneHandler(Mario mario, TmxMap tileMap, GameCamera camera,
			IScrollingBackground scrollingBackground, BitmapFont font, SpriteBatch spriteBatch,
			OrthogonalTiledMapRenderer renderer, Stage stage, Batch batch) {
		super(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);
	}

	public void handleScene(float delta) {

		timer += delta;

		if (endLevelState == 0) {
			mario.setPosition(mario.getX() - 0.6f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioFlagRightAnimation());
			mario.setAcceleration(new Vector2());
			mario.setGravitating(false);
			endLevelState = 1;
			timer = 0;
		} else if (endLevelState == 1 && timer > 1) {
			timer = 0;
			mario.setPosition(mario.getX() + 0.85f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioFlagLeftAnimation());
			mario.setAcceleration(new Vector2());
			mario.setGravitating(true);
			endLevelState = 2;
			tileMap.getFlag().addAction(
					ActionFacade.createMoveAction(tileMap.getFlag().getX(), tileMap.getFlag().getY() - 8.5f, 1f));
		} else if (endLevelState == 2 && mario.getMapCollisionEvent().isCollidingBottom() && timer > 1) {
			timer = 0;
			endLevelState = 3;
			mario.setPosition(mario.getX() + 1f, mario.getY());
			mario.setCurrentAnimation(mario.getMarioRunRightAnimation());
			mario.setAcceleration(new Vector2(2f, 0));
		}

		if (endLevelState == 3) {
			if (mario.getX() > tileMap.getFlagTargetPosition() + 6.5f) {
				mario.setAcceleration(new Vector2());
				endLevelState = 4;
				timer = 0;
				mario.setCurrentAnimation(mario.getMarioVictoryAnimation());
			} else {
				camera.moveCamera(mario);
				// Move scrolling background
				if (Math.floor(camera.getCameraOffset()) == 8) {
					scrollingBackground.update();
				}
				scrollingBackground.render();
			}
		}

		if (endLevelState == 4 && timer > 3) {
			GameManager.getGameManager().setSizeState(mario.getSizeState());
			GameManager.getGameManager().nextLevel();
		} else {
			mario.move(delta);
			mario.collideWithTilemap(tileMap);
			mario.updateCinematicAnimation(delta);
			renderCinematicScene(delta);
		}
	}
	
}
