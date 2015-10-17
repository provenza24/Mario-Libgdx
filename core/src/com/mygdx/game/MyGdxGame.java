package com.mygdx.game;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.mario.background.IScrollingBackground;
import com.mygdx.game.mario.background.impl.LeftScrollingBackground;
import com.mygdx.game.mario.collision.CollisionHandler;
import com.mygdx.game.mario.enums.DirectionEnum;
import com.mygdx.game.mario.enums.MarioStateEnum;
import com.mygdx.game.mario.sprite.AbstractSprite;
import com.mygdx.game.mario.sprite.bloc.Block;
import com.mygdx.game.mario.sprite.tileobject.mario.Mario;
import com.mygdx.game.mario.tilemap.TmxMap;

public class MyGdxGame extends ApplicationAdapter {

	private boolean debugShowText = false;

	private boolean debugShowBounds = false;

	private boolean debugShowFps = false;

	private TmxMap tileMap;

	private OrthogonalTiledMapRenderer renderer;

	private OrthographicCamera camera;

	private Batch batch;

	private Mario mario;

	private float cameraOffset = 0;

	private BitmapFont font;

	private SpriteBatch spriteBatch;

	private ShapeRenderer shapeRenderer;

	private int jumpTimerMax = 20;

	private IScrollingBackground scrollingBackground;

	private boolean scrollable;

	private Stage stage;

	@Override
	public void create() {

		shapeRenderer = new ShapeRenderer();

		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		// font.setColor(0.5f,0.4f,0,1);
		font.setColor(0, 0, 1, 1);

		// load the map, set the unit scale to 1/32 (1 unit == 32 pixels)
		tileMap = new TmxMap("tilemaps/level_1_1.tmx");
		renderer = new OrthogonalTiledMapRenderer(tileMap.getMap(), 1 / 32f);

		mario = tileMap.getMario();

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 16, 15);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();
		cameraOffset = mario.getX();

		scrollingBackground = new LeftScrollingBackground(mario, spriteBatch, tileMap.getBackgroundType(), 16);
		scrollable = true;

		stage = new Stage();			
		for (Actor actor : tileMap.getBlocks()) {
			stage.addActor(actor);
		}
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();

		// Listen to keyboard actions and update Mario status
		handleInput();
		mario.update(tileMap, camera, delta);
		
		CollisionHandler.getCollisionHandler().collideMarioWithUpperBlock(mario, tileMap, stage);
		
		if (scrollable) {
			// Move camera
			moveCamera();
			// Move scrolling background
			if (Math.floor(cameraOffset) == 8) {
				scrollingBackground.update();
			}
			scrollingBackground.render();
		}
		
		// Render tilemap
		renderer.setView(camera);
		renderer.render();
		// Render mystery blocks
		renderMysteryBlocks(delta);
		// Render enemies
		handleEnemies(delta);
		//handleItems
		handleItems(delta);
		// Render Mario		
		mario.render(renderer.getBatch());
		// Render debug mode (press F1 to display/hide debug)
		renderDebugMode();

		//stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}

