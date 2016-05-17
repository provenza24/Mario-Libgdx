package com.game.mario.screen.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.CollisionHandler;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.MarioStateEnum;
import com.game.mario.enums.MusicEnum;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.screen.cinematic.AbstractCinematicSceneHandler;
import com.game.mario.screen.cinematic.LevelEndingSceneHandler;
import com.game.mario.screen.cinematic.MarioDeathSceneHandler;
import com.game.mario.screen.cinematic.MarioGrowingSceneHandler;
import com.game.mario.screen.cinematic.TransferSceneHandler;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.bloc.Block;
import com.game.mario.sprite.bloc.MysteryBlock;
import com.game.mario.sprite.item.Fireball;
import com.game.mario.sprite.statusbar.MarioCoins;
import com.game.mario.sprite.statusbar.MarioLifes;
import com.game.mario.sprite.tileobject.enemy.AbstractEnemy;
import com.game.mario.sprite.tileobject.item.Coin;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.RectangleUtil;

public class GameScreen implements Screen  {
		
	private boolean levelFinished = false;
	
	private boolean keyUpReleased = true;
	
	private int KEY_UP = Keys.Z;
	
	private int KEY_LEFT = Keys.LEFT;
	
	private int KEY_RIGHT = Keys.RIGHT;
	
	private int KEY_SPEED_UP = Keys.A;
	
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
			
	private AbstractCinematicSceneHandler levelEndingSceneHandler;
	
	private AbstractCinematicSceneHandler marioDeathSceneHandler;
	
	private AbstractCinematicSceneHandler marioGrowingSceneHandler;
	
	private AbstractCinematicSceneHandler marioTransferSceneHandler;
		
	public GameScreen() {
		
		shapeRenderer = new ShapeRenderer();

		spriteBatch = new SpriteBatch();
		debugFont = new BitmapFont();		
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
		
		levelFinished = false;			
				
		levelEndingSceneHandler = new LevelEndingSceneHandler(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);
		marioDeathSceneHandler = new MarioDeathSceneHandler(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);
		marioGrowingSceneHandler = new MarioGrowingSceneHandler(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);
		marioTransferSceneHandler = new TransferSceneHandler(mario, tileMap, camera, scrollingBackground, font, spriteBatch, renderer, stage, batch);	
						
		if (tileMap.getMusicTheme().toUpperCase().equals(MusicEnum.OVERWORLD.toString())) {
			SoundManager.getSoundManager().setStageMusic(SoundManager.SOUND_OVERWORLD_THEME);	
		} else {
			SoundManager.getSoundManager().setStageMusic(SoundManager.SOUND_UNDERGROUND_THEME);
		}		
		
		/*mario.setX(56);
		mario.setY(8);
		camera.setCameraOffset(2f);
		camera.getCamera().position.x = 62;						
		camera.getCamera().update();*/
	}
		
	@Override
	public void render(float delta) {
				
		if (levelFinished) {			
			levelEndingSceneHandler.handleScene(delta);
		} else {
			if (mario.isAlive()) {
				if (mario.isGrowing()) {				
					marioGrowingSceneHandler.handleScene(delta);					
				} else if (mario.isInTransfer()) {
					marioTransferSceneHandler.handleScene(delta);
				} else {
					handleMarioAlive(delta);
				}						
			} else {
				marioDeathSceneHandler.handleScene(delta);				
			}		
		}
	}
		
