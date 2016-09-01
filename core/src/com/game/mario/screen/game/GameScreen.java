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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.game.mario.GameManager;
import com.game.mario.background.IScrollingBackground;
import com.game.mario.background.impl.LeftScrollingBackground;
import com.game.mario.camera.GameCamera;
import com.game.mario.collision.CollisionHandler;
import com.game.mario.enums.BackgroundTypeEnum;
import com.game.mario.enums.DirectionEnum;
import com.game.mario.enums.EnemyTypeEnum;
import com.game.mario.enums.MusicEnum;
import com.game.mario.enums.ScreenEnum;
import com.game.mario.enums.SpriteMoveEnum;
import com.game.mario.enums.WorldTypeEnum;
import com.game.mario.screen.cinematic.AbstractCinematicSceneHandler;
import com.game.mario.screen.cinematic.MarioDeathSceneHandler;
import com.game.mario.screen.cinematic.MarioGrowingSceneHandler;
import com.game.mario.screen.cinematic.TransferSceneHandler;
import com.game.mario.screen.cinematic.ending.CastleLevelEndingSceneHandler;
import com.game.mario.screen.cinematic.ending.FlagLevelEndingSceneHandler;
import com.game.mario.sound.SoundManager;
import com.game.mario.sprite.AbstractEnemy;
import com.game.mario.sprite.AbstractItem;
import com.game.mario.sprite.AbstractSfxSprite;
import com.game.mario.sprite.AbstractSprite;
import com.game.mario.sprite.Fireball;
import com.game.mario.sprite.bloc.AbstractBlock;
import com.game.mario.sprite.item.RedMushroom;
import com.game.mario.sprite.item.Star;
import com.game.mario.sprite.sfx.FireballExplosion;
import com.game.mario.sprite.sfx.Smoke;
import com.game.mario.sprite.statusbar.MarioCoins;
import com.game.mario.sprite.statusbar.MarioLifes;
import com.game.mario.sprite.tileobject.item.plateform.AbstractMetalPlateform;
import com.game.mario.sprite.tileobject.mario.Mario;
import com.game.mario.tilemap.TmxMap;
import com.game.mario.util.constant.KeysConstants;
import com.game.mario.util.constant.WinConstants;

public class GameScreen implements Screen  {
		
	/** KEYS CONSTANTS */
	private static final int KEY_DOWN = KeysConstants.KEY_DOWN;
	
	private static final int KEY_UP = KeysConstants.KEY_UP;
	
	private static final int KEY_LEFT =  KeysConstants.KEY_LEFT;
	
	private static final int KEY_RIGHT =  KeysConstants.KEY_RIGHT;
	
	private static final int KEY_SPEED_UP = KeysConstants.KEY_SPEED_UP;
	
	/** Key speed up released indicator */
	private boolean speedUpKeyReleased = true;	
	
	/** The stage with actors */
	private Stage stage;
	
	/** Camera following Mario */
	private GameCamera camera;
	
	/** Tilemap loaded from a TMX file */
	private TmxMap tilemap;

	/** Tilemap renderer: render tilemap and all sprites owned by the tilemap */
	private OrthogonalTiledMapRenderer tilemapRenderer;

	/** Sprite batch used to render fixed sprites (Status bar sprites) and text (debug, end scene text) */
	private SpriteBatch spriteBatch;

	/** Used in debug mode to draw bounding boxes of sprites */
	private ShapeRenderer shapeRenderer;
	
	/** Main batch used to draw elements */
	private Batch batch;

	/** Mario player */
	private Mario mario;

	//TODO refactor this in Mario class
	private static final int JUMP_TIMER_MAX = 40;

	private static final float MARIO_JUMP_ACCELERATION_CONTINUE = 0.0117f;

	private static final float MARIO_JUMP_ACCELERATION_INITIAL = 0.24f;
	
	private int jumpTimerMax = 20;
	
	private float jumpAccelerationContinue = 20;
	
	/** Backgrounds displayed un game */
	private Array<IScrollingBackground> backgrounds;
			
	/** End level cinematic scene handler */
	private AbstractCinematicSceneHandler levelEndingSceneHandler;
	