	private void renderDebugMode() {

		if (debugShowText) {
			// Mario information
			spriteBatch.begin();
			font.draw(spriteBatch,
					"mario.position=" + String.format("%.1f", mario.getX()) + "," + String.format("%.1f", mario.getY()),
					10, 460);
			font.draw(spriteBatch, "mario.acceleration=" + String.format("%.1f", mario.getAcceleration().x) + ","
					+ String.format("%.1f", mario.getAcceleration().y), 10, 440);
			font.draw(spriteBatch, "state=" + mario.getState().toString(), 10, 420);
			font.draw(spriteBatch, "direction=" + mario.getDirection().toString(), 10, 400);
			font.draw(spriteBatch, "jumptimer=" + mario.getJumpTimer(), 10, 380);
			font.draw(spriteBatch, "isOnFloor=" + mario.isOnFloor(), 10, 360);
			font.draw(spriteBatch, "camera.x=" + String.format("%.1f", camera.position.x) + " camera.offset="
					+ String.format("%.1f", cameraOffset), 10, 340);
			font.draw(spriteBatch,
					"tile-collision:  (right=" + mario.getMapCollisionEvent().isCollidingRight() + ", left="
							+ mario.getMapCollisionEvent().isCollidingLeft() + ", top="
							+ mario.getMapCollisionEvent().isCollidingTop() + ", bottom="
							+ mario.getMapCollisionEvent().isCollidingBottom() + ")",
					10, 320);
			font.draw(spriteBatch, "Mysteryblocks: " + tileMap.getBlocks().size(), 10, 300);
			font.draw(spriteBatch, "Enemies: " + tileMap.getEnemies().size(), 10, 280);
			font.draw(spriteBatch, "Items: " + tileMap.getItems().size(), 10, 260);
			spriteBatch.end();
		}

		if (debugShowFps) {
			spriteBatch.begin();
			font.draw(spriteBatch, Integer.toString(Gdx.graphics.getFramesPerSecond()), 492, 475);
			spriteBatch.end();
		}

		if (debugShowBounds) {
			// Green rectangle around Mario
			batch = renderer.getBatch();
			batch.begin();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(0, 1, 0, 0.5f));
			shapeRenderer.rect(mario.getX() + mario.getOffset().x, mario.getY(), mario.getWidth(), mario.getHeight());
			for (AbstractSprite sprite : tileMap.getEnemies()) {
				shapeRenderer.rect(sprite.getX() + sprite.getOffset().x, sprite.getY(), sprite.getWidth(),
						sprite.getHeight());
			}
			for (AbstractSprite sprite : tileMap.getItems()) {
				shapeRenderer.rect(sprite.getX() + sprite.getOffset().x, sprite.getY(), sprite.getWidth(),
						sprite.getHeight());
			}
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
			batch.end();
		}
	}

	private void moveCamera() {

		float move = mario.getX() - mario.getOldPosition().x;
		if (cameraOffset < 8) {
			cameraOffset = cameraOffset + move;
		} else {
			if (move > 0) {
				camera.position.x = camera.position.x + move;
			} else {
				cameraOffset = cameraOffset + move;
			}
		}
		if (mario.getX() < camera.position.x - 8) {
			mario.setX(mario.getOldPosition().x);
			mario.getAcceleration().x = 0;
			cameraOffset = cameraOffset - move;
		}
		camera.update();
	}

	private void handleItems(float deltaTime) {
		List<AbstractSprite> items = tileMap.getItems();
		for (int i = 0; i < items.size(); i++) {
			AbstractSprite item = items.get(i);			
			item.update(tileMap, camera, deltaTime);
			boolean collideMario = mario.getBounds().overlaps(item.getBounds());
			if (collideMario) {
				CollisionHandler.getCollisionHandler().collideMarioWithItem(mario, item);				
			}
			if (item.isDeletable()) {
				items.remove(i--);
			} else if (item.isVisible()) {
				item.render(renderer.getBatch());
			}
		}
	}
	
	private void handleEnemies(float deltaTime) {

		List<AbstractSprite> enemies = tileMap.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			AbstractSprite enemy = enemies.get(i);
			enemy.update(tileMap, camera, deltaTime);
			// Draw it
			if (enemy.isAlive()) {
				for (int j = i + 1; j < enemies.size(); j++) {
					// Check collision with other enemies
					CollisionHandler.getCollisionHandler().collideEnemies(enemy, enemies.get(j));
				}								
				if (!enemy.isKilled()) {					
					boolean collideMario = mario.getBounds().overlaps(enemy.getBounds());
					if (collideMario) {
						if (mario.getY() > enemy.getY() && mario.getState() == MarioStateEnum.FALLING) {									
							enemy.kill();
							mario.getAcceleration().y = 0.15f;
						} else {
							// Mario is dead
						}
					}
				} else {
					if (enemy.getActions().size==0) {
						enemy.setDeletable(true);
					}
				}
			}
			if (enemy.isDeletable()) {
				enemies.remove(i--);
			} else if (enemy.isVisible()) {
				enemy.render(renderer.getBatch());
			}

		}
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
				block.update(tileMap, camera, delta);
				if (block.isDeletable()) {
					blocks.remove(i--);
				} else if (block.isVisible()) {
					batch.draw(block.getCurrentFrame(), block.getX(), block.getY(), 1, 1);	
				}				
			}
			batch.end();
		}
	}

	private void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.F4)) {
			mario.changeSizeState(mario.getSizeState()==0 ? 1 : 0);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			debugShowText = !debugShowText;
		}

		if (Gdx.input.isKeyJustPressed(Keys.F5)) {
			mario.setAcceleration(new Vector2(0, 0));
			mario.setDirection(DirectionEnum.RIGHT);
			mario.setX(217);
			mario.setY(14);
			cameraOffset = 0;
			camera.position.x = 217 + 6;
			camera.update();
			scrollable = false;
		}

		if (Gdx.input.isKeyJustPressed(Keys.F2)) {
			debugShowFps = !debugShowFps;
		}

		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			debugShowBounds = !debugShowBounds;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (mario.getDirection() == DirectionEnum.LEFT) {
				// Sliding
				mario.setStateIfNotJumping(MarioStateEnum.SLIDING_LEFT);
				mario.decelerate(1.5f);
				if (mario.getAcceleration().x <= 0) {
					mario.getAcceleration().x = 0;
					mario.setDirection(DirectionEnum.RIGHT);
				}
			} else {
				mario.accelerate();
				mario.setDirection(DirectionEnum.RIGHT);
				mario.setStateIfNotJumping(MarioStateEnum.RUNNING_RIGHT);
			}
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (mario.getDirection() == DirectionEnum.RIGHT) {
				// Sliding
				mario.setStateIfNotJumping(MarioStateEnum.SLIDING_RIGHT);
				mario.decelerate(1.5f);
				if (mario.getAcceleration().x <= 0) {
					mario.getAcceleration().x = 0;
					mario.setDirection(DirectionEnum.LEFT);
				}
			} else {
				mario.accelerate();
				mario.setDirection(DirectionEnum.LEFT);
				mario.setStateIfNotJumping(MarioStateEnum.RUNNING_LEFT);
			}
		} else {
			mario.decelerate(1);
		}

		if (Gdx.input.isKeyPressed(Keys.UP) && mario.canInitiateJump()
				&& !(mario.getState() == MarioStateEnum.JUMPING || mario.getState() == MarioStateEnum.FALLING)) {
			// player is on the ground, so he is allowed to start a jump
			mario.setJumpTimer(1);
			mario.setState(MarioStateEnum.JUMPING);
			mario.getAcceleration().y = 0.16f;
			mario.setCanJumpHigher(true);
			jumpTimerMax = 24 + (int) mario.getAcceleration().x / 5;
		} else if (Gdx.input.isKeyPressed(Keys.UP) && mario.getState() == MarioStateEnum.JUMPING
				&& mario.canJumpHigher()) {
			if (mario.getJumpTimer() < jumpTimerMax) { //
				mario.incJumpTimer();
				mario.getAcceleration().y += 0.009;
			} else {
				mario.setCanJumpHigher(false);
				mario.setJumpTimer(0);
				mario.setState(MarioStateEnum.FALLING);
			}
		} else {
			mario.setCanJumpHigher(false);
			mario.setJumpTimer(0);
		}

		mario.setCanInitiateJump(!Gdx.input.isKeyPressed(Keys.UP) && mario.isOnFloor());

	}

}
