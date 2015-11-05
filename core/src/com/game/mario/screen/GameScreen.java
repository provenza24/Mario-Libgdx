package com.game.mario.screen;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mario.GameManager;
import com.game.mario.RectangleUtil;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.CollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.statusbar.MarioCoins;
import com.game.mario.sprite.statusbar.MarioLifes;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;

public class GameScreen implements Screen  {

	private boolean debugShowText = false;

	private boolean debugShowBounds = false;

	private boolean debugShowFps = false;

	private TmxMap tileMap;

	private OrthogonalTiledMapRenderer renderer;

	private Batch batch;

	private Mario mario;

	private GameCamera camera;
	
	private BitmapFont debugFont;
	
	private BitmapFont font;

	private SpriteBatch spriteBatch;

	private ShapeRenderer shapeRenderer;

	private int jumpTimerMax = 20;

	private IScrollingBackground scrollingBackground;

	private Stage stage;
		
	public GameScreen() {

		shapeRenderer = new ShapeRenderer();

		spriteBatch = new SpriteBatch();
		debugFont = new BitmapFont();
		// font.setColor(0.5f,0.4f,0,1);
		debugFont.setColor(0, 0, 1, 1);
		
		font = new BitmapFont(Gdx.files.internal("fonts/mario_in_game.fnt"));		
		font.setColor(1,1,1,1);

		// load the map, set the unit scale to 1/32 (1 unit == 32 pixels)
		tileMap = new TmxMap("tilemaps/"+GameManager.getGameManager().getCurrentLevelName());
		renderer = new OrthogonalTiledMapRenderer(tileMap.getMap(), 1 / 32f);

		mario = tileMap.getMario();

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new GameCamera();
		camera.setCameraOffset(mario.getX());
		
		scrollingBackground = new LeftScrollingBackground(mario, spriteBatch, tileMap.getBackgroundType(), 16);
		
		stage = new Stage();
					
		for (Actor actor : tileMap.getBlocks()) {
			stage.addActor(actor);
		}
		
		MarioLifes marioLifes = new MarioLifes();
		marioLifes.setPosition(10, 460 - marioLifes.getHeight()/2);
		MarioCoins marioCoins= new MarioCoins();
		marioCoins.setPosition(100, 460 - marioCoins.getHeight()/2);
		stage.addActor(marioLifes);
		stage.addActor(marioCoins);
	}
		
	@Override
	public void render(float delta) {
					
		// Listen to keyboard actions and update Mario status
		handleInput();
		mario.update(tileMap, camera.getCamera(), delta);
		
		CollisionHandler.getCollisionHandler().collideMarioWithUpperBlock(mario, tileMap, stage);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (camera.isScrollable()) {
			// Move camera
			camera.moveCamera(mario);
			// Move scrolling background
			if (Math.floor(camera.getCameraOffset()) == 8) {
				scrollingBackground.update();
			}
			scrollingBackground.render();
		}
		
		// Render tilemap
		renderer.setView(camera.getCamera());
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
						
		renderStatusBar();
		
		if (mario.getX()>=tileMap.getFlag().getX() 
				&& camera.getCamera().position.x -8 < tileMap.getFlag().getX()) {			
			GameManager.getGameManager().nextLevel();
		}
		
		if (!mario.isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameManager.getGameManager().restartLevel();
		}
		
	}

	private void renderStatusBar() {
		spriteBatch.begin();		
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbLifes(), 40, 470);
		font.draw(spriteBatch, "x " + GameManager.getGameManager().getNbCoins(), 115, 470);		
		spriteBatch.end();
	}

	private void renderDebugMode() {

		if (debugShowText) {
			// Mario information
			spriteBatch.begin();
			debugFont.draw(spriteBatch,
					"mario.position=" + String.format("%.1f", mario.getX()) + "," + String.format("%.1f", mario.getY()),
					10, 460);
			debugFont.draw(spriteBatch, "mario.acceleration=" + String.format("%.1f", mario.getAcceleration().x) + ","
					+ String.format("%.1f", mario.getAcceleration().y), 10, 440);
			debugFont.draw(spriteBatch, "state=" + mario.getState().toString(), 10, 420);
			debugFont.draw(spriteBatch, "direction=" + mario.getDirection().toString(), 10, 400);
			debugFont.draw(spriteBatch, "jumptimer=" + mario.getJumpTimer(), 10, 380);
			debugFont.draw(spriteBatch, "isOnFloor=" + mario.isOnFloor(), 10, 360);
			debugFont.draw(spriteBatch, "camera.x=" + String.format("%.1f", camera.getCamera().position.x) + " camera.offset="
					+ String.format("%.1f", camera.getCameraOffset()), 10, 340);
			debugFont.draw(spriteBatch,
					"tile-collision:  (right=" + mario.getMapCollisionEvent().isCollidingRight() + ", left="
							+ mario.getMapCollisionEvent().isCollidingLeft() + ", top="
							+ mario.getMapCollisionEvent().isCollidingTop() + ", bottom="
							+ mario.getMapCollisionEvent().isCollidingBottom() + ")",
					10, 320);
			debugFont.draw(spriteBatch, "Mysteryblocks: " + tileMap.getBlocks().size(), 10, 300);
			debugFont.draw(spriteBatch, "Enemies: " + tileMap.getEnemies().size(), 10, 280);
			debugFont.draw(spriteBatch, "Items: " + tileMap.getItems().size(), 10, 260);
			spriteBatch.end();
		}

		if (debugShowFps) {
			spriteBatch.begin();
			debugFont.draw(spriteBatch, Integer.toString(Gdx.graphics.getFramesPerSecond()), 492, 475);
			spriteBatch.end();
		}

		if (debugShowBounds) {
			// Green rectangle around Mario
			batch = renderer.getBatch();
			batch.begin();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
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
	
	private void handleItems(float deltaTime) {
		List<AbstractSprite> items = tileMap.getItems();
		for (int i = 0; i < items.size(); i++) {
			AbstractSprite item = items.get(i);			
			item.update(tileMap, camera.getCamera(), deltaTime);
			boolean collideMario = RectangleUtil.overlaps(mario.getBounds(), item.getBounds());
			if (collideMario) {
				CollisionHandler.getCollisionHandler().collideMarioWithItem(mario, item, camera);				
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
			enemy.update(tileMap, camera.getCamera(), deltaTime);
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
						} else if (!mario.isInvincible()){							
							if (mario.getSizeState()>0) {
								mario.changeSizeState(0);
								mario.setInvincible(true);								
							} else {
								mario.setAlive(false);								
								Gdx.app.log("STATE", "Mario just died");
							}
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

	private void handleInput() {

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			GameManager.getGameManager().changeScreen(ScreenEnum.PAUSE_MENU);			
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F4)) {
			mario.changeSizeState(mario.getSizeState()==0 ? 1 : 0);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			debugShowText = !debugShowText;
		}

		if (Gdx.input.isKeyJustPressed(Keys.F2)) {
			debugShowFps = !debugShowFps;
		}

		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			debugShowBounds = !debugShowBounds;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
			this.pause();
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {		
		stage.dispose();
		tileMap.dispose();		
		renderer.dispose();		
		shapeRenderer.dispose();		
		debugFont.dispose();
		spriteBatch.dispose();				
	}

}