	private void handleMarioAlive(float delta) {
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
		
		// Fireballs
		handleFireballs(delta);
		
		// Render Mario		
		mario.render(renderer.getBatch());
		// Render debug mode (press F1 to display/hide debug)
		renderDebugMode();

		//stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		// Display status bar				
		renderStatusBar();
				
		if (mario.getX()>=tileMap.getFlagTargetPosition() 
				&& camera.getCamera().position.x -8 < tileMap.getFlag().getX()) {			
			levelFinished = true;
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
			int alive = 0;
			for (AbstractEnemy enemy : tileMap.getEnemies()) {
				alive += enemy.isAlive() ? 1 : 0;
			}
			debugFont.draw(spriteBatch, "Enemies: " + tileMap.getEnemies().size() + " - " + alive + " alive", 10, 280);
			alive = 0;
			for (AbstractSprite item : tileMap.getItems()) {
				alive += item.isAlive() ? 1 : 0;
			}
			debugFont.draw(spriteBatch, "Items: " + tileMap.getItems().size() + " - " + alive + " alive", 10, 260);
			debugFont.draw(spriteBatch, "Fireballs: " + mario.getFireballs().size(), 10, 240);
			int i=0;
			for (AbstractSprite sprite : mario.getFireballs()) {
				debugFont.draw(spriteBatch, "x"+i+":" + String.format("%.1f",sprite.getX()), 10, 200 + i*20);
				i++;
			}
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
			for (AbstractSprite sprite : mario.getFireballs()) {
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
		if (items.size()>0) {
			Coin.updateStateTime(deltaTime);
		}
		for (int i = 0; i < items.size(); i++) {
			AbstractSprite item = items.get(i);			
			item.update(tileMap, camera.getCamera(), deltaTime);
			boolean collideMario = RectangleUtil.overlaps(mario.getBounds(), item.getBounds());
			if (collideMario) {
				CollisionHandler.getCollisionHandler().collideMarioWithItem(mario, item, camera, scrollingBackground);				
			}
			if (item.isDeletable()) {				
				items.remove(i--);
			} else if (item.isVisible()) {
				item.render(renderer.getBatch());
			}
		}
	}
	
	private void handleFireballs(float deltaTime) {
		List<AbstractSprite> fireballs = mario.getFireballs();
		for (int i = 0; i < fireballs.size(); i++) {
			AbstractSprite abstractSprite = fireballs.get(i); 
			abstractSprite.update(tileMap, camera.getCamera(), deltaTime);			
			if (abstractSprite.isDeletable()) {				
				fireballs.remove(i--);				
			} else if (abstractSprite.isVisible()) {
				abstractSprite.render(renderer.getBatch());
			}
		}
	}
	
	private void handleEnemies(float deltaTime) {

		List<AbstractEnemy> enemies = tileMap.getEnemies();
		for (int i = 0; i < enemies.size(); i++) {
			AbstractEnemy enemy = enemies.get(i);
			enemy.update(tileMap, camera.getCamera(), deltaTime);
			// Draw it
			if (enemy.isAlive()) {
				for (int j = i + 1; j < enemies.size(); j++) {
					// Check collision with other enemies
					CollisionHandler.getCollisionHandler().collideEnemies(enemy, enemies.get(j));
				}	
				for (int k = 0; k < mario.getFireballs().size(); k++) {
					AbstractSprite fireball = mario.getFireballs().get(k);
					boolean collideFireball = fireball.getBounds().overlaps(enemy.getBounds());
					if (collideFireball) {
						enemy.killByFireball(fireball);
						mario.getFireballs().remove(k--);
					}
				}
				if (!enemy.isKilled()) {					
					boolean collideMario = mario.getBounds().overlaps(enemy.getBounds());
					if (collideMario) {
						boolean isEnemyHit = enemy.collideMario(mario);
						if (!isEnemyHit) {
							if (!mario.isInvincible()){							
								if (mario.getSizeState()>0) {								
									mario.setGrowingDown(true);
									mario.setGrowDownAnimation();
									SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);									
								} else {
									mario.setAlive(false);
									mario.setDeathAnimation();
									SoundManager.getSoundManager().stopMusic();
									SoundManager.getSoundManager().playSound(SoundManager.SOUND_MARIO_DEATH);									
								}
							}
						}						
					}
				} else if (!enemy.isBumped()) {
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

	private void handleInput() {
	
		if (Gdx.input.isKeyJustPressed(Keys.C)) {
			tileMap.changeCellValue(0, 0, 5);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			GameManager.getGameManager().changeScreen(ScreenEnum.PAUSE_MENU);			
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F4)) {
			mario.changeSizeState(mario.getSizeState()==0 ? 1 : mario.getSizeState()==1 ? 2 : 0);
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
			mario.setX(mario.getX()+8);
			mario.setY(mario.getY()+6);
			camera.getCamera().position.x = camera.getCamera().position.x+8;				
			camera.getCamera().update();
		}
		
		if (Gdx.input.isKeyPressed(KEY_SPEED_UP)) {			
			List<AbstractSprite> fireballs = mario.getFireballs();
			if (fireballs.size()<2 && mario.getSizeState()==2 && keyUpReleased == true) {
				// A fireball can be launched only if mario has a flower
				fireballs.add(new Fireball(mario));
			}
			keyUpReleased = false;
		} else {
			keyUpReleased = true;
		}
		
		if (Gdx.input.isKeyPressed(KEY_RIGHT)) {
			if (mario.getDirection() == DirectionEnum.LEFT) {
				// Sliding
				mario.setStateIfNotJumping(MarioStateEnum.SLIDING_LEFT);
				mario.decelerate(1.5f);
				if (mario.getAcceleration().x <= 0) {
					mario.getAcceleration().x = 0;
					mario.setDirection(DirectionEnum.RIGHT);
				}
			} else {
				mario.accelerate(Gdx.input.isKeyPressed(KEY_SPEED_UP));
				mario.setDirection(DirectionEnum.RIGHT);
				mario.setStateIfNotJumping(MarioStateEnum.RUNNING_RIGHT);
			}
		} else if (Gdx.input.isKeyPressed(KEY_LEFT)) {
			if (mario.getDirection() == DirectionEnum.RIGHT) {
				// Sliding
				mario.setStateIfNotJumping(MarioStateEnum.SLIDING_RIGHT);
				mario.decelerate(1.5f);
				if (mario.getAcceleration().x <= 0) {
					mario.getAcceleration().x = 0;
					mario.setDirection(DirectionEnum.LEFT);
				}
			} else {
				mario.accelerate(Gdx.input.isKeyPressed(KEY_SPEED_UP));
				mario.setDirection(DirectionEnum.LEFT);
				mario.setStateIfNotJumping(MarioStateEnum.RUNNING_LEFT);
			}
		} else {
			mario.decelerate(1);
		}

		if (Gdx.input.isKeyPressed(KEY_UP) && mario.canInitiateJump()
				&& !(mario.getState() == MarioStateEnum.JUMPING || mario.getState() == MarioStateEnum.FALLING)) {
			// player is on the ground, so he is allowed to start a jump
			mario.setJumpTimer(1);
			mario.setState(MarioStateEnum.JUMPING);
			mario.getAcceleration().y = 0.16f;
			mario.setCanJumpHigher(true);
			jumpTimerMax = 24 + (int) (mario.getAcceleration().x / 4);
			Sound soundToPlay = mario.getSizeState()>0 ? SoundManager.SOUND_JUMP_SUPER : SoundManager.SOUND_JUMP_SMALL;
			SoundManager.getSoundManager().playSound(soundToPlay);
		} else if (Gdx.input.isKeyPressed(KEY_UP) && mario.getState() == MarioStateEnum.JUMPING
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

		mario.setCanInitiateJump(!Gdx.input.isKeyPressed(KEY_UP) && mario.isOnFloor());

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
		SoundManager.getSoundManager().stopMusic();		
	}

}