	/** Mario death cinematic scene handler */
	private AbstractCinematicSceneHandler marioDeathSceneHandler;
	
	/** Mario grow up/donw cinematic scene handler */
	private AbstractCinematicSceneHandler marioGrowingSceneHandler;
	
	/** Mario pipe transfer cinematic scene handler */
	private AbstractCinematicSceneHandler marioTransferSceneHandler;
	
	/** End level indicator : Mario reach the flag or the hawk in castle level */
	private boolean levelFinished = false;
	
	/** Debug font */
	private BitmapFont debugFont;
	
	/** Game font */
	private BitmapFont font;
	
	/** Debug parameters */
	private boolean debugShowText = false;

	private boolean debugShowBounds = false;

	private boolean debugShowFps = false;
		
	private int currentWorld;
	
	private int currentLevel;
	
	private float timer;
		
	public GameScreen() {
		
		currentWorld = GameManager.getGameManager().getCurrentLevel().getWorldNumber();
		currentLevel = GameManager.getGameManager().getCurrentLevel().getLevelNumber();
		
		// Initialize fonts
		debugFont = new BitmapFont();		
		debugFont.setColor(1, 1, 1, 1);		
		font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));		
		font.setColor(1,1,1,1);
		
		// Sprite batch, used to draw background and debug text 
		spriteBatch = new SpriteBatch();		
		// Shape renderer, used to draw rectangles around sprites in debug mode
		shapeRenderer = new ShapeRenderer();
				
		// Load the tilemap, set the unit scale to 1/32 (1 unit == 32 pixels)
		tilemap = new TmxMap("tilemaps/"+GameManager.getGameManager().getCurrentLevelName());
		// Renderer used to draw tilemap
		tilemapRenderer = new OrthogonalTiledMapRenderer(tilemap.getMap(), 1 / 32f);				

		// load the map, set the unit scale to 1/32 (1 unit == 32 pixels)
		tilemap = new TmxMap("tilemaps/"+GameManager.getGameManager().getCurrentLevelName());
		tilemapRenderer = new OrthogonalTiledMapRenderer(tilemap.getMap(), 1 / 32f);

		// Mario !!!
		mario = tilemap.getMario();

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new GameCamera(mario);
		camera.setCameraOffset(mario.getX());
		
		// Initialize backgrounds, which are defined in each TMX map with Tiled
		backgrounds = new Array<IScrollingBackground>();
		int i=0;
		for (BackgroundTypeEnum backgroundTypeEnum : tilemap.getBackgroundTypesEnum()) {
			IScrollingBackground scrollingBackground = new LeftScrollingBackground(mario, spriteBatch, backgroundTypeEnum, i==0 ? 16 : 24);
			backgrounds.add(scrollingBackground);			
			i++;
		}
		
		// Initialize stage, the stage is used for sprites actions
		stage = new Stage();					
		for (Actor actor : tilemap.getBlocks()) {
			// Add all blocks as actors, to simulate a movement when small Mario collides a wall while jumping
			stage.addActor(actor);
		}
				
		// Initialize status bar, remaining lifes, collected coins
		MarioLifes marioLifes = new MarioLifes();
		marioLifes.setPosition(10, Gdx.graphics.getHeight()-25);
		MarioCoins marioCoins= new MarioCoins();
		marioCoins.setPosition(100, Gdx.graphics.getHeight()-25);
		stage.addActor(marioLifes);
		stage.addActor(marioCoins);
		
		// Boolean indicating if level is finished or not
		levelFinished = false;			
				
		// Initialize scene handlers to play cinematics scenes during game
		if (tilemap.getWorldType()==WorldTypeEnum.CASTLE) {
			levelEndingSceneHandler = new CastleLevelEndingSceneHandler(mario, tilemap, camera, backgrounds, font, spriteBatch, tilemapRenderer, stage, batch);
		} else {
			levelEndingSceneHandler = new FlagLevelEndingSceneHandler(mario, tilemap, camera, backgrounds, font, spriteBatch, tilemapRenderer, stage, batch);
		}		
		marioDeathSceneHandler = new MarioDeathSceneHandler(mario, tilemap, camera, backgrounds, font, spriteBatch, tilemapRenderer, stage, batch);
		marioGrowingSceneHandler = new MarioGrowingSceneHandler(mario, tilemap, camera, backgrounds, font, spriteBatch, tilemapRenderer, stage, batch);
		marioTransferSceneHandler = new TransferSceneHandler(mario, tilemap, camera, backgrounds, font, spriteBatch, tilemapRenderer, stage, batch);	
						
		// Initialize sound theme
		SoundManager.getSoundManager().setStageMusic(MusicEnum.valueOf(tilemap.getMusicTheme().toUpperCase()));				
		
		timer = 400;
		
		/*int xBowserPos = 124;
		int yBowserPos = 5;
		
		int xFlagPos = 194;
		int yFlagPos = 1;		
		int xFlyingKoopa = 73;
		int yFlyingKoopa = 73;
		
		int x = xFlagPos;
		int y = yFlagPos;
		
		mario.setX(x);
		mario.setY(y);
		camera.setCameraOffset(2f);
		camera.getCamera().position.x = x+6;						
		camera.getCamera().update();*/				
	}
		
	@Override
	public void render(float delta) {
				
		if (levelFinished) {
			// Level is finished, play end level cinematic scene
			levelEndingSceneHandler.handleScene(delta);
		} else {			
			if (mario.isAlive()) {
				// Mario is alive
				if (mario.isGrowing()) {
					// Mario has been hit or got a mushroom, play grow up/down cinematic scene
					marioGrowingSceneHandler.handleScene(delta);					
				} else if (mario.isInTransfer()) {
					// Mario has taken a pipe, play transfer cinematic scene
					marioTransferSceneHandler.handleScene(delta);
				} else {
					// Still in game !
					handleMarioAlive(delta);
				}						
			} else {
				// Mario is dead, play death cinematic scene
				marioDeathSceneHandler.handleScene(delta);				
			}		
		}
	}
			
	private void handleMarioAlive(float delta) {

		// Common time counter used to update synchronized sprites animations
		AbstractSprite.updateCommonStateTime(delta);
		
		// Move each visible plateform, stuck Mario to the plateform if he's over it
		handlePlateforms(delta);
		
		// Listen to keyboard actions and update Mario status
		handleInput();
						
		// Update mario : 1 - Move, 2 - Collide with tilemap, 3 - Update bounds, 4 - Update animation, 5 - update status		
		mario.update(tilemap, camera.getCamera(), delta);
							
		// Check Mario collision with a block over his head
		CollisionHandler.getCollisionHandler().collideMarioWithUpperBlock(mario, tilemap, stage);
		
		// Draw the scene
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
					
		if (camera.isScrollable()) {			
			if (camera.getCamera().position.x < tilemap.getScrollMaxValue()) {
				// Move camera if its scrollable and if Mario is not fighting Bowser	
				camera.moveCamera();
				// Update scrolling backgrounds
				if (Math.floor(camera.getCameraOffset()) == 8) {
					backgrounds.get(0).update();				
					if (backgrounds.size>1) {
						backgrounds.get(1).update();
					}
				}
			} else {				
				// Mario is fighting Bowser, don't move camera anymore
				if (mario.getX() < camera.getCamera().position.x - 8) {					
					mario.setX(mario.getOldPosition().x);
					mario.getAcceleration().x = 0;
				}
			}			
						
		}
		// Draw backgrounds
		backgrounds.get(0).render();
		if (backgrounds.size>1) {
			backgrounds.get(1).render();
		}
		
		// Render tilemap
		tilemapRenderer.setView(camera.getCamera());
		tilemapRenderer.render();
		// Render mystery blocks
		renderMysteryBlocks(delta);
		// Render plateforms
		renderPlateforms(delta);
		// Move items, check collisions, render
		handleItems(delta);
		// Move enemies, check collisions, render
		handleEnemies(delta);		
		// Move fireballs, check collisions, render
		handleFireballs(delta);		
		// Render Mario		
		mario.render(tilemapRenderer.getBatch());		
		// Render special effects sprites (Lava, Ejected coins, end level sprites...)
		handleSfxSprites(delta);		
		// Render debug mode
		renderDebugMode();
		// Draw stage for moving actors
		stage.draw();
		// Display status bar				
		renderStatusBar();
		// Check if level is finished or not
		if (mario.getX()>=tilemap.getFlagTargetPosition()
				&& camera.getCamera().position.x -8 < tilemap.getFlag().getX()) {			
			levelFinished = true;
		}
		
		// Timer
		timer = timer - 2.5f * delta;
		if (timer<0) {
			timer = 0;
			killMario();
		}
	}

	private void killMario() {
		mario.setAlive(false);
		mario.setDeathAnimation();
		SoundManager.getSoundManager().stopMusic();
		SoundManager.getSoundManager().playSound(SoundManager.SOUND_MARIO_DEATH);
	}

	private void handlePlateforms(float deltaTime) {
		
		mario.setStuck(false);
		List<AbstractMetalPlateform> plateforms = tilemap.getPlateforms();		
		for (int i = 0; i < plateforms.size(); i++) {
			AbstractMetalPlateform plateform = plateforms.get(i);			
			plateform.update(tilemap, camera.getCamera(), deltaTime);			
			if (plateform.isDeletable()) {				
				plateforms.remove(i--);
			} 
		}				
	}
	
	private void renderPlateforms(float deltaTime) {
		
		List<AbstractMetalPlateform> plateforms = tilemap.getPlateforms();		
		for (int i = 0; i < plateforms.size(); i++) {
			AbstractMetalPlateform plateform = plateforms.get(i);
			if (plateform.isVisible()) {
				plateform.render(tilemapRenderer.getBatch());
			}
		}				
	}

	private void renderStatusBar() {
		spriteBatch.begin();		
		font.draw(spriteBatch, "X " + GameManager.getGameManager().getNbLifes(), 40, Gdx.graphics.getHeight()-10);
		font.draw(spriteBatch, "X " + GameManager.getGameManager().getNbCoins(), 115, Gdx.graphics.getHeight()-10);		
		font.draw(spriteBatch, "WORLD", 200, Gdx.graphics.getHeight()-10);
		font.draw(spriteBatch, currentWorld + " - " + currentLevel, 200, Gdx.graphics.getHeight()-27);
		font.draw(spriteBatch, "TIME" , Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight()-10);
		font.draw(spriteBatch, String.format("%.0f", timer) , Gdx.graphics.getWidth() - 55, Gdx.graphics.getHeight()-27);
		spriteBatch.end();
	}
	
	private void handleItems(float deltaTime) {
		List<AbstractItem> items = tilemap.getItems();		
		for (int i = 0; i < items.size(); i++) {
			AbstractItem item = items.get(i);						
			item.update(tilemap, camera.getCamera(), deltaTime);
			boolean collideMario = item.overlaps(mario);						
			if (collideMario) {
				CollisionHandler.getCollisionHandler().collideMarioWithItem(mario, item, camera, backgrounds);				
			}
			if (item.isDeletable()) {				
				items.remove(i--);
			} else if (item.isVisible()) {
				item.render(tilemapRenderer.getBatch());
			}
		}
	}
	
	private void handleSfxSprites(float deltaTime) {	
		List<AbstractSfxSprite> sfxSprites = tilemap.getSfxSprites();		
		for (int i = 0; i < sfxSprites.size(); i++) {
			AbstractSprite sfxSprite = sfxSprites.get(i);			
			sfxSprite.update(tilemap, camera.getCamera(), deltaTime);			
			if (sfxSprite.isDeletable()) {				
				sfxSprites.remove(i--);
			} else if (sfxSprite.isVisible()) {
				sfxSprite.render(tilemapRenderer.getBatch());
			}
		}
	}
	
	private void handleFireballs(float deltaTime) {
		List<AbstractSprite> fireballs = mario.getFireballs();
		for (int i = 0; i < fireballs.size(); i++) {
			AbstractSprite abstractSprite = fireballs.get(i); 
			abstractSprite.update(tilemap, camera.getCamera(), deltaTime);			
			if (abstractSprite.isDeletable()) {				
				fireballs.remove(i--);
				explodeFireball(abstractSprite);		
			} else if (abstractSprite.isVisible()) {
				abstractSprite.render(tilemapRenderer.getBatch());
			}
		}
	}
	
	private void handleEnemies(float deltaTime) {

		List<AbstractEnemy> enemies = tilemap.getEnemies();		
		for (int i = 0; i < enemies.size(); i++) {
			AbstractEnemy enemy = enemies.get(i);
			enemy.update(tilemap, camera.getCamera(), deltaTime);
			// Draw it
			if (enemy.isAlive()) {
				for (int j = i + 1; j < enemies.size(); j++) {
					// Check collision with other enemies
					CollisionHandler.getCollisionHandler().collideEnemies(enemy, enemies.get(j));
				}	
				for (int k = 0; k < mario.getFireballs().size(); k++) {
					AbstractSprite fireball = mario.getFireballs().get(k);					
					boolean collideFireball = fireball.getBounds().overlaps(enemy.getBounds());
					if (collideFireball && !enemy.isKilled()) {
						if (enemy.isKillableByFireball()) {
							enemy.killByFireball(fireball);
						}						
						if (enemy.isExplodeFireball()) {
							mario.getFireballs().remove(k--);
							explodeFireball(fireball);	
						}
					}
				}
				if (!enemy.isKilled()) {					
					boolean collideMario = enemy.overlaps(mario);
					if (collideMario) {
						boolean isEnemyHit = enemy.collideMario(mario);
						if (!isEnemyHit) {
							if (!mario.isInvincible()){							
								if (mario.getSizeState()>0) {								
									mario.setGrowingDown(true);
									mario.setGrowDownAnimation();
									SoundManager.getSoundManager().playSound(SoundManager.SOUND_PIPE);									
								} else {
									killMario();									
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
				enemy.render(tilemapRenderer.getBatch());
			}

		}
	}

	private void explodeFireball(AbstractSprite fireball) {		
		AbstractSfxSprite sprite = new FireballExplosion(fireball);		
		tilemap.getSfxSprites().add(sprite);
		stage.addActor(sprite);				   				
		sprite.addAppearAction();
	}

	private void renderMysteryBlocks(float delta) {

		// Get blocks from tilemap
		List<AbstractBlock> blocks = tilemap.getBlocks();
		if (blocks.size() > 0) {			
			batch = tilemapRenderer.getBatch();
			batch.begin();					
			for (int i = 0; i < blocks.size(); i++) {
				// For each block
				AbstractBlock block = blocks.get(i);
				block.update(tilemap, camera.getCamera(), delta);
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
		
		if (Gdx.input.isKeyPressed(KEY_SPEED_UP)) {			
			List<AbstractSprite> fireballs = mario.getFireballs();
			if (fireballs.size()<2 && mario.getSizeState()>=3 && speedUpKeyReleased == true) {
				// A fireball can be launched only if mario has a flower
				fireballs.add(new Fireball(mario));
			}
			speedUpKeyReleased = false;
		} else {
			speedUpKeyReleased = true;
		}
		
		if (Gdx.input.isKeyPressed(KEY_RIGHT)) {
			if (mario.getDirection() == DirectionEnum.LEFT) {
				// Sliding on the right				
				mario.setStateIfNotJumping(SpriteMoveEnum.SLIDING_LEFT);
				if (mario.getState()==SpriteMoveEnum.SLIDING_LEFT && mario.getPreviousState()!=mario.getState()) {
					addSmokeEffect();		
				}
				if (!(mario.isCrouch() && isUnderBlock(mario))) {
					mario.decelerate(1.5f);
					if (mario.getAcceleration().x <= 0) {
						// Not sliding anymore
						mario.getAcceleration().x = 0;
						mario.setDirection(DirectionEnum.RIGHT);						
					}
				}
				
			} else if (!mario.isCrouch()){
				// Running right, not crouched
				mario.accelerate(Gdx.input.isKeyPressed(KEY_SPEED_UP));
				mario.setDirection(DirectionEnum.RIGHT);
				mario.setStateIfNotJumping(SpriteMoveEnum.RUNNING_RIGHT);
			} else if (!isUnderBlock(mario)) {
				// Mario is crouched, not under a block				
				mario.decelerate(1);				
			}
		} else if (Gdx.input.isKeyPressed(KEY_LEFT)) {
			if (mario.getDirection() == DirectionEnum.RIGHT) {
				// Sliding on the left
				mario.setStateIfNotJumping(SpriteMoveEnum.SLIDING_RIGHT);
				if (mario.getState()==SpriteMoveEnum.SLIDING_RIGHT && mario.getPreviousState()!=mario.getState()) {
					addSmokeEffect();		
				}
				if (!(mario.isCrouch() && isUnderBlock(mario))) {
					mario.decelerate(1.5f);
					if (mario.getAcceleration().x <= 0) {
						// Not sliding anymore
						mario.getAcceleration().x = 0;
						mario.setDirection(DirectionEnum.LEFT);
					}
				}				
			} else if (!mario.isCrouch()) {
				// Running left, not crouched
				mario.accelerate(Gdx.input.isKeyPressed(KEY_SPEED_UP));
				mario.setDirection(DirectionEnum.LEFT);
				mario.setStateIfNotJumping(SpriteMoveEnum.RUNNING_LEFT);
			} else if (!isUnderBlock(mario)) {
				// Mario is crouched				
				mario.decelerate(1);
			}
		} else {
			// No LEFT/RIGHT key entered, decelerate mario
			if (!mario.isCrouch()
					|| (mario.isCrouch() && !isUnderBlock(mario))) {
				mario.decelerate(1);
			}
		}

		if (Gdx.input.isKeyPressed(KEY_UP) && mario.canInitiateJump()
				&& !(mario.getState() == SpriteMoveEnum.JUMPING || mario.getState() == SpriteMoveEnum.FALLING)) {
			// player is on the ground, so he is allowed to start a jump			
			mario.setJumpTimer(1);
			mario.setState(SpriteMoveEnum.JUMPING);
			mario.getAcceleration().y = MARIO_JUMP_ACCELERATION_INITIAL;
			mario.setCanJumpHigher(true);
			mario.setOnFloor(false);
			//jumpTimerMax = JUMP_TIMER_MAX + (int) (mario.getAcceleration().x * JUMPTIMER_ACCELERATION_COEF);
			jumpTimerMax = JUMP_TIMER_MAX;	
			float coefAcceleration = mario.getAcceleration().x<7 ? mario.getAcceleration().x/6000 : mario.getAcceleration().x/4000; 
			jumpAccelerationContinue = MARIO_JUMP_ACCELERATION_CONTINUE + coefAcceleration;			
			Sound soundToPlay = mario.getSizeState()>=1 ? SoundManager.SOUND_JUMP_SUPER : SoundManager.SOUND_JUMP_SMALL;
			SoundManager.getSoundManager().playSound(soundToPlay);
		} else if (Gdx.input.isKeyPressed(KEY_UP) && mario.getState() == SpriteMoveEnum.JUMPING
				&& mario.canJumpHigher()) {
			if (mario.getJumpTimer() < jumpTimerMax) { //
				mario.incJumpTimer();
				mario.getAcceleration().y += jumpAccelerationContinue;
			} else {
				mario.setCanJumpHigher(false);
				mario.setJumpTimer(0);
				mario.setState(SpriteMoveEnum.FALLING);
			}
		} else {
			mario.setCanJumpHigher(false);
			mario.setJumpTimer(0);
		}

		mario.setCanInitiateJump(!Gdx.input.isKeyPressed(KEY_UP) && mario.isOnFloor());

		if (Gdx.input.isKeyPressed(KEY_DOWN)
				&& mario.getSizeState()>1 				
				&& mario.getState()!=SpriteMoveEnum.JUMPING
				&& mario.getState()!=SpriteMoveEnum.FALLING) {
			mario.crouch();
		} else if (!Gdx.input.isKeyPressed(KEY_DOWN) && mario.isCrouch()) {	
			if (!isUnderBlock(mario)) {
				mario.uncrouch();
			}									
		}
		
		handleDebugKeys();
	}

	private void addSmokeEffect() {
		Smoke smoke = new Smoke(mario);
		tilemap.getSfxSprites().add(smoke);
		stage.addActor(smoke);
		smoke.addAppearAction();
	}

	private void handleDebugKeys() {
		
		if (Gdx.input.isKeyJustPressed(Keys.F1)) {
			debugShowFps = !debugShowFps;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F2)) {
			debugShowText = !debugShowText;
		}						

		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			debugShowBounds = !debugShowBounds;
		}
		

		if (Gdx.input.isKeyJustPressed(Keys.F4)) {
			mario.changeSizeState(mario.getSizeState()==0 ? 2 : mario.getSizeState()==2 ? 3 : 0);
		}				
						
		if (Gdx.input.isKeyJustPressed(Keys.F5)) {
			debugFont.setColor(0, 1, 0, 1);
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F6)) {
			tilemap.getItems().add(new Star(mario.getX()+1, 15));
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.F7)) {
			tilemap.getItems().add(new RedMushroom(mario.getX()+1, 15));
		}
		
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			backgrounds.get(0).toggleEnabled();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.NUM_2) && backgrounds.size>1) {
			backgrounds.get(1).toggleEnabled();
		}				
						
		if (Gdx.input.isKeyJustPressed(Keys.NUMPAD_6)) {
			mario.setX(mario.getX()+8);			
			camera.getCamera().position.x = camera.getCamera().position.x+8;				
			camera.getCamera().update();
		}
		if (Gdx.input.isKeyJustPressed(Keys.NUMPAD_2)) {			
			mario.setY(mario.getY()-4);			
		}		
		if (Gdx.input.isKeyJustPressed(Keys.NUMPAD_8)) {			
			mario.setY(mario.getY()+8);			
		}
	}
	
	private boolean isUnderBlock(Mario mario) {		
		
		Vector2 leftTopCorner = new Vector2(mario.getX() + mario.getOffset().x, mario.getY() + 1);
		Vector2 rightTopCorner = new Vector2(mario.getX() + mario.getWidth() + mario.getOffset().x, mario.getY() + 1);
		
		int x = (int) leftTopCorner.x;
		int y = (int) leftTopCorner.y;		
		if (tilemap.isCollisioningTileAt(x, y)) {
			return true;
		}		
		x = (int) rightTopCorner.x;
		y = (int) rightTopCorner.y;								
		
		return tilemap.isCollisioningTileAt(x, y);
	}
	
	private void renderDebugMode() {

		if (debugShowText) {
			
			int x = WinConstants.WIDTH - 600;
			int y = WinConstants.HEIGHT - 40;
			
			/* MARIO VARIABLES */
			spriteBatch.begin();
			debugFont.draw(spriteBatch, "mario.position=" + String.format("%.3f", mario.getX()) + " | " + String.format("%.3f", mario.getY()), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "mario.acceleration=" + String.format("%.1f", mario.getAcceleration().x) + " | " + String.format("%.1f", mario.getAcceleration().y), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "state=" + mario.getState().toString(), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "previous state=" + mario.getPreviousState().toString(), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "direction=" + mario.getDirection().toString(), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "jumptimer=" + mario.getJumpTimer(), x, y);
			y = y -20;			
			debugFont.draw(spriteBatch, "isOnFloor=" + mario.isOnFloor(), x, y);
			y = y -20;			
			debugFont.draw(spriteBatch, "move vector: " + String.format("%.2f",mario.getMove().x) + " | " +String.format("%.2f",mario.getMove().y), x, y);			
			y = y -20;			
			debugFont.draw(spriteBatch, "isOnPlateform: " + mario.isStuck(), x, y);
			y = y -20;			
			debugFont.draw(spriteBatch, "sizeState: " + mario.getSizeState(), x, y);
			
			
			/* ENV VARIABLES */
			x = WinConstants.WIDTH - 350;
			y = WinConstants.HEIGHT - 30;
			
			debugFont.draw(spriteBatch, "camera.x=" + String.format("%.1f", camera.getCamera().position.x), x, y);
			y = y -20;
			debugFont.draw(spriteBatch, " camera.offset=" + String.format("%.1f", camera.getCameraOffset()), x, y);
			y = y -20;			
			debugFont.draw(spriteBatch, "Mysteryblocks: " + tilemap.getBlocks().size(), x, y);
			y = y -20;
			int alive = 0;
			for (AbstractEnemy enemy : tilemap.getEnemies()) {
				alive += enemy.isAlive() ? 1 : 0;
			}
			debugFont.draw(spriteBatch, "Enemies: " + tilemap.getEnemies().size() + " - " + alive + " alive", x, y);
			y = y -20;
			alive = 0;
			for (AbstractEnemy enemy : tilemap.getEnemies()) {
				if (enemy.getEnemyType()==EnemyTypeEnum.KOOPA) {
				debugFont.draw(spriteBatch, "Enemy #" + alive + " - " + (enemy.isAlive() ? " alive - " : "") + enemy.getState() + " -onFloor "+enemy.isOnFloor(), x, y);				
				y = y -20;
				}
				alive++;
			}			
			alive = 0;
			for (AbstractSprite item : tilemap.getItems()) {
				alive += item.isAlive() ? 1 : 0;
			}
			debugFont.draw(spriteBatch, "Items: " + tilemap.getItems().size() + " - " + alive + " alive", x, y);
			y = y -20;
			alive = 0;
			/*for (AbstractSprite item : tileMap.getItems()) {
				debugFont.draw(spriteBatch, "Item #" + alive + " - " + (item.isAlive() ? " alive - " : "") + (item.isVisible() ? " visible - " : "") , x, y);
				y = y -20;
				alive++;
			}*/	
			alive = 0;
			for (AbstractMetalPlateform plateform : tilemap.getPlateforms()) {
				debugFont.draw(spriteBatch, "Plateform #" + alive + " - " + (plateform.isAlive() ? " alive - " : "") + (plateform.isVisible() ? " visible - " : "") , x, y);
				y = y -20;
				alive++;
			}			
			debugFont.draw(spriteBatch, "Fireballs: " + mario.getFireballs().size(), x, y);
			y = y -20;			
			debugFont.draw(spriteBatch, "backgrounds: " + backgrounds.size, x, y);
			y = y -20;
			debugFont.draw(spriteBatch, "worldType: " + tilemap.getWorldType(), x, y);
			
			spriteBatch.end();
		}

		if (debugShowFps) {
			spriteBatch.begin();
			debugFont.draw(spriteBatch, Integer.toString(Gdx.graphics.getFramesPerSecond()), WinConstants.WIDTH - 20, WinConstants.HEIGHT-10);
			spriteBatch.end();
		}

		if (debugShowBounds) {
			// Green rectangle around Mario
			batch = tilemapRenderer.getBatch();
			batch.begin();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			shapeRenderer.setProjectionMatrix(camera.getCamera().combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(new Color(0, 1, 0, 0.5f));
			shapeRenderer.rect(mario.getX() + mario.getOffset().x, mario.getY(), mario.getWidth(), mario.getHeight());
			for (AbstractSprite sprite : tilemap.getEnemies()) {
				shapeRenderer.rect(sprite.getX() + sprite.getOffset().x, sprite.getY(), sprite.getWidth(),
						sprite.getHeight());
			}
			for (AbstractSprite sprite : tilemap.getItems()) {
				shapeRenderer.rect(sprite.getX() + sprite.getOffset().x, sprite.getY(), sprite.getWidth(),
						sprite.getHeight());
			}
			for (AbstractSprite sprite : mario.getFireballs()) {
				shapeRenderer.rect(sprite.getX() + sprite.getOffset().x, sprite.getY(), sprite.getWidth(),
						sprite.getHeight());
			}
			
			for (AbstractMetalPlateform plateform : tilemap.getPlateforms()) {
				shapeRenderer.rect(plateform.getX() + plateform.getOffset().x, plateform.getY(), plateform.getWidth(),
						plateform.getHeight());
			}
			
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
			batch.end();
		}
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
		tilemap.dispose();		
		tilemapRenderer.dispose();		
		shapeRenderer.dispose();
		font.dispose();
		debugFont.dispose();
		spriteBatch.dispose();				
		SoundManager.getSoundManager().stopMusic();		
	}

}
